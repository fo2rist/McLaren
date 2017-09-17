package com.github.fo2rist.mclaren.ui.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.URLUtil;

public class LinkUtils {

    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    private LinkUtils() {
    }

    public static Intent createBrowserIntent(String url) {
        return new Intent(Intent.ACTION_VIEW, Uri.parse(URLUtil.guessUrl(url.trim())));
    }

    public static boolean openInBrowser(Context context, String url) {
        Intent intent = createBrowserIntent(url);
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            return false;
        }
        context.startActivity(intent);
        return true;
    }
}
