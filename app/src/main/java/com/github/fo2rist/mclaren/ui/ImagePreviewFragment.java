package com.github.fo2rist.mclaren.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.mvp.ImagePreviewContract;
import com.github.fo2rist.mclaren.ui.adapters.ImageGalleryAdapter;
import com.github.fo2rist.mclaren.ui.presenters.ImagePreviewPresenter;


public class ImagePreviewFragment extends Fragment implements ImagePreviewContract.View {
    private static final String ARG_FEED_ITEM = "feed_item";
    private ImagePreviewContract.Presenter presenter;
    private ViewPager imagesPager;

    public static ImagePreviewFragment newInstanceForFeedItem(FeedItem feedItem) {
        ImagePreviewFragment fragment = new ImagePreviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FEED_ITEM, feedItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        presenter = new ImagePreviewPresenter();
        View rootView = inflater.inflate(R.layout.fragment_image_preview, container, false);
        Bundle arguments = getArguments();
        if (arguments == null) {
            return rootView;
        }
        FeedItem feedItem = (FeedItem) arguments.getSerializable(ARG_FEED_ITEM);
        imagesPager = rootView.findViewById(R.id.images_pager);

        presenter.onStartWith(this, feedItem);
        return rootView;
    }

    @Override
    public void showImages(String[] imageUris) {
        ImageGalleryAdapter galleryAdapter = new ImageGalleryAdapter(getContext(), imageUris);
        imagesPager.setAdapter(galleryAdapter);
    }
}
