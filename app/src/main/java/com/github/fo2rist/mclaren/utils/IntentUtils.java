package com.github.fo2rist.mclaren.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.URLUtil;
import androidx.annotation.NonNull;

public class IntentUtils {

    private static final String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=";

    private IntentUtils() {
    }

    @NonNull
    public static Intent createBrowserIntent(String url) {
        Uri uri = Uri.parse(URLUtil.guessUrl(url.trim()));
        return createBrowserIntent(uri);
    }

    @NonNull
    private static Intent createBrowserIntent(@NonNull Uri uri) {
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public static boolean openInBrowser(@NonNull Context context, @NonNull Uri uri) {
        Intent intent = createBrowserIntent(uri);
        return launchSafely(context, intent);
    }

    public static boolean openInBrowser(@NonNull Context context, @NonNull String url) {
        Intent intent = createBrowserIntent(url);
        return launchSafely(context, intent);
    }

    public static boolean launchSafely(@NonNull Context context, @NonNull Intent intent) {
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException exc) {
            return false;
        }
    }
}
