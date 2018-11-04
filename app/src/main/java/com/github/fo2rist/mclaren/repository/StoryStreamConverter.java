package com.github.fo2rist.mclaren.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.models.FeedItem.SourceType;
import com.github.fo2rist.mclaren.models.FeedItem.Type;
import com.github.fo2rist.mclaren.models.ImageUrl;
import com.github.fo2rist.mclaren.models.Size;
import com.github.fo2rist.mclaren.web.models.StoryStream;
import com.github.fo2rist.mclaren.web.models.StoryStreamContentItem;
import com.github.fo2rist.mclaren.web.models.StoryStreamItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.github.fo2rist.mclaren.models.FeedItem.TEXT_LENGTH_LIMIT;
import static com.github.fo2rist.mclaren.web.models.StoryStreamContentItem.FeedType;
import static com.github.fo2rist.mclaren.web.models.StoryStreamContentItem.ImageData;
import static com.github.fo2rist.mclaren.web.models.StoryStreamContentItem.VideoData;

class StoryStreamConverter {

    private static final String HTTP = "http";

    public static List<FeedItem> convertFeed(StoryStream storyStreamFeed) {
        ArrayList<FeedItem> result = new ArrayList<>(storyStreamFeed.items.size());
        for (StoryStreamItem storyStreamItem: storyStreamFeed.items) {
            result.add(convertFeedItem(storyStreamItem));
        }
        return result;
    }

    private static FeedItem convertFeedItem(StoryStreamItem storyStreamItem) {
        return new FeedItem(
            fetchId(storyStreamItem),
            fetchType(storyStreamItem),
            fetchText(storyStreamItem),
            fetchContent(storyStreamItem),
            fetchDate(storyStreamItem),
            fetchSourceType(storyStreamItem),
            fetchSourceName(storyStreamItem),
            fetchMediaLink(storyStreamItem),
            fetchImageUrls(storyStreamItem));
    }

    private static long fetchId(StoryStreamItem storyStreamItem) {
        //StoryStream don't offer sequential IDs so use timestamp instead as a hack
        return storyStreamItem.publishDate.getTime();
    }

    @NonNull
    private static Type fetchType(StoryStreamItem storyStreamItem) {
        StoryStreamContentItem storyStreamContentItem = fetchContentItem(storyStreamItem);
        Type result;
        switch (storyStreamContentItem.feedType) {
            case Custom:
                return Type.Article;
            default:
                switch (storyStreamContentItem.contentType) {
                    case Text:
                        return Type.Message;
                    case Image:
                        if (storyStreamContentItem.images.size() > 1) {
                            return Type.Gallery;
                        } else {
                            return Type.Image;
                        }
                    case Video:
                        return Type.Video;
                }
                break;
        }
        return Type.Message;
    }

    @NonNull
    private static String fetchText(StoryStreamItem storyStreamItem) {
        StoryStreamContentItem data = fetchContentItem(storyStreamItem);
        if (data.feedType == FeedType.Custom) {
            String text = data.body;
            text = Html.fromHtml(text).toString();

            text = text.replaceAll("\\n+", " ");

            if (!TextUtils.isEmpty(data.title)) {
                text = data.title.toUpperCase() + "\n" + text;
            }

            if (text.length() > TEXT_LENGTH_LIMIT) {
                text = text.substring(0, TEXT_LENGTH_LIMIT) + "...";
            }

            return text;
        } else {
            //TODO We are loosing links that way. HTML should be preserved and then handled on the UI side. 2018.03.09
            return Html.fromHtml(data.body).toString();
        }
    }

    private static StoryStreamContentItem fetchContentItem(StoryStreamItem storyStreamItem) {
        return storyStreamItem.contentItems.get(0);
    }

    @Nullable
    private static String fetchContent(StoryStreamItem storyStreamItem) {
        return fetchContentItem(storyStreamItem).body;
    }

    @NonNull
    private static Date fetchDate(StoryStreamItem storyStreamItem) {
        return storyStreamItem.publishDate;
    }

    @NonNull
    private static SourceType fetchSourceType(StoryStreamItem storyStreamItem) {
        switch(fetchContentItem(storyStreamItem).feedType) {
            case Twitter:
                return SourceType.Twitter;
            case Instagram:
                return SourceType.Instagram;
            default:
                return SourceType.Unknown;
        }
    }

    @NonNull
    private static String fetchSourceName(StoryStreamItem storyStreamItem) {
        StoryStreamContentItem storyStreamContentItem = fetchContentItem(storyStreamItem);
        return storyStreamContentItem.source; //for pretty name use .author
    }

    @NonNull
    private static String fetchMediaLink(StoryStreamItem storyStreamItem) {
        List<VideoData> videos = fetchContentItem(storyStreamItem).videos;
        return (!videos.isEmpty() && videos.get(0) != null)
                ? videos.get(0).url
                : "";
    }

    @NonNull
    private static ImageUrl[] fetchImageUrls(StoryStreamItem storyStreamItem) {
        List<ImageData> images = fetchContentItem(storyStreamItem).images;
        ImageUrl[] result = new ImageUrl[images.size()];
        for (int i = 0; i < images.size(); i++) {
            result[i] = fetchUrlFromImageData(images.get(i));
        }
        return result;
    }

    private static ImageUrl fetchUrlFromImageData(ImageData imageData) {
        String originalSizeUrl = fixUrl(imageData.originalSizeUrl);
        Size originalSize = toImageSize(imageData.sizes.originalSize);

        if (!originalSizeUrl.isEmpty()) {
            //Here the original link is present additional links are optional

            String twoUpSizeUrl = fixUrl(imageData.twoUpSizeUrl);
            String threeUpSizeUrl = fixUrl(imageData.threeUpSizeUrl);

            if (originalSizeUrl.equals(twoUpSizeUrl) && originalSizeUrl.equals(threeUpSizeUrl)) {
                //Broken links are usually equal, so we should ignore small size links as incorrect
                return ImageUrl.create(originalSizeUrl, originalSize);
            } else if (twoUpSizeUrl.isEmpty() && threeUpSizeUrl.isEmpty()){
                //We only have one link of three, create a single URL
                return ImageUrl.create(originalSizeUrl, originalSize);
            } else {
                //Normal case - all three links are different and not empty
                Size twoUpSize = toImageSize(imageData.sizes.twoUpSize);
                Size threeUpSize = toImageSize(imageData.sizes.threeUpSize);
                return ImageUrl.create(originalSizeUrl, originalSize,
                        twoUpSizeUrl, twoUpSize,
                        threeUpSizeUrl, threeUpSize);
            }
        } else if (imageData.name.startsWith(HTTP)) {
            //Here original link is absent

            //when links are broken the name usually contains the link
            return ImageUrl.create(imageData.name, originalSize);
        } else {
            //nothing we can do
            return ImageUrl.empty();
        }
    }

    /**
     * Strip URL from non-http prefix.
     * Sometimes API return weird prefix before the actual URL, which should be stripped.
     * @return empty string if input is null or empty, fixed URL otherwise.
     */
    @NonNull
    private static String fixUrl(@Nullable String url) {
        if (url == null) {
            return "";
        }

        if (!url.startsWith(HTTP) && url.contains(HTTP) ) {
            return url.replaceFirst("^.*\\/http?", "http");
        } else {
            return url;
        }
    }

    @NonNull
    private static Size toImageSize(@Nullable StoryStreamContentItem.ImageSize size) {
        if (size == null) {
            return Size.UNKNOWN;
        }
        return Size.valueOf(size.width, size.height);
    }
}
