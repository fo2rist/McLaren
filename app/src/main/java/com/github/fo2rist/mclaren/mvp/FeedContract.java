package com.github.fo2rist.mclaren.mvp;

import androidx.annotation.NonNull;

import com.github.fo2rist.mclaren.models.FeedItem;
import java.util.List;

public interface FeedContract {

    /**
     * Feed adapter interaction listener contract.
     */
    interface OnFeedInteractionListener {
        /** Called when feed item itself is clicked. */
        void onItemClicked(@NonNull FeedItem item);

        /** Called when item's source social network link clicked. */
        void onItemSourceClicked(@NonNull FeedItem item);

        /** Called when http/mention/hashtag link in the text clicked. */
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

    interface Presenter extends BasePresenter<View>, Stoppable, OnFeedInteractionListener, OnFeedScrollingListener {
        /**
         * Set account for the presenter.
         * MUST be called prior to onStart if the service has the notion of account.
         */
        void setAccount(@NonNull String account);
        void onStart();
        void onRefreshRequested();
    }
}
