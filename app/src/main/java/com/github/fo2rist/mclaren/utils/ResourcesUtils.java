package com.github.fo2rist.mclaren.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.github.fo2rist.mclaren.BuildConfig;
import java.util.Locale;

public class ResourcesUtils {
    public static Uri getCircuitImageUriById(String id) {
        return Uri.parse(getDrawableUri(String.format(Locale.US, "circuit_%s_thumb", id)));
    }

    public static Uri getCircuitDetailedImageUriById(String id) {
        return Uri.parse(getDrawableUri(String.format(Locale.US, "circuit_%s", id)));
    }

    @NonNull
    private static String getDrawableUri(String drawableName) {
        return "android.resource://" + BuildConfig.APPLICATION_ID + "/drawable/" + drawableName;
    }
}
