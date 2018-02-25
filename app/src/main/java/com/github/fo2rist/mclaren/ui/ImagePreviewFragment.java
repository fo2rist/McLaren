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
        View rootView = inflater.inflate(R.layout.fragment_image_preview, container, false);
        imagesPager = rootView.findViewById(R.id.images_pager);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FeedItem feedItem = fetchBundleParameters();
        if (feedItem != null) {
            presenter = new ImagePreviewPresenter();
            presenter.onStartWith(this, feedItem);
        }
    }

    private FeedItem fetchBundleParameters() {
        Bundle args = getArguments();
        if (args == null) {
            return null;
        }

        return (FeedItem) args.getSerializable(ARG_FEED_ITEM);
    }

    @Override
    public void showImages(String[] imageUris) {
        ImageGalleryAdapter galleryAdapter = new ImageGalleryAdapter(getContext(), imageUris);
        imagesPager.setAdapter(galleryAdapter);
    }
}
