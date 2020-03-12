package com.github.fo2rist.mclaren.web.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represent on feed item with publication date as returned by StoryStream API.
 * Potentially contains an array of {@link StoryStreamItem} but seems like only one is present.
 * @see StoryStream
 */
public class StoryStreamWrappingItem implements Serializable {
    @Nullable
    @SerializedName("publish_date")
    public Date publishDate;

    @Nullable
    @SerializedName("content_items")
    public ArrayList<StoryStreamItem> contentItems;
}
