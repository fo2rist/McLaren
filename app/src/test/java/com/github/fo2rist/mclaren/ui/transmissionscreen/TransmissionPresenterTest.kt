package com.github.fo2rist.mclaren.ui.transmissionscreen

import com.github.fo2rist.mclaren.mvp.TransmissionContract
import com.github.fo2rist.mclaren.repository.TransmissionRepository
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryEventBus
import com.github.fo2rist.mclaren.testutilities.anyKotlinObject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class TransmissionPresenterTest {
    private lateinit var presenter: TransmissionPresenter
    private lateinit var mockRepository: TransmissionRepository
    private lateinit var mockEventBus: TransmissionRepositoryEventBus
    private lateinit var mockView: TransmissionContract.View

    @Before
    fun setUp() {
        mockView = mock(TransmissionContract.View::class.java)
        mockRepository = mock(TransmissionRepository::class.java)
        mockEventBus = mock(TransmissionRepositoryEventBus::class.java)
        presenter = TransmissionPresenter(mockRepository, mockEventBus)
    }

    @Test
    fun `test onStart loadsTransmission and subscribeOnEvents`() {
        presenter.onStart(mockView)

        verify(mockRepository).loadTransmission()
        verify(mockEventBus).subscribe(anyKotlinObject())
    }

    @Test
    fun `test onLoadingStarted showsProgress`() {
        setupPresenter()

        presenter.onLoadingStarted(TransmissionRepositoryEventBus.LoadingEvent.LoadingStarted)

        verify(mockView).showProgress()
    }


    @Test
    fun `test onLoadingFinished hidesProgress`() {
        setupPresenter()

        presenter.onLoadingFinished(TransmissionRepositoryEventBus.LoadingEvent.LoadingFinished)

        verify(mockView).hideProgress()
    }

    @Test
    fun `test onStop unsubscribeOfEvents`() {
        setupPresenter()

        presenter.onStop()

        verify(mockEventBus).unsubscribe(anyKotlinObject())
    }

    private fun setupPresenter() {
        presenter.onStart(mockView)
        reset(mockEventBus)
        reset(mockRepository)
        reset(mockView)
    }
}
