package com.github.fo2rist.mclaren.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.repository.FeedRepositoryEventBus.LoadingEvent;
import com.github.fo2rist.mclaren.web.FeedWebService;
import com.github.fo2rist.mclaren.web.FeedWebService.FeedRequestCallback;
import com.github.fo2rist.mclaren.web.StoryStreamWebService;
import com.github.fo2rist.mclaren.web.models.StoryStream;
import com.github.fo2rist.mclaren.web.models.StoryStreamResponseParser;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Provider of the "Stories" feed supplied by storystream.it service.
 * StoryStream supports caching headers and standard pagination.
 */
@Singleton
public class StoryStreamRepositoryImpl implements StoryStreamRepository {
    private final FeedWebService webService;
    private final FeedRepositoryEventBus repositoryEventBus;
    private final StoryStreamResponseParser responseParser = new StoryStreamResponseParser();

    private TreeSet<FeedItem> feedItems = new TreeSet<>();
    private int lastLoadedPage = 1; //latest page st StoryStream is the same as Page=1

    @Inject
    public StoryStreamRepositoryImpl(StoryStreamWebService webService, FeedRepositoryEventBus repositoryEventBus) {
        this.webService = webService;
        this.repositoryEventBus = repositoryEventBus;
    }

    @Override
    public void loadLatest() {
        publishCachedFeed(); // publish cached data to respond immediately and then load
        repositoryEventBus.publish(new LoadingEvent.LoadingStarted());
        webService.requestLatestFeed(webResponseHandler);
    }

    private void publishCachedFeed() {
        if (!feedItems.isEmpty()) {
            repositoryEventBus.publish(new LoadingEvent.FeedUpdateReady(getFeedItemsAsList()));
        }
    }

    @Override
    public void prepareForHistoryLoading() {
        //no preparation needed for Story Stream service
    }

    @Override
    public void loadNextHistory() {
        int pageToLoad = lastLoadedPage + 1;
        repositoryEventBus.publish(new LoadingEvent.LoadingStarted());
        webService.requestFeedPage(pageToLoad, webResponseHandler);
    }

    private FeedRequestCallback webResponseHandler = new FeedRequestCallback() {

        public void onFailure(URL url, int requestedPage, int responseCode, @Nullable IOException connectionError) {
            repositoryEventBus.publish(new LoadingEvent.LoadingError());
            repositoryEventBus.publish(new LoadingEvent.LoadingFinished()); //TODO remove duplication with MCL feed
        }

        public void onSuccess(URL url, int requestedPage, int responseCode, @Nullable String data) {
            if (requestedPage >= 0) {
                lastLoadedPage = requestedPage;
            }

            List<FeedItem> feedItems = parse(data);
            if (!feedItems.isEmpty()) {
                addNewItems(feedItems); //TODO remove duplication with MCL feed

                repositoryEventBus.publish(new LoadingEvent.FeedUpdateReady(getFeedItemsAsList()));
            }
            repositoryEventBus.publish(new LoadingEvent.LoadingFinished());
        }
    };

    private List<FeedItem> parse(@Nullable String data) {
        StoryStream storyStreamItems = responseParser.parse(data);
        return StoryStreamConverter.convertFeed(storyStreamItems);
    }

    private void addNewItems(List<FeedItem> itemsPortion) {
        feedItems.addAll(itemsPortion);
    }

    @NonNull
    private List<FeedItem> getFeedItemsAsList() {
        return new ArrayList<>(feedItems.descendingSet());
    }
}
