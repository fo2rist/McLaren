package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.repository.feed.FeedRepositoryEventBus
import com.github.fo2rist.mclaren.repository.transmission.TransmissionRepositoryEventBus
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Event Bus implementation for both Feed & Transmission events.
 * Delegated subscription and delivery to [EventBus] library.
 */
@Singleton
class RepositoryEventBusImpl @Inject constructor() : FeedRepositoryEventBus, TransmissionRepositoryEventBus {

    override fun subscribe(subscriber: Any) = EventBus.getDefault().register(subscriber)

    override fun unsubscribe(subscriber: Any) = EventBus.getDefault().unregister(subscriber)

    override fun publish(event: FeedRepositoryEventBus.LoadingEvent) = publishGeneric(event)

    override fun publish(event: TransmissionRepositoryEventBus.LoadingEvent) = publishGeneric(event)

    private fun publishGeneric(message: Any) = EventBus.getDefault().post(message)
}
