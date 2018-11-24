package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.models.FeedItem

/**
 * Event bus for Feed repository.
 * Can notify about feed loading events.
 */
interface FeedRepositoryEventBus : EventBusConnector {

    /** Send event to all active subscribers. */
    fun publish(event: LoadingEvent)

    /** [FeedRepositoryEventBus] event. */
    interface LoadingEvent {
        /** Loading result event. */
        data class FeedUpdateReady(var feed: List<FeedItem>) : LoadingEvent

        /** Networking operation failure. */
        class LoadingError : LoadingEvent

        /**
         * Networking operation start event.
         * Should be sent before any other event.
         */
        class LoadingStarted : LoadingEvent

        /**
         * Networking operation end with success or failure.
         * Should be sent after [FeedUpdateReady] or [LoadingError]
         */
        class LoadingFinished : LoadingEvent
    }
}
