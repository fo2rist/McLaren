package com.github.fo2rist.mclaren.ui.adapters;

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

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
