package com.github.fo2rist.mclaren.repository;

import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.web.FeedWebService;
import com.github.fo2rist.mclaren.web.FeedWebServiceCallback;
import com.github.fo2rist.mclaren.web.StoryStreamWebServiceImpl;
import com.github.fo2rist.mclaren.web.models.StoryStream;
import com.github.fo2rist.mclaren.web.models.StoryStreamResponseParser;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Provider of the "Stories" feed supplied by storystream.it service.
 * StoryStream supports caching headers and standard pagination.
 */
@Singleton
public class StoryStreamRepositoryImpl implements StoryStreamRepository {
    final FeedWebService webService;
    final FeedRepositoryPubSub repositoryPubSub;
    final StoryStreamResponseParser responseParser = new StoryStreamResponseParser();

    private TreeMap<Long, FeedItem> feedMapById = new TreeMap<>();
    private int lastLoadedPage = 1; //latest page st StoryStream is the same as Page=1

    @Inject
    public StoryStreamRepositoryImpl(StoryStreamWebServiceImpl webService, FeedRepositoryPubSub repositoryPubSub) {
        this.webService = webService;
        this.repositoryPubSub = repositoryPubSub;
    }

    @Override
    public void loadLatest() {
        webService.requestLatestFeed(webResponseHandler);
    }

    @Override
    public void prepareForHistoryLoading() {
        //no preparation needed for Story Stream service
    }

    @Override
    public void loadNextHistory() {
        int pageToLoad = lastLoadedPage + 1;
        webService.requestFeedPage(pageToLoad, webResponseHandler);
    }

    private FeedWebServiceCallback webResponseHandler = new FeedWebServiceCallback() {

        public void onFailure(URL url, int requestedPage, int responseCode, @Nullable IOException connectionError) {
            repositoryPubSub.publish(new PubSubEvents.LoadingError());
            repositoryPubSub.publish(new PubSubEvents.LoadingFinished()); //TODO remove duplication with MCL feed
        }

        public void onSuccess(URL url, int requestedPage, int responseCode, @Nullable String data) {
            if (requestedPage >= 0) {
                lastLoadedPage = requestedPage;
            }

            List<FeedItem> feedItems = parse(data);
            if (!feedItems.isEmpty()) {
                List<FeedItem> resultingList = updateFeedItems(feedItems); //TODO remove duplication with MCL feed

                repositoryPubSub.publish(new PubSubEvents.FeedUpdateReady(resultingList));
            }
            repositoryPubSub.publish(new PubSubEvents.LoadingFinished());
        }
    };

    private List<FeedItem> parse(@Nullable String data) {
        StoryStream storyStreamItems = responseParser.parse(data);
        return StoryStreamConverter.convertFeed(storyStreamItems);
    }

    private List<FeedItem> updateFeedItems(List<FeedItem> itemsPortion) {
        for (FeedItem item : itemsPortion) {
            feedMapById.put(item.id, item);
        }
        ArrayList<FeedItem> resultingList = new ArrayList<>();
        resultingList.addAll(feedMapById.descendingMap().values());
        return resultingList;
    }
}
