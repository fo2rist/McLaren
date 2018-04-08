package com.github.fo2rist.mclaren.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.repository.FeedRepositoryPubSub.PubSubEvent;
import com.github.fo2rist.mclaren.web.FeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.FeedWebService;
import com.github.fo2rist.mclaren.web.FeedWebService.FeedRequestCallback;
import com.github.fo2rist.mclaren.web.McLarenFeedWebService;
import com.github.fo2rist.mclaren.web.models.McLarenFeed;
import com.github.fo2rist.mclaren.web.models.McLarenFeedResponseParser;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Provider of the Feed supplied by McLaren own API.
 * McLaren's API doesn't support caching headers.
 * Paging parameter have to be the absolute number of the page vs. typical reverse page numbering where N means N pages
 * from now.
 */
@Singleton
public class McLarenFeedRepositoryImpl implements FeedRepository {
    private static final int UNKNOWN_PAGE = -1;

    final FeedWebService webService;
    final FeedRepositoryPubSub repositoryPubSub;
    final FeedHistoryPredictor historyPredictor;
    final McLarenFeedResponseParser responseParser = new McLarenFeedResponseParser();

    private TreeSet<FeedItem> feedItems = new TreeSet<>();
    private int lastLoadedPage = UNKNOWN_PAGE;


    @Inject
    McLarenFeedRepositoryImpl(McLarenFeedWebService webService, FeedRepositoryPubSub repositoryPubSub, FeedHistoryPredictor historyPredictor) {
        this.webService = webService;
        this.repositoryPubSub = repositoryPubSub;
        this.historyPredictor = historyPredictor;
    }

    @Override
    public void loadLatest() {
        publishCachedFeed(); // publish cached data to respond immediately and then load
        repositoryPubSub.publish(new PubSubEvent.LoadingStarted());
        webService.requestLatestFeed(webResponseHandler);
    }

    private void publishCachedFeed() {
        if (!feedItems.isEmpty()) {
            repositoryPubSub.publish(new PubSubEvent.FeedUpdateReady(getFeedItemsAsList()));
        }
    }

    @Override
    public void prepareForHistoryLoading() {
        if (!historyPredictor.isFirstHistoryPageKnown()) {
            historyPredictor.startPrediction();
        }
        //else we're ready
    }

    @Override
    public void loadNextHistory() {
        if (!historyPredictor.isFirstHistoryPageKnown()) {
            historyPredictor.startPrediction();
            return;
        }

        int pageToLoad;
        if (lastLoadedPage == UNKNOWN_PAGE) {
            pageToLoad = historyPredictor.getFirstHistoryPage();
        } else {
            pageToLoad = lastLoadedPage - 1;
        }
        repositoryPubSub.publish(new PubSubEvent.LoadingStarted());
        webService.requestFeedPage(pageToLoad, webResponseHandler);
    }

    private FeedRequestCallback webResponseHandler = new FeedRequestCallback() {

        public void onFailure(URL url, int requestedPage, int responseCode, @Nullable IOException connectionError) {
            repositoryPubSub.publish(new PubSubEvent.LoadingError());
            repositoryPubSub.publish(new PubSubEvent.LoadingFinished());
        }

        public void onSuccess(URL url, int requestedPage, int responseCode, @Nullable String data) {
            if (requestedPage >= 0) {
                lastLoadedPage = requestedPage;
            }

            List<FeedItem> feedItems = parse(data);
            if (!feedItems.isEmpty()) {
                addNewItems(feedItems);

                repositoryPubSub.publish(new PubSubEvent.FeedUpdateReady(getFeedItemsAsList()));
            }
            repositoryPubSub.publish(new PubSubEvent.LoadingFinished());
        }
    };

    private List<FeedItem> parse(@Nullable String response) {
        McLarenFeed mcLarenFeed = responseParser.parse(response);
        return McLarenFeedConverter.convertFeed(mcLarenFeed);
    }

    private void addNewItems(List<FeedItem> itemsPortion) {
        feedItems.addAll(itemsPortion);
    }

    @NonNull
    private List<FeedItem> getFeedItemsAsList() {
        return new ArrayList<>(feedItems.descendingSet());
    }
}
