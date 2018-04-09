package com.github.fo2rist.mclaren.ui.transmissionscreen

import com.github.fo2rist.mclaren.mvp.TransmissionContract
import com.github.fo2rist.mclaren.repository.TransmissionRepository
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryPubSub
import com.github.fo2rist.mclaren.utils.custommatchers.anyKotlinObject
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
    private lateinit var mockPubSub: TransmissionRepositoryPubSub
    private lateinit var mockView: TransmissionContract.View

    @Before
    fun setUp() {
        mockView = mock(TransmissionContract.View::class.java)
        mockRepository = mock(TransmissionRepository::class.java)
        mockPubSub = mock(TransmissionRepositoryPubSub::class.java)
        presenter = TransmissionPresenter(mockRepository, mockPubSub)
    }


    @Test
    fun test_onStart_loadsTransmission_and_subscribeOnEvents() {
        presenter.onStart(mockView)

        verify(mockRepository).loadTransmission()
        verify(mockPubSub).subscribe(anyKotlinObject())
    }

    @Test
    fun test_onLoadingStarted_showsProgress() {
        setupPresenter()

        presenter.onLoadingStarted(TransmissionRepositoryPubSub.PubSubEvent.LoadingStarted)

        verify(mockView).showProgress()
    }


    @Test
    fun test_onLoadingFinished_hidesProgress() {
        setupPresenter()

        presenter.onLoadingFinished(TransmissionRepositoryPubSub.PubSubEvent.LoadingFinished)

        verify(mockView).hideProgress()
    }

    @Test
    fun test_onStop_unsubscribeOfEvents() {
        setupPresenter()

        presenter.onStop()

        verify(mockPubSub).unsubscribe(anyKotlinObject())
    }

    private fun setupPresenter() {
        presenter.onStart(mockView)
        reset(mockPubSub)
        reset(mockRepository)
        reset(mockView)
    }
}
