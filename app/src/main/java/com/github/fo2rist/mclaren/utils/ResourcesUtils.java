package com.github.fo2rist.mclaren.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.github.fo2rist.mclaren.BuildConfig;
import java.util.Locale;

public class ResourcesUtils {
    public static Uri getCircuitImageUriById(String id) {
        return getDrawableUri(String.format(Locale.US, "circuit_%s", id));
    }

    public static Uri getCircuitDetailedImageUriById(String id) {
        return getDrawableUri(String.format(Locale.US, "circuit_%s", id));
    }

    public static Uri getCircuitDrsImageUriById(String id) {
        return getDrawableUri(String.format(Locale.US, "circuit_%s_drs", id));
    }

    public static Uri getCircuitSectorsImageUriById(String id) {
        return getDrawableUri(String.format(Locale.US, "circuit_%s_sectors", id));
    }

    public static Uri getCircuitTurnsImageUriById(String id) {
        return getDrawableUri(String.format(Locale.US, "circuit_%s_turns", id));
    }

    @NonNull
    private static Uri getDrawableUri(String drawableName) {
        return Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/drawable/" + drawableName);
    }
}
