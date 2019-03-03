package com.github.fo2rist.mclaren.mvp;

import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.models.ImageUrl;
import java.util.List;

/**
 * Contract for single image or gallery preview.
 */
public interface ImagePreviewContract {

    interface View extends BaseView {
        void showImages(List<ImageUrl> imageUris);
    }

    interface Presenter extends BasePresenter<View> {
        void onStartWith(View view, FeedItem item);

        void onScrolledTo(int position);
    }
}
