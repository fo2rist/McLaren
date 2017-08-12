package com.github.fo2rist.mclaren.ui.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.github.fo2rist.mclaren.BuildConfig;
import java.util.Locale;

public class ResourcesUtils {
    public static Uri getCircuitImageUriByNumber(int position) {
        return Uri.parse(getDrawableUri(String.format(Locale.US, "circuit_%02d", position + 1)));
    }

    public static Uri getCircuitDetailedImageUriByNumber(int position) {
        return Uri.parse(getDrawableUri(String.format(Locale.US, "circuit_detailed_%02d", position + 1)));
    }

    @NonNull
    private static String getDrawableUri(String drawableName) {
        return "android.resource://" + BuildConfig.APPLICATION_ID + "/drawable/" + drawableName;
    }
}
