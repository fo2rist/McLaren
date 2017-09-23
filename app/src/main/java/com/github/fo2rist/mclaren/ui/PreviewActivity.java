package com.github.fo2rist.mclaren.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.web.McLarenImageDownloader;

public class PreviewActivity extends AppCompatActivity {

    private static final String KEY_URL = "url";
    private static final String KEY_FEED_ITEM = "feed_item";

    private ActionBar actionBar;
    private ImageView headerImage;

    public static Intent createUrlIntent(Context context, String url) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra(KEY_URL, url);
        return intent;
    }

    public static Intent createFeedItemIntent(Context context, FeedItem item) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra(KEY_FEED_ITEM, item);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        setupToolbar();

        headerImage = findViewById(R.id.image_header);

        Fragment previewFragment;
        if (getIntent().hasExtra(KEY_URL)) {
            previewFragment = WebPreviewFragment.newInstanceForUrl(getIntent().getStringExtra(KEY_URL));
            setHeaderImage(null);
        } else if (getIntent().hasExtra(KEY_FEED_ITEM)) {
            FeedItem feedItem = (FeedItem) getIntent().getSerializableExtra(KEY_FEED_ITEM);

            switch (feedItem.type){
                case Article:
                    setHeaderImage(feedItem.getImageUri());
                    setTitle(feedItem.text);
                    previewFragment = WebPreviewFragment.newInstanceForMcLarenHtml(feedItem.content);
                    break;
                case Image:
                case Gallery:
                    setHeaderImage(null);
                    lockToolBar();
                    previewFragment = ImagePreviewFragment.newInstanceForFeedItem(feedItem);
                    break;
                case Video:
                case Message:
                default:
                    setHeaderImage(null);
                    lockToolBar();
                    previewFragment = new WebPreviewFragment();
                    break;
            }
        } else {
            finish();
            return;
        }

        displayFragment(previewFragment);
    }

    private ActionBar setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.app_name);
        return actionBar;
    }

    private void setTitle(String text) {
        actionBar.setTitle(text);
    }

    private void setHeaderImage(String imageUri) {
        if (TextUtils.isEmpty(imageUri)) {
            headerImage.setVisibility(View.GONE);
        } else {
            McLarenImageDownloader.loadImage(headerImage, imageUri);
        }
    }

    private void lockToolBar() {
        CollapsingToolbarLayout collapsingToolBar = findViewById(R.id.collapsing_toolbar_layout);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolBar.getLayoutParams();
        params.setScrollFlags(0);  // clear all scroll flags
    }

    private void displayFragment(Fragment previewFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, previewFragment)
                .commit();
    }
}
