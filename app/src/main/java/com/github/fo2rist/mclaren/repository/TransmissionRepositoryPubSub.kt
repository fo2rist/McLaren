package com.github.fo2rist.mclaren.repository

interface TransmissionRepositoryPubSub : BaseRepositoryPubSub {
    fun publish(event: PubSubEvent)

    interface PubSubEvent {
        data class TransmissionUpdateReady(val obj: Any) : PubSubEvent
        object LoadingStarted : PubSubEvent
        object LoadingFinished : PubSubEvent
        object LoadingError : PubSubEvent
    }
}
