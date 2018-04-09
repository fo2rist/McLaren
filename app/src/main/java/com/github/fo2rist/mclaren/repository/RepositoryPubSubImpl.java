package com.github.fo2rist.mclaren.repository;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

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
    public void publish(FeedRepositoryPubSub.PubSubEvent event) {
        publishGeneric(event);
    }

    @Override
    public void publish(@NotNull TransmissionRepositoryPubSub.PubSubEvent event) {
        publishGeneric(event);
    }

    private void publishGeneric(Object message) {
        EventBus.getDefault().post(message);
    }
}
