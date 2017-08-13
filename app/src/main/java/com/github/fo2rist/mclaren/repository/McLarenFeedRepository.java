package com.github.fo2rist.mclaren.repository;

import android.net.Uri;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.web.FeedWebsevice;
import com.github.fo2rist.mclaren.web.McLarenFeedWebservice;
import com.github.fo2rist.mclaren.web.model.McLarenFeed;
import com.github.fo2rist.mclaren.web.model.ResponseParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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

    public McLarenFeedRepository() {
        websevice = new McLarenFeedWebservice(); //TODO inject it
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
        return getDebugItems();
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    /*DEBUG*/
    private ArrayList<FeedItem> getDebugItems() {
        ArrayList<FeedItem> items = new ArrayList<>();

        FeedItem feedItem = new FeedItem(Calendar.getInstance().getTime(), "One bla", FeedItem.SourceType.Twitter, "@fo2rist");
        items.add(feedItem);
        feedItem = new FeedItem(Calendar.getInstance().getTime(), "Two bla", FeedItem.SourceType.Instagram, "@fo2rist");
        items.add(feedItem);
        feedItem = new FeedItem(Calendar.getInstance().getTime(), "Bla bla image", FeedItem.SourceType.Unknown, "@fo2rist", Uri.parse("android.resource://com.github.fo2rist.mclaren/drawable/image_splash"));
        items.add(feedItem);
        feedItem = new FeedItem(Calendar.getInstance().getTime(), "Bla bla image same", FeedItem.SourceType.Twitter, "@fo2rist", Uri.parse("https://static.pexels.com/photos/33045/lion-wild-africa-african.jpg"));
        items.add(feedItem);

        ArrayList<Uri> imageUrls = new ArrayList<>();
        imageUrls.add(Uri.parse("android.resource://com.github.fo2rist.mclaren/drawable/image_splash"));
        imageUrls.add(Uri.parse("android.resource://com.github.fo2rist.mclaren/drawable/image_background_pattern_small"));
        imageUrls.add(Uri.parse("https://static.pexels.com/photos/33045/lion-wild-africa-african.jpg"));
        feedItem = new FeedItem(Calendar.getInstance().getTime(), "Bla bla gallery", FeedItem.SourceType.Instagram, "@fo2rist", imageUrls);
        items.add(feedItem);

        feedItem = new FeedItem(Calendar.getInstance().getTime(), "Bla bla gallery same", FeedItem.SourceType.Twitter, "@fo2rist", imageUrls);
        items.add(feedItem);

        return items;
    }
    /*END DEBUG*/
}
