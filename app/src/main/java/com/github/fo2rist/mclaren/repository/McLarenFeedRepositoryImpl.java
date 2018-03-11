package com.github.fo2rist.mclaren.repository;

import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.web.FeedHistoryPredictor;
import com.github.fo2rist.mclaren.web.FeedWebService;
import com.github.fo2rist.mclaren.web.FeedWebServiceCallback;
import com.github.fo2rist.mclaren.web.McLarenFeedWebService;
import com.github.fo2rist.mclaren.web.models.McLarenFeed;
import com.github.fo2rist.mclaren.web.models.McLarenFeedResponseParser;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.inject.Inject;

/**
 * Provider of the Feed supplied by McLaren own API.
 * McLaren's API doesn't support caching headers.
 * Paging parameter have to be the absolute number of the page vs. typical reverse page numbering where N means N pages
 * from now.
 */
public class McLarenFeedRepositoryImpl implements FeedRepository {
    private static final int UNKNOWN_PAGE = -1;

    final FeedWebService webService;
    final FeedRepositoryPubSub repositoryPubSub;
    final FeedHistoryPredictor historyPredictor;
    final McLarenFeedResponseParser responseParser = new McLarenFeedResponseParser();

    private TreeMap<Long, FeedItem> feedMapById = new TreeMap<>();
    private int lastLoadedPage = UNKNOWN_PAGE;


    @Inject
    McLarenFeedRepositoryImpl(McLarenFeedWebService webService, FeedRepositoryPubSub repositoryPubSub, FeedHistoryPredictor historyPredictor) {
        this.webService = webService;
        this.repositoryPubSub = repositoryPubSub;
        this.historyPredictor = historyPredictor;
    }

    @Override
    public void loadLatest() {
        repositoryPubSub.publish(new PubSubEvents.LoadingStarted());
        webService.requestLatestFeed(webResponseHandler);
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
        if (lastLoadedPage == UNKNOWN_PAGE) {
            pageToLoad = historyPredictor.getFirstHistoryPage();
        } else {
            pageToLoad = lastLoadedPage - 1;
        }
        repositoryPubSub.publish(new PubSubEvents.LoadingStarted());
        webService.requestFeedPage(pageToLoad, webResponseHandler);
    }

    private FeedWebServiceCallback webResponseHandler = new FeedWebServiceCallback() {

        public void onFailure(URL url, int requestedPage, int responseCode, @Nullable IOException connectionError) {
            repositoryPubSub.publish(new PubSubEvents.LoadingError());
            repositoryPubSub.publish(new PubSubEvents.LoadingFinished());
        }

        public void onSuccess(URL url, int requestedPage, int responseCode, @Nullable String data) {
            if (requestedPage >= 0) {
                lastLoadedPage = requestedPage;
            }

            List<FeedItem> feedItems = parse(data);

            if (!feedItems.isEmpty()) {
                List<FeedItem> resultingList = updateFeedItems(feedItems);

                repositoryPubSub.publish(new PubSubEvents.FeedUpdateReady(resultingList));
            }
            repositoryPubSub.publish(new PubSubEvents.LoadingFinished());
        }
    };

    private List<FeedItem> parse(String response) {
        McLarenFeed mcLarenFeed = responseParser.parse(response);
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
