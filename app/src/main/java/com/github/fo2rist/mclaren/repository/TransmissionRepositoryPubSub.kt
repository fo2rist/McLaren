package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.models.TransmissionInfo

interface TransmissionRepositoryPubSub : BaseRepositoryPubSub {
    fun publish(event: PubSubEvent)

    interface PubSubEvent {
        data class TransmissionUpdateReady(val data: TransmissionInfo) : PubSubEvent
        object LoadingStarted : PubSubEvent
        object LoadingFinished : PubSubEvent
        object LoadingError : PubSubEvent
    }
}
