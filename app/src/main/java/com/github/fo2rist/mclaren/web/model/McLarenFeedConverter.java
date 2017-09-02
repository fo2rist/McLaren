package com.github.fo2rist.mclaren.web.model;

import com.github.fo2rist.mclaren.models.FeedItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Converts web-models to plain app models.
 */
public class McLarenFeedConverter {
    private McLarenFeedConverter() {
    }

    public static List<FeedItem> convertFeed(McLarenFeed mcLarenFeed) {
        ArrayList<FeedItem> result = new ArrayList<>(mcLarenFeed.size());
        for (McLarenFeedItem mcLarenFeedItem : mcLarenFeed) {
            if (!mcLarenFeedItem.hidden) {
                result.add(convertFeedItem(mcLarenFeedItem));
            }
        }
        return result;
    }

    private static FeedItem convertFeedItem(McLarenFeedItem mcLarenFeedItem) {
        return new FeedItem(fetchType(mcLarenFeedItem),
                fetchText(mcLarenFeedItem),
                fetchDate(mcLarenFeedItem),
                fetchSourceType(mcLarenFeedItem),
                fetchSourceName(mcLarenFeedItem),
                fetchMediaUris(mcLarenFeedItem));
    }

    private static FeedItem.Type fetchType(McLarenFeedItem mcLarenFeedItem) {
        if (mcLarenFeedItem.type == McLarenFeedItem.Type.IMAGE) {
            return FeedItem.Type.Image;
        } else if (mcLarenFeedItem.type == McLarenFeedItem.Type.GALLERY) {
            return FeedItem.Type.Gallery;
        } else if (mcLarenFeedItem.type == McLarenFeedItem.Type.VIDEO) {
            return FeedItem.Type.Video;
        } else if (mcLarenFeedItem.type == McLarenFeedItem.Type.MESSAGE) {
            return FeedItem.Type.Message;
        } else if (mcLarenFeedItem.type == McLarenFeedItem.Type.ARTICLE) {
            return FeedItem.Type.Article;
        } else {
            return FeedItem.Type.Message;
        }
    }

    private static String fetchText(McLarenFeedItem mcLarenFeedItem) {
        switch (mcLarenFeedItem.type) {
            case ARTICLE:
            case GALLERY:
                return mcLarenFeedItem.title;
            default:
               return mcLarenFeedItem.content;
        }
    }

    private static Date fetchDate(McLarenFeedItem mcLarenFeedItem) {
        return mcLarenFeedItem.publicationDate != null
                ? mcLarenFeedItem.publicationDate
                : new Date();
    }

    private static FeedItem.SourceType fetchSourceType(McLarenFeedItem mcLarenFeedItem) {
        if (mcLarenFeedItem.source == McLarenFeedItem.Source.TWITTER) {
            return FeedItem.SourceType.Twitter;
        } else if (mcLarenFeedItem.source == McLarenFeedItem.Source.INSTAGRAM) {
            return FeedItem.SourceType.Instagram;
        } else {
            return FeedItem.SourceType.Unknown;
        }
    }

    private static String fetchSourceName(McLarenFeedItem mcLarenFeedItem) {
        if (mcLarenFeedItem.author != null) {
            return mcLarenFeedItem.author;
        } else if (mcLarenFeedItem.origin != null) {
            return mcLarenFeedItem.origin;
        } else {
            return "";
        }
    }

    private static String[] fetchMediaUris(McLarenFeedItem mcLarenFeedItem) {
        if (mcLarenFeedItem.media == null) {
            return new String[0];
        } else {
            String[] result = new String[mcLarenFeedItem.media.size()];
            for (int i = 0; i < mcLarenFeedItem.media.size(); i++) {
                result[i] = mcLarenFeedItem.media.get(i).url;
            }
            return result;
        }
    }
}
