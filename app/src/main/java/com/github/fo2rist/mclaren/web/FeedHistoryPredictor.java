package com.github.fo2rist.mclaren.web;

public interface FeedHistoryPredictor {
    int getFirstHistoryPage();

    void startPrediction();

    boolean isFirstHistoryPageKnown();
}
