package com.github.fo2rist.mclaren.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.URLUtil;

public class IntentUtils {

    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";
    private static final String PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=";
    private static final String MCLAREN_APP_PACKAGE = "mclaren.mobile";

    private IntentUtils() {
    }

    public static Intent createBrowserIntent(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(URLUtil.guessUrl(url.trim())));
    }

    public static boolean openInBrowser(Context context, String url) {
        Intent intent = createBrowserIntent(url);
        return launchSafely(context, intent);
    }

    public static boolean launchSafely(Context context, Intent intent) {
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }
        context.startActivity(intent);
        return true;
    }

    public static Intent createAppIntent(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            intent = createBrowserIntent(PLAY_STORE_URL + packageName);
        }
        return intent;
    }

    public static Intent createMcLarenAppIntent(Context context) {
        return createAppIntent(context, MCLAREN_APP_PACKAGE);
    }
}
