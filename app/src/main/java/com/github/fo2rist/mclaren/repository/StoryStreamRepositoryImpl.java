package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent;
import com.github.fo2rist.mclaren.repository.converters.StoryStreamConverter;
import com.github.fo2rist.mclaren.web.SafeJsonParser;
import com.github.fo2rist.mclaren.web.StoryStreamWebService;
import com.github.fo2rist.mclaren.web.models.StoryStream;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Provider of the "Stories" feed supplied by storystream.it service.
 * StoryStream supports caching headers and standard pagination.
 */
@Singleton
public class StoryStreamRepositoryImpl extends BaseFeedRepository<StoryStream> {
    private int lastLoadedPage = 1; //latest page's number StoryStream is 1, no prameter request and Page=1 are equals

    @Inject
    StoryStreamRepositoryImpl(
            StoryStreamWebService webService,
            FeedRepositoryEventBus repositoryEventBus) {
        super(webService, StoryStreamConverter.INSTANCE, repositoryEventBus, new SafeJsonParser<>(StoryStream.class));
    }

    @Override
    public final void prepareForHistoryLoading() {
        //no preparation needed for Story Stream service
    }

    @Override
    public final void loadNextPage() {
        int pageToLoad = lastLoadedPage + 1;
        repositoryEventBus.publish(new LoadingEvent.LoadingStarted());
        webService.requestFeedPage(pageToLoad, getWebResponseHandler());
    }

    @Override
    protected void onPageLoaded(int page) {
        lastLoadedPage = page;
    }
}
