package com.github.fo2rist.mclaren.mvp;

import com.github.fo2rist.mclaren.models.FeedItem;
import java.util.List;

public interface FeedContract {

    /**
     * Feed adapter interaction listener contract.
     */
    interface OnFeedInteractionListener {
        void onItemClicked(FeedItem item);

        void onItemSourceClicked(FeedItem item);

        void onLinkClicked(FeedItem item, String link);
    }

    /**
     * Feed adapter scrolling listener contract.
     */
    interface OnFeedScrollingListener {
        void onScrolledToSecondThird();

        void onScrolledToBottom();
    }

    interface View extends BaseView {
        void displayFeed(List<FeedItem> feedItems);
        void showProgress();
        void hideProgress();

        void navigateToBrowser(String link);
        void navigateToPreview(FeedItem item);
    }

    interface Presenter extends BasePresenter<View>, OnFeedInteractionListener, OnFeedScrollingListener {
        void onRefreshRequested();
    }
}
