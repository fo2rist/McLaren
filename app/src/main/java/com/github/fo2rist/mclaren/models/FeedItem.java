package com.github.fo2rist.mclaren.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Arrays;
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

    public int id;
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

    public FeedItem(int id, @NonNull Type type, @NonNull String text, @Nullable String content, @NonNull Date dateTime,
            @NonNull SourceType sourceType, @NonNull String sourceName, String embeddedLink, String... imageUris) {
        this.id = id;
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

    //TODO integrate autovalue instead of this 2017.09.22
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeedItem feedItem = (FeedItem) o;

        if (type != feedItem.type) {
            return false;
        }
        if (!text.equals(feedItem.text)) {
            return false;
        }
        if (content != null ? !content.equals(feedItem.content) : feedItem.content != null) {
            return false;
        }
        if (!dateTime.equals(feedItem.dateTime)) {
            return false;
        }
        if (sourceType != feedItem.sourceType) {
            return false;
        }
        if (!sourceName.equals(feedItem.sourceName)) {
            return false;
        }
        if (!embeddedLink.equals(feedItem.embeddedLink)) {
            return false;
        }
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(imageUris, feedItem.imageUris);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + dateTime.hashCode();
        result = 31 * result + sourceType.hashCode();
        result = 31 * result + sourceName.hashCode();
        result = 31 * result + embeddedLink.hashCode();
        result = 31 * result + Arrays.hashCode(imageUris);
        return result;
    }
}
