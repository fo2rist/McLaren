package com.github.fo2rist.mclaren.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.models.FeedItem.SourceType;
import com.github.fo2rist.mclaren.models.FeedItem.Type;
import com.github.fo2rist.mclaren.web.models.StoryStream;
import com.github.fo2rist.mclaren.web.models.StoryStreamContentItem;
import com.github.fo2rist.mclaren.web.models.StoryStreamItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.github.fo2rist.mclaren.models.FeedItem.TEXT_LENGTH_LIMIT;
import static com.github.fo2rist.mclaren.web.models.StoryStreamContentItem.FeedType;
import static com.github.fo2rist.mclaren.web.models.StoryStreamContentItem.ImageData;
import static com.github.fo2rist.mclaren.web.models.StoryStreamContentItem.VideoData;

class StoryStreamConverter {
    public static List<FeedItem> convertFeed(StoryStream storyStreamFeed) {
        ArrayList<FeedItem> result = new ArrayList<>(storyStreamFeed.items.size());
        for (StoryStreamItem storyStreamItem: storyStreamFeed.items) {
            result.add(convertFeedItem(storyStreamItem));
        }
        return result;
    }

    private static FeedItem convertFeedItem(StoryStreamItem storyStreamItem) {
        return new FeedItem(
            fetchId(storyStreamItem),
            fetchType(storyStreamItem),
            fetchText(storyStreamItem),
            fetchContent(storyStreamItem),
            fetchDate(storyStreamItem),
            fetchSourceType(storyStreamItem),
            fetchSourceName(storyStreamItem),
            fetchHiddenMediaLink(storyStreamItem),
            fetchMediaUrls(storyStreamItem));
    }

    private static long fetchId(StoryStreamItem storyStreamItem) {
        //StoryStream don't offer sequential IDs so use timestamp instead as a hack
        return storyStreamItem.publishDate.getTime();
    }

    @NonNull
    private static Type fetchType(StoryStreamItem storyStreamItem) {
        StoryStreamContentItem storyStreamContentItem = fetchContentItem(storyStreamItem);
        Type result;
        switch (storyStreamContentItem.feedType) {
            case Custom:
                return Type.Article;
            default:
                switch (storyStreamContentItem.contentType) {
                    case Text:
                        return Type.Message;
                    case Image:
                        if (storyStreamContentItem.images.size() > 1) {
                            return Type.Gallery;
                        } else {
                            return Type.Image;
                        }
                    case Video:
                        return Type.Video;
                }
                break;
        }
        return Type.Message;
    }

    @NonNull
    private static String fetchText(StoryStreamItem storyStreamItem) {
        StoryStreamContentItem data = fetchContentItem(storyStreamItem);
        if (data.feedType == FeedType.Custom) {
            String text = data.body;
            text = Html.fromHtml(text).toString();

            text = text.replaceAll("\\n+", " ");

            if (!TextUtils.isEmpty(data.title)) {
                text = data.title.toUpperCase() + "\n" + text;
            }

            if (text.length() > TEXT_LENGTH_LIMIT) {
                text = text.substring(0, TEXT_LENGTH_LIMIT);
            }

            return text;
        } else {
            //TODO We are loosing links that way. HTML should be preserved and then handled on the UI side. 2018.03.09
            return Html.fromHtml(data.body).toString();
        }
    }

    private static StoryStreamContentItem fetchContentItem(StoryStreamItem storyStreamItem) {
        return storyStreamItem.contentItems.get(0);
    }

    @Nullable
    private static String fetchContent(StoryStreamItem storyStreamItem) {
        return fetchContentItem(storyStreamItem).body;
    }

    @NonNull
    private static Date fetchDate(StoryStreamItem storyStreamItem) {
        return storyStreamItem.publishDate;
    }

    @NonNull
    private static SourceType fetchSourceType(StoryStreamItem storyStreamItem) {
        switch(fetchContentItem(storyStreamItem).feedType) {
            case Twitter:
                return SourceType.Twitter;
            case Instagram:
                return SourceType.Instagram;
            default:
                return SourceType.Unknown;
        }
    }

    @NonNull
    private static String fetchSourceName(StoryStreamItem storyStreamItem) {
        StoryStreamContentItem storyStreamContentItem = fetchContentItem(storyStreamItem);
        return storyStreamContentItem.source; //for pretty name use .author
    }

    @NonNull
    private static String fetchHiddenMediaLink(StoryStreamItem storyStreamItem) {
        List<VideoData> videos = fetchContentItem(storyStreamItem).videos;
        return (!videos.isEmpty() && videos.get(0) != null)
                ? videos.get(0).url
                : "";
    }

    @NonNull
    private static String[] fetchMediaUrls(StoryStreamItem storyStreamItem) {
        List<ImageData> images = fetchContentItem(storyStreamItem).images;
        String[] result = new String[images.size()];
        for (int i = 0; i < images.size(); i++) {
            result[i] = images.get(i).originalSizeUrl;
        }
        return result;
    }
}
