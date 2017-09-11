package com.github.fo2rist.mclaren.testdata;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.models.FeedItem.SourceType;
import com.github.fo2rist.mclaren.models.FeedItem.Type;
import java.util.Date;

public class FeedItems {
    public static final String TITLE = "test title";
    private static final String CONTENT = "test content";
    private static final String SOURCE_NAME = "test source";
    private static final String[] IMAGES = new String[]{""};

    public static final FeedItem GALLERY_ITEM = new FeedItem(Type.Gallery,
            TITLE,
            CONTENT,
            new Date(),
            SourceType.Twitter,
            SOURCE_NAME,
            IMAGES);
}
