package com.github.fo2rist.mclaren.ui.previewscreen;

import android.support.v4.app.FragmentActivity;

import com.github.fo2rist.mclaren.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static com.github.fo2rist.mclaren.testdata.FeedItems.TWITTER_GALLERY_ITEM;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class ImagePreviewFragmentTest extends BasePreviewActivityTest {

    private FragmentActivity activity;

    @Before
    public void setUp() {
        ImagePreviewFragment fragment = ImagePreviewFragment.newInstanceForFeedItem(TWITTER_GALLERY_ITEM);
        activity = startWithFragment(fragment);
    }

    @Test
    public void testLayoutNotEmpty() {
        assertNotNull(activity.findViewById(R.id.images_pager));
    }
}
