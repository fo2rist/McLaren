package com.github.fo2rist.mclaren.web.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represent full feed as it returned by Story Stream Service.
 */
public class StoryStream implements Serializable {
    @SerializedName("date")
    public Date date;

    @SerializedName("blocks")
    public ArrayList<StoryStreamItem> items;

    @SerializedName("total_pages")
    public int totalPages;

    @SerializedName("meta")
    public Object meta;
}
