package com.github.fo2rist.mclaren.repository

/**
 * A event bus that supports subscription.
 */
interface EventBusConnector {

    fun subscribe(subscriber: Any)

    fun unsubscribe(subscriber: Any)
}
