package com.github.fo2rist.mclaren.repository;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.greenrobot.eventbus.EventBus;

@Singleton
public class RepositoryPubSubImpl implements FeedRepositoryPubSub, TransmissionRepositoryPubSub {

    @Inject
    RepositoryPubSubImpl() {
        //constructor for injection
    }

    public void subscribe(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public void unsubscribe(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    @Override
    public void publish(PubSubEvent.FeedUpdateReady event) {
        publishGeneric(event);
    }

    @Override
    public void publish(PubSubEvent.LoadingStarted event) {
        publishGeneric(event);
    }

    @Override
    public void publish(PubSubEvent.LoadingFinished event) {
        publishGeneric(event);
    }

    @Override
    public void publish(PubSubEvent.LoadingError event) {
        publishGeneric(event);
    }

    private void publishGeneric(Object message) {
        EventBus.getDefault().post(message);
    }
}
