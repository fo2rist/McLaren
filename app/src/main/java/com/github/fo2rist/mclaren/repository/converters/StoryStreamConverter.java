package com.github.fo2rist.mclaren.repository.converters;

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
import com.github.fo2rist.mclaren.web.models.StoryStreamItem;
import com.github.fo2rist.mclaren.web.models.StoryStreamItemWrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.github.fo2rist.mclaren.models.FeedItem.TEXT_LENGTH_LIMIT;
import static com.github.fo2rist.mclaren.web.models.StoryStreamItem.FeedType;
import static com.github.fo2rist.mclaren.web.models.StoryStreamItem.ImageData;
import static com.github.fo2rist.mclaren.web.models.StoryStreamItem.VideoData;

/**
 * Converts StoryStream API feed web-model to app models.
 */
public final class StoryStreamConverter implements FeedConverter<StoryStream> {

    public static final StoryStreamConverter INSTANCE = new StoryStreamConverter();

    private static final String HTTP = "http";

    private StoryStreamConverter() {
    }

    @NonNull
    @Override
    public List<FeedItem> convertFeed(@NonNull StoryStream storyStreamFeed) {
        ArrayList<FeedItem> result = new ArrayList<>(storyStreamFeed.items.size());
        for (StoryStreamItemWrapper storyStreamItem: storyStreamFeed.items) {
            // TODO: Parser may fail and produce useless feed item -> Filter out bad items. 2019-07-31
            result.add(convertFeedItem(storyStreamItem));
        }
        return result;
    }
    
    private static FeedItem convertFeedItem(StoryStreamItemWrapper storyStreamItem) {
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

    private static long fetchId(@NonNull StoryStreamItemWrapper storyStreamItem) {
        //StoryStream don't offer sequential IDs so use timestamp instead as a hack
        Date publishDate = storyStreamItem.publishDate;
        return publishDate != null
                ? publishDate.getTime()
                : 0; // TODO default values makes no sense need. Check if this kind of item can be displayed. 2019-07-31
    }

    @NonNull
    private static Type fetchType(@NonNull StoryStreamItemWrapper storyStreamItem) {
        StoryStreamItem storyStreamContentItem = fetchContentItem(storyStreamItem);

        if (storyStreamContentItem == null) {
            //TODO Default value doesn't reflect the actual intent in case the content item is null. 2019-07-31
            return Type.Message;
        }

        if (storyStreamContentItem.feedType == FeedType.Custom) {
            return Type.Article;
        } else {
            if (storyStreamContentItem.contentType == null) {
                return Type.Message;
            }
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
                default:
                    return Type.Message;
            }
        }
    }

    @NonNull
    private static String fetchText(@NonNull StoryStreamItemWrapper storyStreamItem) {
        StoryStreamItem storyStreamContentItem = fetchContentItem(storyStreamItem);
        if (storyStreamContentItem == null) {
            return "";
        }
        if (storyStreamContentItem.body == null) {
            return "";
        }

        if (storyStreamContentItem.feedType == FeedType.Custom) {
            String text = storyStreamContentItem.body;
            text = Html.fromHtml(text).toString();

            text = text.replaceAll("\\n+", " ");

            if (!TextUtils.isEmpty(storyStreamContentItem.title)) {
                text = storyStreamContentItem.title.toUpperCase(Locale.US) + "\n" + text;
            }

            if (text.length() > TEXT_LENGTH_LIMIT) {
                text = text.substring(0, TEXT_LENGTH_LIMIT) + "...";
            }

            return text;
        } else {
            //TODO We are loosing links that way. HTML should be preserved and then handled on the UI side. 2018.03.09
            return Html.fromHtml(storyStreamContentItem.body).toString();
        }
    }

    @Nullable
    private static StoryStreamItem fetchContentItem(@NonNull StoryStreamItemWrapper storyStreamItem) {
        ArrayList<StoryStreamItem> contentItems = storyStreamItem.contentItems;
        return contentItems != null
                ? contentItems.get(0)
                : null;
    }

    @Nullable
    private static String fetchContent(@NonNull StoryStreamItemWrapper storyStreamItem) {
        StoryStreamItem storyStreamContentItem = fetchContentItem(storyStreamItem);
        return storyStreamContentItem != null
                ? storyStreamContentItem.body
                : null;
    }

    @NonNull
    private static Date fetchDate(@NonNull StoryStreamItemWrapper storyStreamItem) {
        return storyStreamItem.publishDate != null
                ? storyStreamItem.publishDate
                : new Date(); //TODO default value makes no sense. Check if it can be used anyhow. 2019-07-31
    }

    @NonNull
    private static SourceType fetchSourceType(@NonNull StoryStreamItemWrapper storyStreamItem) {
        FeedType feedType = fetchContentItem(storyStreamItem).feedType;
        if (feedType == null) {
            return SourceType.Unknown;
        }
        switch(feedType) {
            case Twitter:
                return SourceType.Twitter;
            case Instagram:
                return SourceType.Instagram;
            default:
                return SourceType.Unknown;
        }
    }

    @NonNull
    private static String fetchSourceName(@NonNull StoryStreamItemWrapper storyStreamItem) {
        StoryStreamItem storyStreamContentItem = fetchContentItem(storyStreamItem);
        //for pretty name use .author not .source
        return (storyStreamContentItem != null && storyStreamContentItem.source != null)
                ? storyStreamContentItem.source
                : "" ;
    }

    @NonNull
    private static String fetchMediaLink(@NonNull StoryStreamItemWrapper storyStreamItem) {
        StoryStreamItem storyStreamContentItem = fetchContentItem(storyStreamItem);
        if (storyStreamContentItem == null) {
            return "";
        }
        List<VideoData> videos = storyStreamContentItem.videos;
        return (videos != null && !videos.isEmpty() && videos.get(0) != null)
                ? videos.get(0).url
                : "";
    }

    @NonNull
    private static List<ImageUrl> fetchImageUrls(@NonNull StoryStreamItemWrapper storyStreamItem) {
        StoryStreamItem storyStreamContentItem = fetchContentItem(storyStreamItem);
        if (storyStreamContentItem == null) {
            return new ArrayList<>();
        }

        List<ImageData> images = storyStreamContentItem.images;
        if (images == null) {
            return new ArrayList<>();
        }

        List<ImageUrl> result = new ArrayList<>(images.size());
        for (int i = 0; i < images.size(); i++) {
            result.add(fetchUrlFromImageData(images.get(i)));
        }
        return result;
    }

    private static ImageUrl fetchUrlFromImageData(@NonNull ImageData imageData) {
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
    private static Size toImageSize(@Nullable StoryStreamItem.ImageSize size) {
        if (size == null) {
            return Size.UNKNOWN;
        }
        return Size.valueOf(size.width, size.height);
    }
}
