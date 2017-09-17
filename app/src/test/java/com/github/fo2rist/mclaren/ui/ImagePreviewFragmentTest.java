package com.github.fo2rist.mclaren.ui;

import com.github.fo2rist.mclaren.BuildConfig;
import com.github.fo2rist.mclaren.R;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.github.fo2rist.mclaren.testdata.FeedItems.TWITTER_GALLERY_ITEM;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startVisibleFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ImagePreviewFragmentTest {

    @Test
    public void testLayout() throws Exception {
        ImagePreviewFragment fragment = ImagePreviewFragment.newInstanceForFeedItem(TWITTER_GALLERY_ITEM);
        startVisibleFragment(fragment, PreviewActivity.class, R.id.content_frame);

        assertNotNull(fragment.getActivity().findViewById(R.id.images_pager));
    }
}
