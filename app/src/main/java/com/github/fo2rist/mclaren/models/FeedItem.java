package com.github.fo2rist.mclaren.models;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
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

    Date dateTime_;
    Type type_ = Type.Message; //The default one
    @NonNull
    String text_;
    SourceType sourceType_ = SourceType.Unknown; //The default one
    String sourceName_;
    List<Uri> imageUris_ = new ArrayList<>();

    public FeedItem(Date dateTime, String text, SourceType sourceType, String sourceName_) {
        this.dateTime_ = dateTime;
        this.type_ = Type.Message;
        this.text_ = text;
        this.sourceType_ = sourceType;
        this.sourceName_ = sourceName_;
    }

    public FeedItem(Date dateTime, @NonNull String text, SourceType sourceType, String sourceName, Uri imageUri) {
        this.dateTime_ = dateTime;
        this.type_ = Type.Image;
        this.text_ = text;
        this.sourceType_ = sourceType;
        this.sourceName_ = sourceName;
        this.imageUris_.add(imageUri);
    }

    public FeedItem(Date dateTime, @NonNull String text, SourceType sourceType, String sourceName, List<Uri> imageUris) {
        this.dateTime_ = dateTime;
        this.type_ = Type.Gallery;
        this.text_ = text;
        this.sourceType_ = sourceType;
        this.sourceName_ = sourceName;
        this.imageUris_.addAll(imageUris);
    }

    public Date getDateTime() {
        return dateTime_;
    }

    public Type getType() {
        return type_;
    }

    @NonNull
    public String getText() {
        return text_;
    }

    public SourceType getSourceType() {
        return sourceType_;
    }

    public String getSourceName() {
        return sourceName_;
    }

    @NonNull
    public List<Uri> getImageUris() {
        return imageUris_;
    }

    /**
     * Get single image. Should be null for text posts.
     * @return the first image if there are many or null
     */
    @Nullable
    public Uri getImageUri() {
        if (imageUris_.isEmpty()) {
            return null;
        } else {
            return imageUris_.get(0);
        }
    }
}
