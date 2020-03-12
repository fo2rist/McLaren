package com.github.fo2rist.mclaren.ui.previewscreen;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.models.ImageUrl;
import com.github.fo2rist.mclaren.mvp.ImagePreviewContract;
import com.github.fo2rist.mclaren.ui.utils.SilentPageChangeListener;
import dagger.android.support.AndroidSupportInjection;
import java.util.List;
import javax.inject.Inject;

import static com.github.fo2rist.mclaren.ui.tools.McLarenImageDownloader.ImageSizeLimit;

/**
 * Displays list of images or a single one with zoom controls.
 */
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
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
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
        presenter.onStartWith(fetchBundleParameters());
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
    public void showImages(List<ImageUrl> imageUrls) {
        ImageGalleryAdapter galleryAdapter = new ImageGalleryAdapter(
                getContext(),
                imageUrls,
                ImageSizeLimit.FULLSCREEN,
                ImageGalleryAdapter.ImageViewType.INTERACTIVE);
        imagesPager.setAdapter(galleryAdapter);
    }
}
