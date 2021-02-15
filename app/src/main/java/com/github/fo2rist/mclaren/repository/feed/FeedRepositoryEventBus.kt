package com.github.fo2rist.mclaren.repository.feed

import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.repository.EventBusConnector

/**
 * Event bus for Feed repository.
 * Can notify about feed loading events.
 */
interface FeedRepositoryEventBus : EventBusConnector {

    /** Send event to all active subscribers. */
    fun publish(event: LoadingEvent)

    /**
     * [FeedRepositoryEventBus] event.
     * Each contains the account ID the event belongs to and optional data.
     */
    interface LoadingEvent {
        /** Loading result event. */
        data class FeedUpdateReady(val account: String, val feed: List<FeedItem>) : LoadingEvent

        /** Networking operation failure. */
        class LoadingError(val account: String) : LoadingEvent

        /**
         * Networking operation start event.
         * Should be sent before any other event.
         */
        class LoadingStarted(val account: String) : LoadingEvent

        /**
         * Networking operation end with success or failure.
         * Should be sent after [FeedUpdateReady] or [LoadingError]
         */
        class LoadingFinished(val account: String) : LoadingEvent
    }
}
