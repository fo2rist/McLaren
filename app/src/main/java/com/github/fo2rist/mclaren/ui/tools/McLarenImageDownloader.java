package com.github.fo2rist.mclaren.ui.tools;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.github.fo2rist.mclaren.BuildConfig;
import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.ImageUrl;
import com.github.fo2rist.mclaren.web.OkHttpClientFactory;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class McLarenImageDownloader {
    /**
     * Desired image size depending on the purpose.
     */
    public enum ImageSizeLimit {
        /** Maximal allowed size for image. */
        MAXIMAL,
        /** Image large enough for fullscreen mode. */
        FULLSCREEN,
        /** Image large enough for tiles in feed etc.*/
        TILE,
        /** Image suitable for icons and small preview. */
        THUMB
    }

    private static McLarenImageDownloader instance;

    private Picasso picasso;

    private int maxImageSize;
    private int fullscreenImageSize;
    private int tileImageSize;
    private int thumbImageSize;

    private static synchronized McLarenImageDownloader getInstance(Context context) {
        if (instance == null) {
            instance = new McLarenImageDownloader(context);
        }
        return instance;
    }

    /**
     * Load image with default size.
     */
    public static void loadImage(ImageView imageView, ImageUrl imageUrl) {
        loadImage(imageView, imageUrl, ImageSizeLimit.TILE);
    }

    /**
     * Load image with specific size.
     */
    public static void loadImage(ImageView imageView, ImageUrl imageUrl, ImageSizeLimit sizeType) {
        getInstance(imageView.getContext()).load(imageView, imageUrl, sizeType);
    }

    /**
     * Cache image with specific size.
     */
    public static void cacheImages(Context context, List<ImageUrl> imageUrls, ImageSizeLimit sizeType) {
        McLarenImageDownloader downloader = getInstance(context);
        for (ImageUrl uri : imageUrls) {
            downloader.cache(uri, sizeType);
        }
    }

    private McLarenImageDownloader(Context context) {
        maxImageSize = context.getResources().getDimensionPixelSize(R.dimen.max_image_size);
        fullscreenImageSize = context.getResources().getDimensionPixelSize(R.dimen.fullscreen_image_size);
        tileImageSize = context.getResources().getDimensionPixelSize(R.dimen.tile_image_size);
        thumbImageSize = context.getResources().getDimensionPixelSize(R.dimen.thumb_image_size);

        picasso = new Picasso.Builder(context)
                .downloader(createCachingDownloader(context))
                .indicatorsEnabled(BuildConfig.DEBUG)
                .loggingEnabled(BuildConfig.DEBUG)
                .build();
    }

    @NonNull
    private OkHttp3Downloader createCachingDownloader(Context context) {
        OkHttpClient client = OkHttpClientFactory.createPicassoClient(context, createMcLarenAuthInterceptor());
        return new OkHttp3Downloader(client);
    }

    @NonNull
    private Interceptor createMcLarenAuthInterceptor() {
        return chain -> {
            //set auth header to requests for tab-api
            if (chain.request().url().encodedPath().contains("tab-api/")) {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", BuildConfig.MCLAREN_TAB_API_AUTH)
                        .build();
                return chain.proceed(newRequest);
            } else { // or just proceed request as is
                return chain.proceed(chain.request());
            }
        };
    }

    private void load(ImageView imageView, ImageUrl imageUrl, ImageSizeLimit sizeType) {
        int sizeInPixels = getSizeInPixelsFor(sizeType);

        Uri loadUri = buildImageUri(imageUrl, sizeInPixels);
        picasso.load(loadUri)
                .resize(sizeInPixels, 0) // cap by size in but maintain aspect ratio
                .into(imageView);
    }

    private void cache(ImageUrl imageUrl, ImageSizeLimit sizeType) {
        int sizeInPixels = getSizeInPixelsFor(sizeType);

        Uri loadUri = buildImageUri(imageUrl, sizeInPixels);
        picasso.load(loadUri)
                .fetch();
    }

    private int getSizeInPixelsFor(ImageSizeLimit sizeType) {
        switch (sizeType) {
            case MAXIMAL:
                return maxImageSize;
            case FULLSCREEN:
                return fullscreenImageSize;
            case THUMB:
                return thumbImageSize;
            case TILE:
            default:
                return tileImageSize;
        }
    }

    @Nullable
    private Uri buildImageUri(ImageUrl imageUrl, int sizeCap) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return null;
        }

        return Uri.parse(imageUrl.getUrl(sizeCap, sizeCap));
    }
}
