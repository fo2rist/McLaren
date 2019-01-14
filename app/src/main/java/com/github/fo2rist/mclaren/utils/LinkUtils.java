package com.github.fo2rist.mclaren.utils;

import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.models.FeedItem;

public class LinkUtils {
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    public static final String MCLAREN_COM = "www.mclaren.com";
    private static final String INSTAGRAM_COM = "www.instagram.com";
    private static final String TWITTER_COM = "twitter.com";

    private static final String INSTAGRAM_BASE_PATH = HTTPS + INSTAGRAM_COM + "/";
    private static final String INSTAGRAM_HASHTAG_BASE_PATH = HTTPS + INSTAGRAM_COM + "/explore/tags/";
    private static final String MCLAREN_BASE_PATH = HTTP + MCLAREN_COM;
    private static final String MCLAREN_F1_BASE_PATH = MCLAREN_BASE_PATH + "/formula1";
    private static final String MCLAREN_F1_CAR_PATH = MCLAREN_F1_BASE_PATH+ "/car/The-F1-Effect/";
    private static final String TWITTER_BASE_PATH = HTTPS + TWITTER_COM + "/";
    private static final String TWITTER_HASHTAG_BASE_PATH = HTTPS + TWITTER_COM + "/hashtag/";

    /** Return embedded media link if possible, otherwise return fallback link to media source. */
    @Nullable
    public static String getMediaLink(FeedItem feedItem) {
        if (feedItem.getEmbeddedMediaLink().isEmpty()) {
            return getFeedMentionLink(feedItem, feedItem.getSourceName());
        } else {
            return feedItem.getEmbeddedMediaLink();
        }
    }

    @Nullable
    public static String getFeedMentionLink(FeedItem feedItem, String mentionId) {
        switch (feedItem.getSourceType()) {
            case Twitter:
                return getTwitterPageLink(mentionId);
            case Instagram:
                return getInstagramPageLink(mentionId);
            case Unknown:
                return getMcLarenFormula1Link();
            default:
                return null;
        }
    }

    public static String getTwitterPageLink(String twitterId) {
        return TWITTER_BASE_PATH + purify(twitterId);
    }

    private static String getInstagramPageLink(String instagramId) {
        return INSTAGRAM_BASE_PATH + purify(instagramId);
    }

    @Nullable
    public static String getFeedHashtagLink(FeedItem feedItem, String hashtag) {
        switch (feedItem.getSourceType()) {
            case Twitter:
                return getTwitterHashtagLink(hashtag);
            case Instagram:
                return getInstagramHashtagLink(hashtag);
            case Unknown:
            default:
                return null;
        }
    }

    private static String getTwitterHashtagLink(String hashtag) {
        return TWITTER_HASHTAG_BASE_PATH + purify(hashtag);
    }

    private static String getInstagramHashtagLink(String hashtag) {
        return INSTAGRAM_HASHTAG_BASE_PATH + purify(hashtag);
    }

    public static String getMcLarenFormula1Link() {
        return MCLAREN_F1_BASE_PATH;
    }

    public static String getMcLarenCarLink() {
        return MCLAREN_F1_CAR_PATH;
    }

    /**
     * Clean mention or hash tag from unwanted prefixes.
     */
    private static String purify(String tag) {
        tag = tag.trim();
        tag = tag.replaceAll("^.?(@|#)", "");
        return tag;
    }
}
