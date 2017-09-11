package com.github.fo2rist.mclaren.ui.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.github.fo2rist.mclaren.web.McLarenImageDownloader;

public class ImageUtils {
    /* Placeholders that Web API returns in image URLs to specify width. */
    public static final String CDN_API_WIDTH_PLACEHOLDER = "WIDTH_PLACEHOLDER";
    public static final String CDN_API_HEIGHT_PLACEHOLDER = "HEIGHT_PLACEHOLDER";
    private static final String TAB_API_WIDTH_PLACEHOLDER = "{width}";
    private static final String TAB_API_HEIGHT_PLACEHOLDER = "{height}";

    //TODO tab-api doesn't not support images larger than original size. Let's handle it on client. 2017-02-09
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    private ImageUtils() {
    }

    public static void loadImage(ImageView imageView, String imageUri) {
        loadImage(imageView, imageUri, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public static void loadImage(ImageView imageView, String imageUri, int width, int height) {
        Uri loadUri = buildImageUri(imageUri, width, height);
        McLarenImageDownloader.getLoader(imageView.getContext())
                .load(loadUri)
                .into(imageView);
    }

    /**
     * @return null for null or empty source urls
     */
    private static Uri buildImageUri(String imageUri, int width, int height) {
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
