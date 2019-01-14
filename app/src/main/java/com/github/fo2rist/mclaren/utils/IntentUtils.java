package com.github.fo2rist.mclaren.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.URLUtil;

public class IntentUtils {

    private static final String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=";
    private static final String MCLAREN_APP_PACKAGE = "mclaren.mobile";

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
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }
        context.startActivity(intent);
        return true;
    }

    @NonNull
    public static Intent createAppIntent(@NonNull Context context, @NonNull String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            intent = createBrowserIntent(PLAY_STORE_URL + packageName);
        }
        return intent;
    }

    @NonNull
    public static Intent createMcLarenAppIntent(@NonNull Context context) {
        return createAppIntent(context, MCLAREN_APP_PACKAGE);
    }
}
