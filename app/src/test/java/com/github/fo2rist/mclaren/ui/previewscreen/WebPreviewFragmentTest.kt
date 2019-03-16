package com.github.fo2rist.mclaren.ui.previewscreen

import android.support.v4.app.FragmentActivity
import com.github.fo2rist.mclaren.R
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WebPreviewFragmentTest : BasePreviewActivityTest() {
    private lateinit var activity: FragmentActivity

    @Before
    fun setUp() {
        val fragment = WebPreviewFragment.newInstanceForMcLarenHtml("<html></html>")

        activity = startWithFragment(fragment)
    }

    @Test
    fun `test layout is not empty`() {
        assertNotNull(activity.findViewById(R.id.web_view))
    }
}
