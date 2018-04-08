package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.models.FeedItem;
import java.util.List;

public interface FeedRepositoryPubSub extends BaseRepositoryPubSub {

    void publish(PubSubEvent.FeedUpdateReady event);

    void publish(PubSubEvent.LoadingStarted event);

    void publish(PubSubEvent.LoadingFinished event);

    void publish(PubSubEvent.LoadingError event);

    interface PubSubEvent {
        class FeedUpdateReady {
            public List<FeedItem> feed;

            public FeedUpdateReady(List<FeedItem> feed) {
                this.feed = feed;
            }
        }

        /** Used to notify about networking operation start. */
        class LoadingStarted {
        }

        /** Used to notify about networking operation end with any result success or failure. */
        class LoadingFinished {
        }

        /** Used to notify about networking operation failure. */
        class LoadingError {
        }
    }
}
