package com.github.fo2rist.mclaren.web;

import android.annotation.SuppressLint;
import android.content.Context;

import com.github.fo2rist.mclaren.BuildConfig;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class McLarenImageDownloader {
    @SuppressLint("StaticFieldLeak")
    private static Picasso loader;

    private McLarenImageDownloader() {
    }

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
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
            })
            .build();


    public static synchronized Picasso getLoader(Context context) {
        if (loader == null) {
            loader = buildLoader(context);
        }
        return loader;
    }

    private static Picasso buildLoader(Context context) {
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .build();
    }
}
