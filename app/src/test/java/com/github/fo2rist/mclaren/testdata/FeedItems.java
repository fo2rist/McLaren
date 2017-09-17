package com.github.fo2rist.mclaren.testdata;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.models.FeedItem.SourceType;
import com.github.fo2rist.mclaren.models.FeedItem.Type;
import java.util.Date;

public class FeedItems {
    public static final String TITLE = "test title";
    private static final String CONTENT = "test content";
    private static final String SOURCE_NAME_TEST = "test source";
    private static final String SOURCE_NAME_MCLAREN = "mclaren.com";
    private static final String[] IMAGES = new String[]{""};

    public static final FeedItem TWITTER_GALLERY_ITEM = new FeedItem(Type.Gallery,
            TITLE,
            CONTENT,
            new Date(),
            SourceType.Twitter,
            SOURCE_NAME_TEST,
            IMAGES);

    public static final FeedItem INSTAGRAM_GALLERY_ITEM = new FeedItem(Type.Gallery,
            TITLE,
            CONTENT,
            new Date(),
            SourceType.Instagram,
            SOURCE_NAME_TEST,
            IMAGES);

    public static final FeedItem MCLAREN_ARTICLE_ITEM = new FeedItem(Type.Article,
            TITLE,
            CONTENT,
            new Date(),
            SourceType.Unknown,
            SOURCE_NAME_MCLAREN);
}
