package com.github.fo2rist.mclaren.web.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represent full feed as it returned by StoryStream API.
 * @see StoryStream
 * @see StoryStreamItemWrapper
 */
public class StoryStream implements Serializable {
    @Nullable
    @SerializedName("date")
    public Date date;

    @NonNull
    @SerializedName("blocks")
    public ArrayList<StoryStreamItemWrapper> items = new ArrayList<>();

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("meta")
    public Object meta;
}
