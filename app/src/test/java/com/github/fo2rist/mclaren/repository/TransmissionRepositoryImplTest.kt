package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.repository.TransmissionRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.testdata.REAL_TRANSMISSION_RESPONSE
import com.github.fo2rist.mclaren.web.TransmissionWebService
import com.github.fo2rist.mclaren.web.utils.BadResponse
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
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
    fun test_loadRepository_callsWebService_and_firesLoadStartedEvent() = runBlocking {
        repository.loadTransmission()

        verify(mockWebService).requestTransmission()
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

    private fun prepareRealWebResponse()= runBlocking {
        whenever(mockWebService.requestTransmission()).thenReturn(REAL_TRANSMISSION_RESPONSE)
    }

    @Test
    fun test_refresh_onFailure_firesLoadingErrorEvent() {
        prepareFailureWebResponse()

        repository.refreshTransmission()

        verify(mockEventBus).publish(LoadingEvent.LoadingStarted)
        verify(mockEventBus).publish(LoadingEvent.LoadingError)
        verify(mockEventBus).publish(LoadingEvent.LoadingFinished)
    }

    private fun prepareFailureWebResponse() = runBlocking {
        whenever(mockWebService.requestTransmission()).thenThrow(BadResponse(URL("http://empty.url"), 400))
    }
}
