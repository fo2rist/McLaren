package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.repository.TransmissionRepositoryPubSub.PubSubEvent
import com.github.fo2rist.mclaren.testdata.REAL_TRANSMISSION_RESPONSE
import com.github.fo2rist.mclaren.utils.custommatchers.anyKotlinObject
import com.github.fo2rist.mclaren.web.TransmissionWebService
import com.github.fo2rist.mclaren.web.TransmissionWebService.TransmissionRequestCallback
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.net.URL

@RunWith(JUnit4::class)
class TransmissionRepositoryImplTest {
    private lateinit var repository: TransmissionRepository
    private lateinit var mockWebService: TransmissionWebService
    private lateinit var mockPubSub: TransmissionRepositoryPubSub

    @Before
    fun setUp() {
        mockWebService = mock(TransmissionWebService::class.java)
        mockPubSub = mock(TransmissionRepositoryPubSub::class.java)
        repository = TransmissionRepositoryImpl(mockWebService, mockPubSub)
    }

    @Test
    fun test_loadRepository_callsWebService_and_firesLoadStartedEvent() {
        repository.loadTransmission()

        verify(mockWebService).requestTransmission(anyKotlinObject())
        verify(mockPubSub).publish(PubSubEvent.LoadingStarted)
    }

    @Test
    fun test_onSuccess_firesLoadFinishEvents() {
        prepareRealWebResponse()

        repository.loadTransmission()

        verify(mockPubSub).publish(PubSubEvent.LoadingStarted)
        verify(mockPubSub).publish(PubSubEvent.LoadingFinished)
        //The last event is matched by the parent class so we count two previous calls
        verify(mockPubSub, times(3)).publish(anyKotlinObject<PubSubEvent.TransmissionUpdateReady>())
    }

    @Test
    fun test_loadRepository_respondWithCachesItems_ifPresent() {
        //load for the first time
        prepareRealWebResponse()
        repository.loadTransmission()
        reset(mockPubSub)
        reset(mockWebService)

        //load when cache is non empty
        repository.loadTransmission()

        verify(mockPubSub).publish(PubSubEvent.LoadingStarted)
        verify(mockPubSub, times(2)).publish(anyKotlinObject<PubSubEvent.TransmissionUpdateReady>())
    }

    private fun prepareRealWebResponse() {
        doAnswer {
            val callback: TransmissionRequestCallback = it.getArgument(0)
            callback.onSuccess(URL("http://dummy.url"), 200, REAL_TRANSMISSION_RESPONSE)
        }.`when`(mockWebService).requestTransmission(anyKotlinObject())
    }

    private fun resetWebResponse() {
        `when`(mockWebService.requestTransmission(anyKotlinObject())).thenReturn(Unit)
    }
}
