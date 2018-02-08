package com.github.fo2rist.mclaren.web;

public interface FeedWebsevice {
    /** Request latest posts. */
    void requestLatestFeed(WebCallback callback);

    /** Request specific page from history, which may be latest. */
    void requestFeedPage(int pageNumber, WebCallback callback);

}
