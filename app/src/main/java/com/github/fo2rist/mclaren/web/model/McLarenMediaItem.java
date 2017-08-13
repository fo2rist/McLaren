package com.github.fo2rist.mclaren.web.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Represent sub-item in feed of any multimedia type.
 */
public class McLarenMediaItem {
    enum Source {
        @SerializedName("twitter")
        TWITTER,
        @SerializedName("instagram")
        INSTAGRAM,
        @SerializedName("mclaren")
        MCLAREN,
    }
    enum Type {
        @SerializedName("image")
        IMAGE,
    }

    int id;
    /** Media type. May be null for twitter source. */
    @Nullable
    Type type;
    @Nullable
    Source source;
    String url;
    int width;
    int height;
}
