package com.github.fo2rist.mclaren.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.models.ImageUrl;
import com.github.fo2rist.mclaren.web.McLarenImageDownloader;
import com.github.fo2rist.mclaren.web.McLarenImageDownloader.ImageSizeType;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import javax.inject.Inject;

public class PreviewActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private static final String CONTENT_FRAGMENT_TAG = "content_fragment";
    private static final String KEY_URL = "url";
    private static final String KEY_FEED_ITEM = "feed_item";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

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
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        setupToolbar();

        headerImage = findViewById(R.id.image_header);

        Fragment previewFragment = null;
        if (getIntent().hasExtra(KEY_URL)) {
            setHeaderImage(null);
            previewFragment = WebPreviewFragment.newInstanceForUrl(getIntent().getStringExtra(KEY_URL));
        } else if (getIntent().hasExtra(KEY_FEED_ITEM)) {
            FeedItem feedItem = (FeedItem) getIntent().getSerializableExtra(KEY_FEED_ITEM);

            switch (feedItem.type){
                case Article:
                    setTitle(feedItem.text);
                    if (isPortraitMode()) {
                        setHeaderImage(feedItem.getImageUri());
                    } else {
                        setHeaderImage(null);
                    }
                    previewFragment = WebPreviewFragment.newInstanceForMcLarenHtml(feedItem.content);
                    break;
                case Image:
                case Gallery:
                    enterFullScreen();
                    setHeaderImage(null);
                    if (isPortraitMode()) {
                        setToolBarVisible(true);
                        lockToolBar();
                    } else {
                        setToolBarVisible(false);
                    }
                    previewFragment = ImagePreviewFragment.newInstanceForFeedItem(feedItem);
                    break;
                case Video:
                case Message:
                default:
                    //we don't use Preview for Messages and Video so far
                    previewFragment = null;
            }
        }

        if (previewFragment == null) {
            finish();
        } else {
            //we have already created a fragment to show but there is a change we can retain the existing one
            //in case of rotation or cold activity restart
            //if fragment retained will use it because it may contain some visual state
            Fragment retainedFragment = getSupportFragmentManager().findFragmentByTag(CONTENT_FRAGMENT_TAG);
            displayFragment((retainedFragment != null) ? retainedFragment :previewFragment);
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.app_name);
    }

    private void setTitle(String text) {
        actionBar.setTitle(text);
    }

    private void setHeaderImage(ImageUrl imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            headerImage.setVisibility(View.GONE);
        } else {
            McLarenImageDownloader.loadImage(headerImage, imageUrl, ImageSizeType.TILE);
        }
    }

    private boolean isPortraitMode() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    private void lockToolBar() {
        CollapsingToolbarLayout collapsingToolBar = findViewById(R.id.collapsing_toolbar_layout);
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) collapsingToolBar.getLayoutParams();
        params.setScrollFlags(0);  // clear all scroll flags
    }

    private void setToolBarVisible(boolean visible) {
        CollapsingToolbarLayout collapsingToolBar = findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    private void enterFullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
    }

    private void displayFragment(Fragment previewFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, previewFragment, CONTENT_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
