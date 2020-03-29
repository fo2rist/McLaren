package com.github.fo2rist.mclaren.web.feed;

public interface FeedHistoryPredictor {
    int getFirstHistoryPage();

    void startPrediction();

    boolean isFirstHistoryPageKnown();
}
