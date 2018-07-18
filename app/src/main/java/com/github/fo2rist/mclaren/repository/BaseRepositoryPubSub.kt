package com.github.fo2rist.mclaren.repository

interface BaseRepositoryPubSub {
    fun subscribe(subscriber: Any)

    fun unsubscribe(subscriber: Any)
}
