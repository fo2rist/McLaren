package com.github.fo2rist.mclaren.repository.transmission

import com.github.fo2rist.mclaren.repository.transmission.TransmissionRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.testdata.REAL_TRANSMISSION_RESPONSE
import com.github.fo2rist.mclaren.web.transmission.TransmissionWebService
import com.github.fo2rist.mclaren.web.utils.BadResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.stubbing
import com.nhaarman.mockitokotlin2.verifyBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import java.net.URL

@RunWith(RobolectricTestRunner::class)
class TransmissionRepositoryImplTest {
    private lateinit var repository: TransmissionRepository
    private lateinit var mockWebService: TransmissionWebService
    private lateinit var mockEventBus: TransmissionRepositoryEventBus

    @Before
    fun setUp() {
        mockWebService = mock(TransmissionWebService::class.java)
        mockEventBus = mock(TransmissionRepositoryEventBus::class.java)
        repository = TransmissionRepositoryImpl(mockWebService, mockEventBus)
    }

    @Test
    fun test_loadRepository_callsWebService_and_firesLoadStartedEvent() {
        repository.loadTransmission()

        verifyBlocking(mockWebService) { requestTransmission() }
        verify(mockEventBus).publish(LoadingEvent.LoadingStarted)
    }

    @Test
    fun test_refresh_onSuccess_firesLoadFinishEvents() {
        prepareRealWebResponse()

        repository.refreshTransmission()

        verify(mockEventBus).publish(LoadingEvent.LoadingStarted)
        verify(mockEventBus).publish(any<LoadingEvent.TransmissionUpdateReady>())
        verify(mockEventBus).publish(LoadingEvent.LoadingFinished)
    }

    @Test
    fun test_loadRepository_respondWithCachesItems_ifPresent() {
        //load for the first time
        prepareRealWebResponse()
        repository.loadTransmission()
        reset(mockEventBus)

        //load when cache is non empty
        repository.loadTransmission()

        verify(mockEventBus).publish(LoadingEvent.LoadingStarted)
        verify(mockEventBus, times(2)).publish(any<LoadingEvent.TransmissionUpdateReady>())
        verify(mockEventBus).publish(LoadingEvent.LoadingFinished)
    }

    private fun prepareRealWebResponse() {
        stubbing(mockWebService) {
            onBlocking { requestTransmission() }.doReturn(REAL_TRANSMISSION_RESPONSE)
        }
    }

    @Test
    fun test_refresh_onFailure_firesLoadingErrorEvent() {
        prepareFailureWebResponse()

        repository.refreshTransmission()

        verify(mockEventBus).publish(LoadingEvent.LoadingStarted)
        verify(mockEventBus).publish(LoadingEvent.LoadingError)
        verify(mockEventBus).publish(LoadingEvent.LoadingFinished)
    }

    private fun prepareFailureWebResponse() {
        stubbing(mockWebService) {
            onBlocking { requestTransmission() }.doThrow(BadResponse(URL("http://empty.url"), 400))
        }
    }
}
