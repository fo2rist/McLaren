package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.models.FeedItem;
import java.util.List;

public interface FeedRepository {
    interface Listener {
        void onGetFeed(List<FeedItem> feed);
    }

    void load();
    void subscribe(Listener listener);
    void unsubscribe(Listener listener);
}
