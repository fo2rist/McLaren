package com.github.fo2rist.mclaren.repository.feed;

import com.github.fo2rist.mclaren.repository.converters.FeedConverter;
import com.github.fo2rist.mclaren.web.feed.StoryStreamWebService;
import com.github.fo2rist.mclaren.web.models.StoryStream;
import com.github.fo2rist.mclaren.web.utils.SafeJsonParser;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Provider of the "Stories" feed supplied by storystream.it service.
 * StoryStream supports caching headers and standard pagination.
 */
@Singleton
public class StoryStreamRepositoryImpl extends BaseFeedRepository<StoryStream> {
    private int lastLoadedPage = 1; //latest page's number StoryStream is 1, no parameter request and Page=1 are equals

    @Inject
    StoryStreamRepositoryImpl(
            StoryStreamWebService webService,
            FeedRepositoryEventBus repositoryEventBus,
            FeedConverter<StoryStream> converter
    ) {
        super(webService, converter, repositoryEventBus, new SafeJsonParser<>(StoryStream.class));
    }

    @Override
    public final void prepareForHistoryLoading() {
        //no preparation needed for Story Stream service
    }

    @Override
    protected int getNextPageNumber() {
        return lastLoadedPage + 1;
    }

    @Override
    protected void onPageLoaded(int page) {
        lastLoadedPage = page;
    }
}
