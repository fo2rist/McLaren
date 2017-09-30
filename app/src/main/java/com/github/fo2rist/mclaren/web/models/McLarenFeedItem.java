package com.github.fo2rist.mclaren.web.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represent one item in feed.
 * Fields we don't use at the time:
 *  - layer int
 *  - sourceId string id
 *  - tweetText string
 *  - appOnly boolean
 *  - tweetText string may be set for all kind of posts not just tweets
 * Fields with conditional meaning:
 *  - content: plain string for Message, and Gallery from Twitter, html for Article and Gallery from null
 *  - body: null of html for some Gallery posts
 */
public class McLarenFeedItem implements Serializable{
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

    public Type type;
    public String author;
    public int id;
    public int likes;
    public int likeStep;
    public String origin;
    public boolean hidden;
    public boolean promotional;
    public Date publicationDate;
    public Source source;
    public String title;
    public String content;
    public String body;
    public String tweetText;
    ArrayList<McLarenMediaItem> media;
}
