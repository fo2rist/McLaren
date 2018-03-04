package com.github.fo2rist.mclaren.repository;

import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

public class FeedRepositoryPubSubImpl implements FeedRepositoryPubSub {

    @Inject
    FeedRepositoryPubSubImpl() {
        //constructor for injection
    }

    public void subscribe(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public void unsubscribe(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    @Override
    public void publish(PubSubEvents.FeedUpdateReady event) {
        publishGeneric(event);
    }

    @Override
    public void publish(PubSubEvents.LoadingStarted event) {
        publishGeneric(event);
    }

    @Override
    public void publish(PubSubEvents.LoadingFinished event) {
        publishGeneric(event);
    }

    @Override
    public void publish(PubSubEvents.LoadingError event) {
        publishGeneric(event);
    }

    public void publishGeneric(Object message) {
        EventBus.getDefault().post(message);
    }
}
