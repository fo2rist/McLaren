package com.github.fo2rist.mclaren.repository.utils;

import android.support.annotation.NonNull;

import com.github.fo2rist.mclaren.models.ImageUrl;

/**
 * Helps with McLaren API specific image URLs.
 * Some URLs are fixed some are dynamic (e.g. contain placeholder for future propagation).
 */
public class ImageUrlParser {
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
    public static String convertToInternalUrl(@NonNull String serverApiImageUrl) {
        if (isMcLarenTabApiDynamicLink(serverApiImageUrl)) {
            return replaceWidthHeight(serverApiImageUrl,
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

    private static String replaceWidthHeight(String imageUrl,
            String widthPlaceholder, String heightPlaceholder, String widthReplacement, String heightReplacement) {

        return imageUrl
                .replace(widthPlaceholder, widthReplacement)
                .replace(heightPlaceholder, heightReplacement);
    }
}
