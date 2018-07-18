package com.github.fo2rist.mclaren.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Represent single item in the feed.
 */
public class FeedItem implements Serializable, Comparable<FeedItem> {
    /**
     * Suggested limit for text length.
     * Not enforced by constructor itself.
     */
    public static final int TEXT_LENGTH_LIMIT = 280;

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

    public long id;
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
    public final String embeddedMediaLink;
    @NonNull
    public final List<ImageUrl> imageUrls;

    public FeedItem(long id, @NonNull Type type, @NonNull String text, @Nullable String content, @NonNull Date dateTime,
            @NonNull SourceType sourceType, @NonNull String sourceName, @NonNull String embeddedMediaLink,
            ImageUrl... imageUrls) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.content = content;
        this.dateTime = dateTime;
        this.sourceType = sourceType;
        this.sourceName = sourceName;
        this.embeddedMediaLink = embeddedMediaLink;
        this.imageUrls = Arrays.asList(imageUrls);
    }

    /**
     * Get single image. Should be null for text posts.
     * @return the first image if there are many or null
     */
    @Nullable
    public ImageUrl getImageUri() {
        if (imageUrls.isEmpty()) {
            return null;
        } else {
            return imageUrls.get(0);
        }
    }

    //TODO integrate autovalue instead of this 2017.09.22
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        FeedItem another = (FeedItem) other;

        if (id != another.id) {
            return false;
        }
        if (type != another.type) {
            return false;
        }
        if (!text.equals(another.text)) {
            return false;
        }
        if (content != null ? !content.equals(another.content) : another.content != null) {
            return false;
        }
        if (!dateTime.equals(another.dateTime)) {
            return false;
        }
        if (sourceType != another.sourceType) {
            return false;
        }
        if (!sourceName.equals(another.sourceName)) {
            return false;
        }
        if (!embeddedMediaLink.equals(another.embeddedMediaLink)) {
            return false;
        }

        return imageUrls.equals(another.imageUrls);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + type.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + dateTime.hashCode();
        result = 31 * result + sourceType.hashCode();
        result = 31 * result + sourceName.hashCode();
        result = 31 * result + embeddedMediaLink.hashCode();
        result = 31 * result + imageUrls.hashCode();
        return result;
    }

    @Override
    public int compareTo(@NonNull FeedItem other) {
        return Long.compare(this.id, other.id);
    }
}
