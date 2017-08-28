package com.github.fo2rist.mclaren.repository;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.web.FeedWebsevice;
import com.github.fo2rist.mclaren.web.model.McLarenFeed;
import com.github.fo2rist.mclaren.web.model.McLarenFeedConverter;
import com.github.fo2rist.mclaren.web.model.ResponseParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class McLarenFeedRepository implements FeedRepository, Callback {
    @Inject
    FeedWebsevice websevice;

    private List<FeedItem> feed = new ArrayList<>();
    private HashSet<Listener> listeners = new HashSet<>();

    @Inject
    public McLarenFeedRepository() {
        //empty constructor for injection
    }

    @Override
    public void load() {
        websevice.requestFeed(this);
    }

    @Override
    public void subscribe(Listener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void unsubscribe(Listener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (!response.isSuccessful()) {
            //TODO completely get rid of network raw logic here
        }
        this.feed = parse(response.body().string());
        for (Listener listener: listeners) {
            listener.onGetFeed(feed);
        }
    }

    private List<FeedItem> parse(String response) {
        McLarenFeed mcLarenFeed = ResponseParser.parseFeed(response);
        return McLarenFeedConverter.convertFeed(mcLarenFeed);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        //TODO
    }
}
