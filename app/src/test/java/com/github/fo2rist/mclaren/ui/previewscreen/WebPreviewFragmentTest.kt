package com.github.fo2rist.mclaren.ui.previewscreen

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.testing.launchFragmentInContainer
import com.github.fo2rist.mclaren.R
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4;


@RunWith(AndroidJUnit4::class)
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
