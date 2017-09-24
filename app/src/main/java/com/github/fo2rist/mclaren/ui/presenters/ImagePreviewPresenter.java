package com.github.fo2rist.mclaren.ui.presenters;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.ImagePreviewContract;


public class ImagePreviewPresenter implements ImagePreviewContract.Presenter {
    private ImagePreviewContract.View view;
    private FeedItem item;

    @Override
    public void onStart(ImagePreviewContract.View view) {
        this.view = view;
    }

    @Override
    public void onStartWith(ImagePreviewContract.View view, FeedItem item) {
        this.onStart(view);
        this.item = item;

        showItemContent();
    }

    @Override
    public void onStop() {
    }

    private void showItemContent() {
        view.showImages(item.imageUris);
    }
}
