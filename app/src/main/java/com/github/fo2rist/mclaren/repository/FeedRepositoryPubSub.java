package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.models.FeedItem;
import java.util.List;

public interface FeedRepositoryPubSub extends BaseRepositoryPubSub {

    void publish(PubSubEvent event);

    interface PubSubEvent {
        class FeedUpdateReady implements PubSubEvent {
            public List<FeedItem> feed;

            public FeedUpdateReady(List<FeedItem> feed) {
                this.feed = feed;
            }
        }

        /** Used to notify about networking operation start. */
        class LoadingStarted implements PubSubEvent {
        }

        /** Used to notify about networking operation end with any result success or failure. */
        class LoadingFinished implements PubSubEvent {
        }

        /** Used to notify about networking operation failure. */
        class LoadingError implements PubSubEvent {
        }
    }
}
