package com.github.fo2rist.mclaren.utils;

import android.content.Context;
import androidx.annotation.NonNull;

import java.io.File;
import okhttp3.Cache;

public final class CacheUtils {
    private CacheUtils() {
    }

    /**
     * Create cache object.
     * @param context context to be used to gather default cache dir
     * @param tag tag to distinguish different caches and prevent conflicts. Note: should never repeat same tag!
     * @param size cache size in bytes
     */
    public static Cache createCache(Context context, String tag, long size) {
        return new Cache(CacheUtils.getCacheDir(context, tag), size);
    }

    @NonNull
    private static File getCacheDir(Context context, String tag) {
        return getCacheDir(context.getCacheDir().getPath(), tag);
    }

    @NonNull
    private static File getCacheDir(String basePath, String tag) {
        return new File(basePath, tag);
    }
}
