package com.github.fo2rist.mclaren.repository;

public interface FeedRepository {
    void loadLatest();

    void prepareForLoading();

    void loadPrevious();
}
