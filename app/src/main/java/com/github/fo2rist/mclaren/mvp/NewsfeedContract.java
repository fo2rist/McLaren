package com.github.fo2rist.mclaren.mvp;

import com.github.fo2rist.mclaren.models.FeedItem;
import java.util.List;

public interface NewsfeedContract {
    interface View extends BaseView {
        void setFeed(List<FeedItem> feedItems);
        void hideProgress();
    }

    interface Presenter extends BasePresenter<View> {
        void onRefreshRequested();
        void onLoadMoreRequested();
    }
}
