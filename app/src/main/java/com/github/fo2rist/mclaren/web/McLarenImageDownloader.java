package com.github.fo2rist.mclaren.web;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.github.fo2rist.mclaren.BuildConfig;
import com.github.fo2rist.mclaren.utils.CacheUtils;
import com.github.fo2rist.mclaren.utils.McLarenImageUtils;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class McLarenImageDownloader {
    //TODO tab-api doesn't not support images larger than original size. Let's handle it on client. 2017-02-09
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    private static final int CACHE_SIZE = 25 * 1024 * 1024; //25 Mb

    private static McLarenImageDownloader instance;

    private Picasso picasso;

    private static synchronized McLarenImageDownloader getInstance(Context context) {
        if (instance == null) {
            instance = new McLarenImageDownloader(context);
        }
        return instance;
    }

    public static void loadImage(ImageView imageView, String imageUri) {
        loadImage(imageView, imageUri, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    private static void loadImage(ImageView imageView, String imageUri, int width, int height) {
        getInstance(imageView.getContext()).load(imageUri, imageView, width, height);
    }

    public static void cacheImages(Context context, String[] imageUris) {
        McLarenImageDownloader imageDownloader = getInstance(context);
        for (String uri : imageUris) {
            imageDownloader.cache(uri, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        }
    }

    private McLarenImageDownloader(Context context) {
        picasso = new Picasso.Builder(context)
                .downloader(createCachingDownloader(context))
                .indicatorsEnabled(BuildConfig.DEBUG)
                .loggingEnabled(BuildConfig.DEBUG)
                .build();
    }

    @NonNull
    private OkHttp3Downloader createCachingDownloader(Context context) {
        //WARN caching OkHTTP clients should not use the same directory or at lest should never call the same endpoint
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(createMcLarenAuthInterceptor())
                .cache(CacheUtils.createCache(context,"images", CACHE_SIZE))
                .build();
        return new OkHttp3Downloader(client);
    }

    @NonNull
    private Interceptor createMcLarenAuthInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                //set auth header to requests for tab-api
                if (chain.request().url().encodedPath().contains("tab-api/")) {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", BuildConfig.MCLAREN_TAB_API_AUTH)
                            .build();
                    return chain.proceed(newRequest);
                } else { // or just proceed request as is
                    return chain.proceed(chain.request());
                }
            }
        };
    }

    private void load(String imageUri, ImageView imageView, int width, int height) {
        Uri loadUri = McLarenImageUtils.buildImageUri(imageUri, width, height);

        picasso.load(loadUri)
                .into(imageView);
    }

    private void cache(String imageUri, int width, int height) {
        Uri loadUri = McLarenImageUtils.buildImageUri(imageUri, width, height);
        picasso.load(loadUri).
                fetch();
    }
}
