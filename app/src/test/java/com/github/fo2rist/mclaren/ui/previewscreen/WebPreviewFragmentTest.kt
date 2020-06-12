package com.github.fo2rist.mclaren.ui.previewscreen

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.fo2rist.mclaren.R
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@Ignore("Need to rewrite test for AndroidX fragment scenarios")
class WebPreviewFragmentTest {
    private lateinit var activity: FragmentActivity

    @Before
    fun setUp() {
        val fragment = WebPreviewFragment.newInstanceForMcLarenHtml("<html></html>")
        launchFragmentInContainer<WebPreviewFragment>()
    }

    @Test
    fun `test layout is not empty`() {
        assertNotNull(activity.findViewById(R.id.web_view))
    }
}
