package com.github.fo2rist.mclaren.repository;

import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.web.FeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.FeedWebsevice;
import com.github.fo2rist.mclaren.web.WebCallback;
import com.github.fo2rist.mclaren.web.models.McLarenFeed;
import com.github.fo2rist.mclaren.web.models.McLarenFeedConverter;
import com.github.fo2rist.mclaren.web.models.ResponseParser;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.inject.Inject;

public class McLarenFeedRepository implements FeedRepository, WebCallback {

    private static final int PAGE_UNKNOWN = -1;

    final FeedWebsevice websevice;
    final FeedRepositoryPubSub repositoryPubSub;
    final FeedHistoryPredictor historyPredictor;

    private TreeMap<Integer, FeedItem> feedMapById = new TreeMap<>();
    private int lastLoadedPage = PAGE_UNKNOWN;

    @Inject
    McLarenFeedRepository(FeedWebsevice websevice, FeedRepositoryPubSub repositoryPubSub, FeedHistoryPredictor historyPredictor) {
        this.websevice = websevice;
        this.repositoryPubSub = repositoryPubSub;
        this.historyPredictor = historyPredictor;
    }

    @Override
    public void loadLatest() {
        repositoryPubSub.publish(new PubSubEvents.LoadingStarted());
        websevice.requestLatestFeed(this);
    }

    @Override
    public void prepareForLoading() {
        if (!historyPredictor.isFirstHistoryPageKnown()) {
            historyPredictor.startPrediction();
        }
        //else we're ready
    }

    @Override
    public void loadPrevious() {
        if (!historyPredictor.isFirstHistoryPageKnown()) {
            historyPredictor.startPrediction();
            return;
        }

        int pageToLoad;
        if (lastLoadedPage == PAGE_UNKNOWN) {
            pageToLoad = historyPredictor.getFirstHistoryPage();
        } else {
            pageToLoad = lastLoadedPage - 1;
        }
        repositoryPubSub.publish(new PubSubEvents.LoadingStarted());
        websevice.requestFeedPage(pageToLoad, this);
    }

    @Override
    public void onFailure(URL url, int requestedPage, int responseCode, @Nullable IOException connectionError) {
        repositoryPubSub.publish(new PubSubEvents.LoadingError());
        repositoryPubSub.publish(new PubSubEvents.LoadingFinished());
    }

    @Override
    public void onSuccess(URL url, int requestedPage, int responseCode, @Nullable String data) {
        if (requestedPage >= 0) {
            lastLoadedPage = requestedPage;
        }

        List<FeedItem> newFeedItems = parse(data);
        if (!newFeedItems.isEmpty()) {
            List<FeedItem> resultingList = updateFeedItems(newFeedItems);

            repositoryPubSub.publish(new PubSubEvents.FeedUpdateReady(resultingList));
        }
        repositoryPubSub.publish(new PubSubEvents.LoadingFinished());
    }

    private List<FeedItem> parse(String response) {
        McLarenFeed mcLarenFeed = ResponseParser.parseFeed(response);
        return McLarenFeedConverter.convertFeed(mcLarenFeed);
    }

    /**
     * Add portion of items to main collection.
     * @return resulting items as a list.
     */
    private List<FeedItem> updateFeedItems(List<FeedItem> itemsPortion) {
        for (FeedItem item : itemsPortion) {
            feedMapById.put(item.id, item);
        }
        ArrayList<FeedItem> resultingList = new ArrayList<>();
        resultingList.addAll(feedMapById.descendingMap().values());
        return resultingList;
    }
}
