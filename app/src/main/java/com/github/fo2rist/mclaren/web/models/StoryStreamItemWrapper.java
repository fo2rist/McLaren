package com.github.fo2rist.mclaren.web.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represent on feed item with puclication date as returned by StoryStream API.
 * Potentially contains an array of {@link StoryStreamItem} but seems like only on is usually present.
 * @see StoryStream
 */
public class StoryStreamItemWrapper implements Serializable {
    @Nullable
    @SerializedName("publish_date")
    public Date publishDate;

    @Nullable
    @SerializedName("content_items")
    public ArrayList<StoryStreamItem> contentItems;
}
