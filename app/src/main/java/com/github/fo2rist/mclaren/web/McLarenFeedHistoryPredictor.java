package com.github.fo2rist.mclaren.web;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.github.fo2rist.mclaren.web.FeedWebService.FeedRequestCallback;
import java.io.IOException;
import java.net.URL;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 * Predictor for page number which should be next after the latest posts.
 * Hack for McLaren web service. App have to guess the page number
 */
public class McLarenFeedHistoryPredictor implements FeedHistoryPredictor, FeedRequestCallback {
    private static class PageStatus {
        final int pageNumber;
        final boolean exists;

        PageStatus(int pageNumber, boolean exists) {
            this.pageNumber = pageNumber;
            this.exists = exists;
        }
    }

    // As of 2017.09.24 it was 454
    // As of 2018.02.07 it was 504
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    static final int LATEST_KNOWN_PAGE = 504;
    private static final LocalDate LATEST_KNOWN_DATE = new LocalDate(2018, 2, 7);
    private static final int APPROXIMATED_DAYS_PER_PAGE = 3;
    private static final int APPROXIMATED_EXTRA_PAGES_BUFFER = 3;

    static final int UNKNOWN_PAGE = -1;

    private final FeedWebService webService;

    /** Newest checked page. */
    private PageStatus currentTop = new PageStatus(LATEST_KNOWN_PAGE * 100, false); //initially page way in the future
    /** Oldest checked page. */
    private PageStatus currentBottom = new PageStatus(LATEST_KNOWN_PAGE, true);
    /** First page that contains items older than latest feed page. */
    private int firstHistoryPage = UNKNOWN_PAGE;
    private boolean isActiveNow = false;

    @Inject
    public McLarenFeedHistoryPredictor(McLarenFeedWebService webService) {
        this.webService = webService;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public boolean isActive() {
        return isActiveNow;
    }

    private void setActive(boolean active) {
        isActiveNow = active;
    }

    /**

     * @return page number if already known or {@link #UNKNOWN_PAGE}.
     */
    @Override
    public int getFirstHistoryPage() {
        return firstHistoryPage;
    }

    /**
     * Start prediction process in the background.
     * At the end if server response value provided by {@link #getFirstHistoryPage()} should be correct.
     */
    @Override
    public void startPrediction() {
        if (isFirstHistoryPageKnown()) {
            return;
        }
        if (isActive()) { //already in progress
            return;
        }
        //once we define a starting point to lookup we just wait for next answer and either shift top or bottom line
        //of possibilities window
        setActive(true);

        requestPage(guessClosestNotExistingPage());
    }

    @Override
    public boolean isFirstHistoryPageKnown() {
        return firstHistoryPage != UNKNOWN_PAGE;
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    int guessClosestNotExistingPage() {
        Days daysBetween = Days.daysBetween(LATEST_KNOWN_DATE, LocalDate.now());

        return LATEST_KNOWN_PAGE + daysBetween.getDays() * APPROXIMATED_DAYS_PER_PAGE + APPROXIMATED_EXTRA_PAGES_BUFFER;
    }

    private void requestPage(int nextPageToAsk) {
        webService.requestFeedPage(nextPageToAsk, this);
    }

    @Override
    public void onSuccess(@NotNull URL url, int requestedPage, int responseCode, @Nullable String data) {
        recordHit(requestedPage);
        analyzeUpdatedState();
    }

    @Override
    public void onFailure(@NotNull URL url, int requestedPage, int responseCode, @Nullable IOException connectionError) {
        if (connectionError != null || responseCode >= 500) {
            //give up on precessing if server gives not valuable response
            setActive(false);
            return;
        }
        recordMiss(requestedPage);
        analyzeUpdatedState();
    }

    private void recordHit(int pageNumber) {
        currentBottom = new PageStatus(pageNumber, true);
    }

    private void recordMiss(int pageNumber) {
        currentTop = new PageStatus(pageNumber, false);
    }

    private void analyzeUpdatedState() {
        if (isFirstPageDetected()) {
            //first page in history is 1 page before the latest one
            firstHistoryPage = currentBottom.pageNumber - 1;
            setActive(false);
        } else {
            //otherwise, bisect
            requestPage((currentBottom.pageNumber + currentTop.pageNumber + 1) / 2);
        }
    }

    private boolean isFirstPageDetected() {
        return (currentTop.pageNumber - currentBottom.pageNumber == 1)
                && !currentTop.exists
                && currentBottom.exists;
    }
}
