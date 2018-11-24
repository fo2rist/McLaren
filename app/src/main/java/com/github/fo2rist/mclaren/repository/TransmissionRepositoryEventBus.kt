package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.models.TransmissionInfo

/**
 * Even bus for Transmission repository.
 * Can notify about transmission loading events.
 */
interface TransmissionRepositoryEventBus : EventBusConnector {

    /** Send event to all active subscribers. */
    fun publish(event: LoadingEvent)

    /** [TransmissionRepositoryEventBus] event. */
    interface LoadingEvent {
        /** Loading result event. */
        data class TransmissionUpdateReady(val data: TransmissionInfo) : LoadingEvent

        /** Networking operation failure. */
        object LoadingError : LoadingEvent

        /**
         * Networking operation start event.
         * Should be sent before any other event.
         */
        object LoadingStarted : LoadingEvent

        /**
         * Networking operation end with success or failure.
         * Should be sent after [TransmissionUpdateReady] or [LoadingError]
         */
        object LoadingFinished : LoadingEvent
    }
}
