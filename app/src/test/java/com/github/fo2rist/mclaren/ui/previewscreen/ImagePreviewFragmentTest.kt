package com.github.fo2rist.mclaren.ui.previewscreen

import androidx.fragment.app.FragmentActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.testdata.FeedItems.TWITTER_GALLERY_ITEM
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@Ignore("Need to rewrite test for AndroidX fragment scenarios")
class ImagePreviewFragmentTest {
    private val activity: FragmentActivity? = null

    @Before
    fun setUp() {
        val fragment = ImagePreviewFragment.newInstanceForFeedItem(TWITTER_GALLERY_ITEM)
        //launchFragmentInContainer<ImagePreviewFragment>()
    }

    @Test
    fun testLayoutNotEmpty() {
        Assert.assertNotNull(activity!!.findViewById(R.id.images_pager))
    }
}
