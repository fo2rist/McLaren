package com.github.fo2rist.mclaren.web.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Represent one atomic item in feed as it's received from StoryStream API.
 * @see StoryStreamWrappingItem
 */
public class StoryStreamItem implements Serializable {

    public enum FeedType {
        Custom,
        Twitter,
        Image,
        Instagram,

        Facebook,
        Link,
        Quote,
        Url,
        Youtube,
        @SerializedName("app-direct-upload") // May have serialized name "app-direct-upload" or "App Direct Upload"
        AppDirectUpload,
        @SerializedName("asset") // May have serialized name "asset" or "Asset"
        Asset,
        @SerializedName("instagram-hashtag")
        InstagramHashtag,
    }

    public enum ContentType {
        @SerializedName("text")
        Text,
        @SerializedName("image")
        Image,
        @SerializedName("video")
        Video,
    }

    public static class ImageSize {
        public int width;
        public int height;
    }

    public static class Sizes {
        @Nullable
        @SerializedName("original")
        public ImageSize originalSize;

        @Nullable
        @SerializedName("two_up")
        public ImageSize twoUpSize;

        @Nullable
        @SerializedName("three_up")
        public ImageSize threeUpSize;
    }

    public static class ImageData implements Serializable {
        @Nullable
        @SerializedName("name")
        public String name;

        @Nullable
        @SerializedName("original")
        public String originalSizeUrl;

        @Nullable
        @SerializedName("two_up")
        public String twoUpSizeUrl;

        @Nullable
        @SerializedName("three_up")
        public String threeUpSizeUrl;

        @SerializedName("is_external")
        public boolean isExternal;

        @Nullable
        public Sizes sizes;
    }

    public static class VideoData implements Serializable {
        @Nullable
        public String url;
    }

    @Nullable
    public Map<String, String> feed;

    @Nullable
    public String title;

    @Nullable
    public String source;

    @Nullable
    public String body;

    @Nullable
    public String permalink;

    @Nullable
    public String author;

    @Nullable
    @SerializedName("story_name")
    public String storyName;

    @Nullable
    @SerializedName("content_type")
    public ContentType contentType;

    @Nullable
    @SerializedName("publish_date")
    public Date publishDate;

    @Nullable
    @SerializedName("created_date")
    public Date createdDate;

    @Nullable
    @SerializedName("feed_type")
    public FeedType feedType;

    @Nullable
    public List<ImageData> images;

    @Nullable
    public List<VideoData> videos;
}
