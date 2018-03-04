package com.github.fo2rist.mclaren.repository;

/**
 * Supplier of the news feed.
 */
public interface FeedRepository {
    void loadLatest();

    void prepareForLoading();

    void loadPrevious();
}
