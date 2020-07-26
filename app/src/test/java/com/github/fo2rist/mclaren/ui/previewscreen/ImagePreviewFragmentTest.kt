package com.github.fo2rist.mclaren.ui.previewscreen

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.common.views.KView
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.testdata.FeedItems.TWITTER_GALLERY_ITEM
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImagePreviewFragmentTest {
    private val imagePager
        get() = KView { withId(R.id.images_pager) }

    @Before
    fun setUp() {
        // fragment instance itself is not required, it's a way to test how intent arguments are passed
        val fragment = ImagePreviewFragment.newInstanceForFeedItem(TWITTER_GALLERY_ITEM)
        launchFragmentInContainer<TestImagePreviewFragment>(fragment.arguments)
    }

    @Test
    fun testLayoutNotEmpty() {
        imagePager {
            isDisplayed()
        }
    }

    // special version that doesn't trigger android dagger, and inject mocks instead
    class TestImagePreviewFragment : ImagePreviewFragment() {
        override fun injectDependencies() {
            presenter = mock()
        }
    }
}
