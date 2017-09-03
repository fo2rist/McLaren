package com.github.fo2rist.mclaren.web.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represent one item in feed.
 * Fields we don't use at the time:
 *  - layer int
 *  - sourceId string id
 *  - tweetText string
 *  - appOnly boolean
 * Fields with conditional meaning:
 *  - content: plain string for Message, and Gallery from Twitter, html for Article and Gallery from null
 *  - body: null of html for some Gallery posts
 */
public class McLarenFeedItem {
    enum Type {
        @SerializedName("image")
        IMAGE,
        @SerializedName("gallery")
        GALLERY,
        @SerializedName("video")
        VIDEO,
        @SerializedName("message")
        MESSAGE,
        @SerializedName("article")
        ARTICLE,
    }

    enum Source {
        @SerializedName("Twitter")
        TWITTER,
        @SerializedName("Instagram")
        INSTAGRAM,
    }

    Type type;
    String author;
    int id;
    int likes;
    int likeStep;
    String origin;
    boolean hidden;
    boolean promotional;
    Date publicationDate;
    Source source;
    String title;
    String content;
    String body;
    ArrayList<McLarenMediaItem> media;
}
