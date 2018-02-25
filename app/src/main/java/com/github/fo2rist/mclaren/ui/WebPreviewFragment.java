package com.github.fo2rist.mclaren.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.utils.McLarenHtmlUtils;

import static com.github.fo2rist.mclaren.utils.LinkUtils.MCLAREN_COM;


public class WebPreviewFragment extends Fragment {
    private static final String ARG_URL = "url";
    private static final String ARG_MCLAREN_HTML = "mclaren_html";

    private WebView webView;

    public static WebPreviewFragment newInstanceForUrl(String url) {
        return createInstanceWithArgs(ARG_URL, url);
    }

    public static WebPreviewFragment newInstanceForMcLarenHtml(String html) {
        return createInstanceWithArgs(ARG_MCLAREN_HTML, html);
    }

    @NonNull
    private static WebPreviewFragment createInstanceWithArgs(String key, String value) {
        WebPreviewFragment fragment = new WebPreviewFragment();
        Bundle args = new Bundle();
        args.putString(key, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_web_preview, container, false);
        webView = rootView.findViewById(R.id.web_view);
        setupWebView(webView);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle arguments = getArguments();
        if (arguments == null) {
            return;
        }
        if (arguments.containsKey(ARG_URL)) {
            loadUrl(webView, arguments.getString(ARG_URL));
        } else if (arguments.containsKey(ARG_MCLAREN_HTML)) {
            loadHtml(webView, arguments.getString(ARG_MCLAREN_HTML));
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView(WebView webView) {
        WebViewClient webViewClient = new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //keep all mclaren related navigation inside the view
                if (url.contains(MCLAREN_COM)) {
                    return false;
                } else {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                    return true;
                }
            }
        };
        webView.setWebViewClient(webViewClient);
        webView.getSettings().setJavaScriptEnabled(true);
    }

    private void loadUrl(WebView webView, String url) {
        webView.loadUrl(url);
    }

    private void loadHtml(WebView webView, String html) {
        webView.loadDataWithBaseURL(null, McLarenHtmlUtils.pretify(html),"text/html", "UTF-8", null);
    }
}
