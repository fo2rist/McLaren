package com.github.fo2rist.mclaren.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent;
import com.github.fo2rist.mclaren.web.SafeJsonParser;
import com.github.fo2rist.mclaren.web.StoryStreamWebService;
import com.github.fo2rist.mclaren.web.models.StoryStream;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Provider of the "Stories" feed supplied by storystream.it service.
 * StoryStream supports caching headers and standard pagination.
 */
@Singleton
public class StoryStreamRepositoryImpl extends BaseFeedRepository<StoryStream> {
    private int lastLoadedPage = 1; //latest page st StoryStream is the same as Page=1

    @Inject
    StoryStreamRepositoryImpl(StoryStreamWebService webService, FeedRepositoryEventBus repositoryEventBus) {
        super(webService, repositoryEventBus, new SafeJsonParser<>(StoryStream.class));
    }

    @Override
    public final void prepareForHistoryLoading() {
        //no preparation needed for Story Stream service
    }

    @Override
    public final void loadNextHistory() {
        int pageToLoad = lastLoadedPage + 1;
        repositoryEventBus.publish(new LoadingEvent.LoadingStarted());
        webService.requestFeedPage(pageToLoad, getWebResponseHandler());
    }

    @NonNull
    @Override
    protected List<FeedItem> parse(@Nullable String data) {
        StoryStream storyStreamItems = responseParser.parse(data);
        return StoryStreamConverter.convertFeed(storyStreamItems);
    }

    @Override
    protected void setLastLoadedPage(int page) {
        lastLoadedPage = page;
    }
}
