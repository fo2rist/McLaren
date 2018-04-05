package com.github.fo2rist.mclaren.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.github.fo2rist.mclaren.analytics.Events;
import com.github.fo2rist.mclaren.analytics.EventsLogger;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.ImagePreviewContract;
import javax.inject.Inject;


public class ImagePreviewPresenter implements ImagePreviewContract.Presenter {
    private ImagePreviewContract.View view;

    private int lastKnownGalleryPosition = 0;

    @Nullable
    private FeedItem item;
    @NonNull
    private EventsLogger eventsLogger;

    @Inject
    ImagePreviewPresenter(EventsLogger eventsLogger) {
        this.eventsLogger = eventsLogger;
    }

    @Override
    public void onStart(@NonNull ImagePreviewContract.View view) {
        this.view = view;
    }

    @Override
    public void onStartWith(@NonNull ImagePreviewContract.View view, FeedItem item) {
        this.onStart(view);
        this.item = item;

        showItemContent();
    }

    @Override
    public void onScrolledTo(int position) {
        if (position > lastKnownGalleryPosition) {
            eventsLogger.logViewEvent(Events.GALLERY_NEXT);
        } else if (position < lastKnownGalleryPosition) {
            eventsLogger.logViewEvent(Events.GALLERY_PREV);
        }
        lastKnownGalleryPosition = position;
    }

    @Override
    public void onStop() {
    }

    private void showItemContent() {
        if (item == null) {
            return;
        }

        view.showImages(item.imageUrls);
    }
}
