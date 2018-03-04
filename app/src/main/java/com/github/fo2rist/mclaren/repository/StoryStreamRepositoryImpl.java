package com.github.fo2rist.mclaren.repository;

import android.support.annotation.Nullable;
import android.util.Log;

import com.github.fo2rist.mclaren.web.FeedWebService;
import com.github.fo2rist.mclaren.web.StoryStreamWebServiceImpl;
import com.github.fo2rist.mclaren.web.FeedWebServiceCallback;
import com.github.fo2rist.mclaren.web.models.StoryStream;
import java.io.IOException;
import java.net.URL;
import javax.inject.Inject;

import static com.github.fo2rist.mclaren.web.models.StoryStreamResponseParser.parse;

/**
 * Provider of the "Stories" feed supplied by storystream.it service.
 * StoryStream supports caching headers and standard pagination.
 */
public class StoryStreamRepositoryImpl implements StoryStreamRepository {
    final FeedWebService webService;
    private int lastLoadedPage = 0;

    @Inject
    public StoryStreamRepositoryImpl(StoryStreamWebServiceImpl webService) {
        this.webService = webService;
    }

    @Override
    public void loadLatest() {
        webService.requestLatestFeed(webResponseHandler);
    }

    @Override
    public void prepareForLoading() {
        //no preparation needed for Story Stream service
    }

    @Override
    public void loadPrevious() {
        int pageToLoad = lastLoadedPage + 1;
        webService.requestFeedPage(pageToLoad, webResponseHandler);
    }

    private FeedWebServiceCallback webResponseHandler = new FeedWebServiceCallback() {

        public void onFailure(URL url, int requestedPage, int responseCode, @Nullable IOException connectionError) {
            //TODO
            Log.d("Error", "" + responseCode);
        }

        public void onSuccess(URL url, int requestedPage, int responseCode, @Nullable String data) {
            //TODO
            Log.d("Response", data);
            if (requestedPage >= 0) {
                lastLoadedPage = requestedPage;
            }

            StoryStream storyStreamItems = parse(data);
            Log.d("Response Size", "" + storyStreamItems.items.size());
        }
    };
}
