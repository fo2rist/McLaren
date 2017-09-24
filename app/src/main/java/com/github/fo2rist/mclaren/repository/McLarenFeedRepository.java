package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.web.FeedWebsevice;
import com.github.fo2rist.mclaren.web.model.McLarenFeed;
import com.github.fo2rist.mclaren.web.model.McLarenFeedConverter;
import com.github.fo2rist.mclaren.web.model.ResponseParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.inject.Inject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class McLarenFeedRepository implements FeedRepository, Callback {
    //hack for McLaren web service. App have to guess the page number. As of Sep 2017 it's 453
    private static final int DEFAULT_LATEST_PAGE = 453;
    @Inject
    FeedWebsevice websevice;
    @Inject
    FeedRepositoryPubSub repositoryPubSub;

    private TreeMap<Integer, FeedItem> feedMapById = new TreeMap<>();
    private int lastAskedPage = DEFAULT_LATEST_PAGE;

    @Inject
    public McLarenFeedRepository() {
        //empty constructor for injection
    }

    @Override
    public void loadLatest() {
        repositoryPubSub.publish(new PubSubEvents.LoadingStarted());
        websevice.requestFeed(this);
    }

    @Override
    public void loadPrevious() {
        repositoryPubSub.publish(new PubSubEvents.LoadingStarted());
        websevice.requestPreviousFeedPage(lastAskedPage - 1, this);
        lastAskedPage -= 1;
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        //TODO completely get rid of network raw logic here
        processLatestFeedResponse(response);

        repositoryPubSub.publish(new PubSubEvents.LoadingFinished());
    }

    private void processLatestFeedResponse(Response response) throws IOException {
        //TODO distinguish fresh feed and old feed responses. 2017.09.22
        //this response only handles on type of requests results otherwise we'd need distinguishing logic
        if (response.isSuccessful()) {
            List<FeedItem> itemsPortion = parse(response.body().string());
            List<FeedItem> resultingList = updateFeedItems(itemsPortion);

            repositoryPubSub.publish(new PubSubEvents.FeedUpdateReady(resultingList));
        } else {
            repositoryPubSub.publish(new PubSubEvents.LoadingError());
        }
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

    @Override
    public void onFailure(Call call, IOException e) {
        repositoryPubSub.publish(new PubSubEvents.LoadingError());
        repositoryPubSub.publish(new PubSubEvents.LoadingFinished());
    }
}
