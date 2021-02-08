package com.github.fo2rist.mclaren.web.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represent one item in feed as it's received from McLaren API.
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
public class McLarenFeedItem implements Serializable {
    public enum Type {
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

    public enum Source {
        @SerializedName("Twitter")
        TWITTER,
        @SerializedName("Instagram")
        INSTAGRAM,
    }

    @Nullable
    public Type type;
    @Nullable
    public String author;
    public int id;
    public int likes;
    public int likeStep;
    @Nullable
    public String origin;
    public boolean hidden;
    public boolean promotional;
    @Nullable
    public Date publicationDate;
    @Nullable
    public Source source;
    @Nullable
    public String title;
    @Nullable
    public String content;
    @Nullable
    public String body;
    @Nullable
    public String tweetText;
    public ArrayList<McLarenMediaItem> media;
}
