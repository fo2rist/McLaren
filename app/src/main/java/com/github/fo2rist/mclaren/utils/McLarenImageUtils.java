package com.github.fo2rist.mclaren.utils;

import android.net.Uri;
import android.text.TextUtils;

/**
 * Helps with McLaren API specific image URLs
 */
public class McLarenImageUtils {
    /* Placeholders that Web API returns in image URLs to specify width. */
    private static final String CDN_API_WIDTH_PLACEHOLDER = "WIDTH_PLACEHOLDER";
    private static final String CDN_API_HEIGHT_PLACEHOLDER = "HEIGHT_PLACEHOLDER";
    private static final String TAB_API_WIDTH_PLACEHOLDER = "{width}";
    private static final String TAB_API_HEIGHT_PLACEHOLDER = "{height}";

    private McLarenImageUtils() {
    }

    /**
     * @return null for null or empty source urls
     */
    public static Uri buildImageUri(String imageUri, int width, int height) {
        if (TextUtils.isEmpty(imageUri)) {
            return null;
        }

        String uriWithSubstitutions;
        if (imageUri.contains(TAB_API_WIDTH_PLACEHOLDER) || imageUri.contains(TAB_API_HEIGHT_PLACEHOLDER)) {
            uriWithSubstitutions = replaceWidthHeight(imageUri,
                    TAB_API_WIDTH_PLACEHOLDER,
                    TAB_API_HEIGHT_PLACEHOLDER,
                    width,
                    height);
        } else if (imageUri.contains(CDN_API_WIDTH_PLACEHOLDER) || imageUri.contains(CDN_API_HEIGHT_PLACEHOLDER)) {
            uriWithSubstitutions = replaceWidthHeight(imageUri,
                    CDN_API_WIDTH_PLACEHOLDER,
                    CDN_API_HEIGHT_PLACEHOLDER,
                    width,
                    height);
        } else {
            uriWithSubstitutions = imageUri;
        }
        return Uri.parse(uriWithSubstitutions);
    }

    private static String replaceWidthHeight(String imageUri, String widthPlaceholder, String heightPlaceholder, int width, int height) {
        return imageUri.replace(widthPlaceholder, String.valueOf(width))
                .replace(heightPlaceholder, String.valueOf(height));
    }
}
