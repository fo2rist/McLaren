package com.github.fo2rist.mclaren.repository;

public interface FeedRepositoryPubSub {
    void subscribe(Object subscriber);

    void unsubscribe(Object subscriber);

    void publish(PubSubEvents.FeedUpdateReady event);

    void publish(PubSubEvents.LoadingStarted event);

    void publish(PubSubEvents.LoadingFinished event);

    void publish(PubSubEvents.LoadingError event);
}
