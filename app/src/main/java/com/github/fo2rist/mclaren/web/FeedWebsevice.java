package com.github.fo2rist.mclaren.web;

public interface FeedWebsevice {
    /** Request latest posts. */
    void requestLatestFeed(WebCallback callback);

    /** Request specific page from history, which may be latest. */
    void requestFeedPage(int pageNumber, WebCallback callback);

    /** Request specific page HEAD. Same as {@link #requestFeedPage(int, WebCallback)} but always return empty body. */
    void requestFeedPageHead(int pageNumber, WebCallback callback);
}
