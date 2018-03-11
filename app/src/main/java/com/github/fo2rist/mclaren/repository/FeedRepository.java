package com.github.fo2rist.mclaren.repository;

/**
 * Supplier of the news feed.
 */
public interface FeedRepository {
    /** Request the latest entries from feed. */
    void loadLatest();

    /** Warm up repository to load the next page from history. */
    void prepareForHistoryLoading();

    /** Requests the next portion of not loaded yet items in feed. */
    void loadNextHistory();
}
