package com.github.fo2rist.mclaren.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

/**
 * Represent single item in the feed.
 */
public class FeedItem implements Serializable {
    public enum Type {
        Image,
        Gallery,
        Video,
        Message,
        Article,
    }

    public enum SourceType {
        Twitter,
        Instagram,
        Unknown,
    }

    @NonNull
    public final Type type;
    @NonNull
    public final String text;
    @Nullable
    public final String content;
    @NonNull
    public final Date dateTime;
    @NonNull
    public final SourceType sourceType;
    @NonNull
    public final String sourceName;
    /** Non displayable link that may be found in source data. */
    @NonNull
    public final String embeddedLink;
    @NonNull
    public final String[] imageUris;

    public FeedItem(@NonNull Type type, @NonNull String text, @Nullable String content, @NonNull Date dateTime,
            @NonNull SourceType sourceType, @NonNull String sourceName, String embeddedLink, String... imageUris) {
        this.type = type;
        this.text = text;
        this.content = content;
        this.dateTime = dateTime;
        this.sourceType = sourceType;
        this.sourceName = sourceName;
        this.embeddedLink = embeddedLink;
        this.imageUris = imageUris;
    }

    /**
     * Get single image. Should be null for text posts.
     * @return the first image if there are many or null
     */
    @Nullable
    public String getImageUri() {
        if (imageUris.length == 0) {
            return null;
        } else {
            return imageUris[0];
        }
    }
}
