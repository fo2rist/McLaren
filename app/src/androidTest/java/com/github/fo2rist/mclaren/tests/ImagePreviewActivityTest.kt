package com.github.fo2rist.mclaren.tests

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.github.fo2rist.mclaren.pages.PreviewPage
import com.github.fo2rist.mclaren.testdata.FeedItems.GALLERY_ITEM
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewActivity
import com.github.fo2rist.mclaren.utils.ActivityTestBase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImagePreviewActivityTest : ActivityTestBase() {
    @get:Rule
    var rule = ActivityTestRule(PreviewActivity::class.java, false, false)
    private val page = PreviewPage()

    @Test
    fun testLoadsGallery() {
        rule.launchActivity(PreviewActivity.createFeedItemIntent(context, GALLERY_ITEM))


        page {
            toolbarImage{
                isGone()
            }

            galleryView {
                isDisplayed()
                swipeLeft()
            }
        }
    }
}
