package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.repository.TransmissionRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.testdata.REAL_TRANSMISSION_RESPONSE
import com.github.fo2rist.mclaren.web.TransmissionWebService
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
    fun test_onSuccess_firesLoadFinishEvents() {
        prepareRealWebResponse()

        repository.loadTransmission()

        verify(mockEventBus).publish(LoadingEvent.LoadingStarted)
        verify(mockEventBus).publish(LoadingEvent.LoadingFinished)
        //The last event is matched by the parent class so we count two previous calls
        verify(mockEventBus).publish(any<LoadingEvent.TransmissionUpdateReady>())
    }

    @Test
    fun test_loadRepository_respondWithCachesItems_ifPresent() {
        //load for the first time
        prepareRealWebResponse()
        repository.loadTransmission()
        reset(mockEventBus)
        reset(mockWebService)

        //load when cache is non empty
        repository.loadTransmission()

        verify(mockEventBus).publish(LoadingEvent.LoadingStarted)
        verify(mockEventBus).publish(LoadingEvent.LoadingFinished)
        verify(mockEventBus, times(2)).publish(any<LoadingEvent.TransmissionUpdateReady>())
    }

    private fun prepareRealWebResponse()= runBlocking {
        whenever(mockWebService.requestTransmission()).thenReturn(REAL_TRANSMISSION_RESPONSE)
    }
}
