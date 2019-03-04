package com.github.fo2rist.mclaren.ui.previewscreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.ImageUrl;
import com.github.fo2rist.mclaren.web.McLarenImageDownloader;
import com.github.fo2rist.mclaren.web.McLarenImageDownloader.ImageSizeLimit;
import java.util.List;

/**
 * Page adapter that displays images in ImageViews by URLs.
 */
public class ImageGalleryAdapter extends PagerAdapter {

    /**
     * Defines the type of images provided by adapter.
     */
    public enum ImageViewType {
        /** Plain image that can display a drawable. */
        STATIC,
        /** Interactive image view that supports zoom, pan etc. */
        INTERACTIVE,
    }

    private final List<ImageUrl> imageUris;
    private final ImageViewType viewType;
    private final ImageSizeLimit sizeLimit;

    public ImageGalleryAdapter(
            Context context, List<ImageUrl> imageUris, ImageSizeLimit sizeLimit, ImageViewType viewType
    ) {
        this.imageUris = imageUris;
        this.sizeLimit = sizeLimit;
        this.viewType = viewType;
        //pre cache all images form network on adapter creation
        McLarenImageDownloader.cacheImages(context, imageUris, sizeLimit);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final ImageView imageView;
        switch (viewType) {
            case STATIC:
                imageView = new ImageView(container.getContext());
                break;
            case INTERACTIVE:
            default:
                imageView = new PhotoView(container.getContext());
                break;
        }
        imageView.setId(R.id.image);
        McLarenImageDownloader.loadImage(imageView, imageUris.get(position), sizeLimit);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public int getCount() {
        return imageUris.size();
    }

    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // pager adapter uses objects returned by #instantiateItem() as keys
        // as soon as the view itself returned it's OK to compare object links b/c we don't have real keys that
        // can be compared by value. Ssee https://developer.android.com/reference/android/support/v4/view/PagerAdapter
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
