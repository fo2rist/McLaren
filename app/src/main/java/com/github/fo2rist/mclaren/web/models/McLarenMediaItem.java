package com.github.fo2rist.mclaren.web.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Represent sub-item in feed of any multimedia type.
 */
public class McLarenMediaItem {
    public enum Source {
        @SerializedName("twitter")
        TWITTER,
        @SerializedName("instagram")
        INSTAGRAM,
        @SerializedName("mclaren")
        MCLAREN,
    }
    public enum Type {
        @SerializedName("image")
        IMAGE,
    }

    public int id;
    /** Media type. May be null for twitter source. */
    @Nullable
    public Type type;
    @Nullable
    public Source source;
    public String url;
    public int width;
    public int height;
}
