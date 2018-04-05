package com.github.fo2rist.mclaren.ui.previewscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.models.ImageUrl;
import com.github.fo2rist.mclaren.mvp.ImagePreviewContract;
import com.github.fo2rist.mclaren.ui.utils.SilentPageChangeListener;
import dagger.android.support.AndroidSupportInjection;
import java.util.List;
import javax.inject.Inject;


public class ImagePreviewFragment extends Fragment implements ImagePreviewContract.View {
    private static final String ARG_FEED_ITEM = "feed_item";

    @Inject
    ImagePreviewContract.Presenter presenter;

    private ViewPager imagesPager;

    public static ImagePreviewFragment newInstanceForFeedItem(FeedItem feedItem) {
        ImagePreviewFragment fragment = new ImagePreviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_FEED_ITEM, feedItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_preview, container, false);
        imagesPager = rootView.findViewById(R.id.images_pager);
        imagesPager.addOnPageChangeListener(pageChangeListener);
        return rootView;
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new SilentPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            presenter.onScrolledTo(position);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStartWith(this, fetchBundleParameters());
    }

    @Nullable
    private FeedItem fetchBundleParameters() {
        Bundle args = getArguments();
        if (args == null) {
            return null;
        }

        return (FeedItem) args.getSerializable(ARG_FEED_ITEM);
    }

    @Override
    public void showImages(List<ImageUrl> imageUris) {
        ImageGalleryAdapter galleryAdapter = new ImageGalleryAdapter(getContext(), imageUris);
        imagesPager.setAdapter(galleryAdapter);
    }
}
