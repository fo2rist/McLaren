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
import com.github.fo2rist.mclaren.web.McLarenImageDownloader.ImageSizeType;
import java.util.List;

public class ImageGalleryAdapter extends PagerAdapter {

    private final List<ImageUrl> imageUris;

    public ImageGalleryAdapter(Context context, List<ImageUrl> imageUris) {
        this.imageUris = imageUris;
        //pre cache all images form network on adapter creation
        McLarenImageDownloader.cacheImages(context, imageUris, ImageSizeType.FULLSCREEN);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new PhotoView(container.getContext());
        imageView.setId(R.id.image);
        McLarenImageDownloader.loadImage(imageView, imageUris.get(position), ImageSizeType.FULLSCREEN);
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
