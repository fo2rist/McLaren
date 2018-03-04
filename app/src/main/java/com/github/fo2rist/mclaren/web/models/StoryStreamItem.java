package com.github.fo2rist.mclaren.web.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class StoryStreamItem  implements Serializable {
    @SerializedName("publish_date")
    public Date publishDate;

    @SerializedName("content_items")
    public ArrayList<StoryStreamContentItem> contentItems;
}
