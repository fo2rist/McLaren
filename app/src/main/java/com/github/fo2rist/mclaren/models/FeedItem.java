package com.github.fo2rist.mclaren.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    @NonNull
    public final Date dateTime;
    @NonNull
    public final SourceType sourceType;
    @NonNull
    public final String sourceName;
    @NonNull
    public final String[] imageUris;

    public static FeedItem createMessage(@NonNull String text, @NonNull Date dateTime, @NonNull SourceType sourceType,
            @NonNull String sourceName) {
        return new FeedItem(Type.Message, text, dateTime, sourceType, sourceName);
    }

    public static FeedItem createImage(@NonNull String text, @NonNull Date dateTime, @NonNull SourceType sourceType,
            @NonNull String sourceName, @NonNull String imageUri) {
        return new FeedItem(Type.Image, text, dateTime, sourceType, sourceName, imageUri);
    }

    public static FeedItem createGallery(@NonNull String text, @NonNull Date dateTime, @NonNull SourceType sourceType,
            @NonNull String sourceName, @NonNull List<String> imageUris) {
        return new FeedItem(Type.Gallery, text, dateTime, sourceType, sourceName, imageUris.toArray(new String[imageUris.size()]));
    }

    public FeedItem(@NonNull Type type, @NonNull String text, @NonNull Date dateTime, @NonNull SourceType sourceType,
            @NonNull String sourceName, String...imageUris) {
        this.type = type;
        this.text = text;
        this.dateTime = dateTime;
        this.sourceType = sourceType;
        this.sourceName = sourceName;
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
