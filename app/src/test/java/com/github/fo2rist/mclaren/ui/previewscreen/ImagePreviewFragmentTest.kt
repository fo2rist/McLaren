package com.github.fo2rist.mclaren.ui.previewscreen

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.common.views.KView
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.testdata.FeedItems.TWITTER_GALLERY_ITEM
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@Ignore("Need to support/avoid injection, simplest option — make class open, override injection and pass mock")
class ImagePreviewFragmentTest {
    private val imagePager
        get() = KView { withId(R.id.images_pager) }

    @Before
    fun setUp() {
        val fragment = ImagePreviewFragment.newInstanceForFeedItem(TWITTER_GALLERY_ITEM)
        val scenario = launchFragmentInContainer<ImagePreviewFragment>(fragment.arguments)
        scenario.onFragment {
            it.presenter = mock()
        }

    }

    @Test
    fun testLayoutNotEmpty() {
        imagePager {
            isDisplayed()
        }
    }
}
