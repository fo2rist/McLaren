package com.github.fo2rist.mclaren.mvp;

import com.github.fo2rist.mclaren.models.FeedItem;

public interface ImagePreviewContract {
    interface View extends BaseView {
        void showImages(String[] imageUris);
    }

    interface Presenter extends BasePresenter<View> {
        void onStartWith(View view, FeedItem item);
    }
}
