package com.github.fo2rist.mclaren.repository.converters.utils;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.github.fo2rist.mclaren.models.ImageUrl;

import static com.github.fo2rist.mclaren.utils.LinkUtils.HTTP;
import static com.github.fo2rist.mclaren.utils.LinkUtils.MCLAREN_COM;

/**
 * Helps with McLaren API specific image URLs.
 * Some URLs are fixed some are dynamic (e.g. contain placeholder for future propagation).
 */
public class ImageUrlParser {
    @VisibleForTesting
    static final String TAB_API_HOST = MCLAREN_COM;
    @VisibleForTesting
    static final String TAB_API_PATH = "formula1/tab-api/1.0/";
    @VisibleForTesting
    static final String CDN_API_HOST = "cdn.mcl-app-api.com";
    @VisibleForTesting
    static final String CDN_API_PATH = "api/v1/image";

    /* Placeholders that Web API returns in image URLs to specify width. */
    private static final String MCLAREN_CDN_API_WIDTH_PLACEHOLDER = "WIDTH_PLACEHOLDER";
    private static final String MCLAREN_CDN_API_HEIGHT_PLACEHOLDER = "HEIGHT_PLACEHOLDER";
    private static final String MCLAREN_TAB_API_WIDTH_PLACEHOLDER = "{width}";
    private static final String MCLAREN_TAB_API_HEIGHT_PLACEHOLDER = "{height}";

    private ImageUrlParser() {
    }

    /**
     * Convert link optional with/height placeholder to internal dynamic link format for
     * {@link ImageUrl}.
     *
     * @param serverApiImageUrl any URL that API return
     * @return same URL if it's static or URL with internal unified placeholder if it's dynamic.
     */
    public static String convertToInternalUrl(@Nullable String serverApiImageUrl) {
        if (serverApiImageUrl == null) {
            return "";
        }

        if (isMcLarenTabApiDynamicLink(serverApiImageUrl)) {
            String normalizedTabApiUrl = normalizeTabApiUrl(serverApiImageUrl);
            return replaceWidthHeight(normalizedTabApiUrl,
                    MCLAREN_TAB_API_WIDTH_PLACEHOLDER, MCLAREN_TAB_API_HEIGHT_PLACEHOLDER,
                    ImageUrl.DYNAMIC_WIDTH_PLACEHOLDER, ImageUrl.DYNAMIC_HEIGHT_PLACEHOLDER);
        } else if (isMcLarenCdnDynamicLink(serverApiImageUrl)) {
            return replaceWidthHeight(serverApiImageUrl,
                    MCLAREN_CDN_API_WIDTH_PLACEHOLDER, MCLAREN_CDN_API_HEIGHT_PLACEHOLDER,
                    ImageUrl.DYNAMIC_WIDTH_PLACEHOLDER, ImageUrl.DYNAMIC_HEIGHT_PLACEHOLDER);
        } else {
            return serverApiImageUrl;
        }
    }

    private static boolean isMcLarenTabApiDynamicLink(String imageUrl) {
        return imageUrl.contains(MCLAREN_TAB_API_WIDTH_PLACEHOLDER)
                || imageUrl.contains(MCLAREN_TAB_API_HEIGHT_PLACEHOLDER);
    }

    private static boolean isMcLarenCdnDynamicLink(String imageUrl) {
        return imageUrl.contains(MCLAREN_CDN_API_WIDTH_PLACEHOLDER)
                   || imageUrl.contains(MCLAREN_CDN_API_HEIGHT_PLACEHOLDER);
    }

    // sometimes Tab Api give URL without host -> convert them to normal format if required
    private static String normalizeTabApiUrl(String serverApiImageUrl) {
        if (serverApiImageUrl.startsWith(TAB_API_PATH)) {
            return HTTP + MCLAREN_COM + "/" + serverApiImageUrl;
        } else {
            return serverApiImageUrl;
        }
    }

    private static String replaceWidthHeight(String imageUrl,
            String widthPlaceholder, String heightPlaceholder, String widthReplacement, String heightReplacement) {

        return imageUrl
                .replace(widthPlaceholder, widthReplacement)
                .replace(heightPlaceholder, heightReplacement);
    }
}
