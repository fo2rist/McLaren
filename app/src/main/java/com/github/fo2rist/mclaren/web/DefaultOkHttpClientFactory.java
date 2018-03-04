package com.github.fo2rist.mclaren.web;

import android.content.Context;

import com.github.fo2rist.mclaren.utils.CacheUtils;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class DefaultOkHttpClientFactory {

    private static final int WEB_CACHE_SIZE = 5 * 1024 * 1024; // 5 Mb

    private static OkHttpClient clientInstance = null;

    /**
     * Get or create single OkHttp Client instance.
     */
    static public synchronized OkHttpClient getInstance(Context context) {
        if (clientInstance == null) {
            Cache cache = CacheUtils.createCache(context, "web", WEB_CACHE_SIZE);
            clientInstance = new OkHttpClient.Builder().cache(cache).build();
        }
        return clientInstance;
    }

    private DefaultOkHttpClientFactory() {
        //no instantiation
    }
}
