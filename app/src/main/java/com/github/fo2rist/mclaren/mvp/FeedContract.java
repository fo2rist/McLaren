package com.github.fo2rist.mclaren.mvp;

import android.support.annotation.NonNull;

import com.github.fo2rist.mclaren.models.FeedItem;
import java.util.List;

public interface FeedContract {

    /**
     * Feed adapter interaction listener contract.
     */
    interface OnFeedInteractionListener {
        void onItemClicked(@NonNull FeedItem item);

        void onItemSourceClicked(@NonNull FeedItem item);

        void onLinkClicked(@NonNull FeedItem item, @NonNull String link);
    }

    /**
     * Feed adapter scrolling listener contract.
     */
    interface OnFeedScrollingListener {
        void onScrolledToSecondThird();

        void onScrolledToBottom();
    }

    interface View extends BaseView {
        void displayFeed(@NonNull List<FeedItem> feedItems);
        void showProgress();
        void hideProgress();

        void navigateToBrowser(@NonNull String link);
        void navigateToPreview(@NonNull String link);
        void navigateToPreview(@NonNull FeedItem item);
    }

    interface Presenter extends BasePresenter<View>, OnFeedInteractionListener, OnFeedScrollingListener {
        void onRefreshRequested();
    }
}
