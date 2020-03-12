package com.github.fo2rist.mclaren.ui.previewscreen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.utils.McLarenHtmlUtils;

import static com.github.fo2rist.mclaren.utils.IntentUtils.openInBrowser;
import static com.github.fo2rist.mclaren.utils.LinkUtils.MCLAREN_COM;


public class WebPreviewFragment extends Fragment {
    private static final String ARG_URL = "url";
    private static final String ARG_MCLAREN_HTML = "mclaren_html";

    private WebView webView;

    public static WebPreviewFragment newInstanceForUrl(@NonNull String url) {
        return createInstanceWithArgs(ARG_URL, url);
    }

    public static WebPreviewFragment newInstanceForMcLarenHtml(@NonNull String html) {
        return createInstanceWithArgs(ARG_MCLAREN_HTML, html);
    }

    @NonNull
    private static WebPreviewFragment createInstanceWithArgs(@NonNull String key, @NonNull String value) {
        WebPreviewFragment fragment = new WebPreviewFragment();
        Bundle args = new Bundle();
        args.putString(key, value);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
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
    private void setupWebView(@NonNull WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);

        WebViewClient webViewClient = new WebViewClient() {
            private boolean allowExit = false;
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                allowExit = true;
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //keep all mclaren related navigation inside the view
                //and don't let redirect out of the view before page loads
                //so only user interaction can lead outside but not redirect event
                if (url.contains(MCLAREN_COM) || !allowExit) {
                    return false;
                } else {
                    allowExit = false; //don't allow immediate exit on return back
                    openInBrowser(getContext(), url);
                    return true;
                }
            }
        };
        webView.setWebViewClient(webViewClient);
    }

    private void loadUrl(@NonNull WebView webView, @Nullable String url) {
        if (url == null) {
            return;
        }
        webView.loadUrl(url);
    }

    private void loadHtml(@NonNull WebView webView, @Nullable String html) {
        webView.loadDataWithBaseURL(null, McLarenHtmlUtils.pretify(html),"text/html", "UTF-8", null);
    }
}
