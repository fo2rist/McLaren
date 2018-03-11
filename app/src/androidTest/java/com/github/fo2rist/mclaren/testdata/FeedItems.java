package com.github.fo2rist.mclaren.testdata;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.models.FeedItem.SourceType;
import com.github.fo2rist.mclaren.models.FeedItem.Type;
import java.util.Date;

public class FeedItems {
    private static final long ID = 1;
    private static final String TITLE = "test title";
    private static final String CONTENT = "test content";
    private static final String SOURCE_NAME = "test source";
    private static final String GOOGLE_LOGO_IMAGE = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png";
    private static final String MEDIA_LINK = "http://mclaren.com/formula1";
    private static final String[] IMAGES = new String[]{GOOGLE_LOGO_IMAGE, GOOGLE_LOGO_IMAGE};

    public static final FeedItem GALLERY_ITEM = new FeedItem(
            ID,
            Type.Gallery,
            TITLE,
            CONTENT,
            new Date(),
            SourceType.Twitter,
            SOURCE_NAME,
            MEDIA_LINK,
            IMAGES);

    public static final FeedItem HTML_ARTICLE_ITEM = new FeedItem(
            ID,
            Type.Article,
            "Some text",
            TestData.ARTICLE_HTML,
            new Date(),
            FeedItem.SourceType.Unknown,
            SOURCE_NAME,
            MEDIA_LINK,
            IMAGES);
}
