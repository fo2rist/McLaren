package com.github.fo2rist.mclaren.web.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StoryStreamContentItem implements Serializable {
    public enum FeedType {
        Custom,
        Facebook,
        Image,
        Instagram,
        Link,
        Quote,
        Twitter,
        Url,
        Youtube,
        //May have serialized name "app-direct-upload" or "App Direct Upload"
        @SerializedName("app-direct-upload")
        AppDirectUpload,
        //May have serialized name "asset" or "Asset"
        @SerializedName("asset")
        Asset,
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

    public static class ImageData implements Serializable {
        @SerializedName("original")
        public String originalSizeUrl;

        @SerializedName("two_up")
        public String twoUpSizeUrl;

        @SerializedName("three_up")
        public String threeUpSizeUrl;

        @SerializedName("is_external")
        public boolean isExternal;

        public Map<String, ImageSize> sizes;
    }

    public static class VideoData implements Serializable {
        public String url;
    }

    public Map<String, String> feed;
    public String title;
    public String source;
    public String body;
    public String permalink;
    public String author;

    @SerializedName("story_name")
    public String storyName;

    @SerializedName("content_type")
    public ContentType contentType;

    @SerializedName("publish_date")
    public Date publishDate;

    @SerializedName("created_date")
    public Date createdDate;

    @SerializedName("feed_type")
    public FeedType feedType;

    public List<ImageData> images;
    public List<VideoData> videos;
}
