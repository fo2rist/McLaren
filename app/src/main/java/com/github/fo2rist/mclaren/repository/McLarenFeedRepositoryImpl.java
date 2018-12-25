package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.repository.converters.McLarenFeedConverter;
import com.github.fo2rist.mclaren.web.FeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.McLarenFeedWebService;
import com.github.fo2rist.mclaren.web.SafeJsonParser;
import com.github.fo2rist.mclaren.web.models.McLarenFeed;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Provider of the Feed supplied by McLaren own API.
 * McLaren's API doesn't support caching headers.
 * Paging parameter have to be the absolute number of the page vs. typical reverse page numbering where N means N pages
 * from now.
 */
@Singleton
public class McLarenFeedRepositoryImpl extends BaseFeedRepository<McLarenFeed> {
    private int lastLoadedPage = UNKNOWN_PAGE;
    private final FeedHistoryPredictor historyPredictor;

    @Inject
    McLarenFeedRepositoryImpl(
            McLarenFeedWebService webService,
            FeedRepositoryEventBus repositoryEventBus,
            FeedHistoryPredictor historyPredictor) {
        super(webService, McLarenFeedConverter.INSTANCE, repositoryEventBus, new SafeJsonParser<>(McLarenFeed.class));
        this.historyPredictor = historyPredictor;
    }

    @Override
    public final void prepareForHistoryLoading() {
        if (!historyPredictor.isFirstHistoryPageKnown()) {
            historyPredictor.startPrediction();
        }
        //else we're ready
    }

    @Override
    protected int getNextPageNumber() {
        if (!historyPredictor.isFirstHistoryPageKnown()) {
            historyPredictor.startPrediction();
            return UNKNOWN_PAGE;
        } else if (lastLoadedPage == UNKNOWN_PAGE) {
            return historyPredictor.getFirstHistoryPage();
        } else {
            return lastLoadedPage - 1;
        }
    }

    @Override
    protected void onPageLoaded(int page) {
        lastLoadedPage = page;
    }
}
