package com.github.fo2rist.mclaren.ui.previewscreen

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.github.fo2rist.mclaren.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WebPreviewFragmentTest {

    private val webView
        get() = KView { withId(R.id.web_view) }

    @Before
    fun setUp() {
        // fragment instance itself is not needed but that way we can get the very same bundle it starts with
        val fragment = WebPreviewFragment.newInstanceForMcLarenHtml("<html> </html>")
        launchFragmentInContainer<WebPreviewFragment>(fragment.arguments)
    }

    @Test
    fun `test layout is not empty`() {
        webView {
            isVisible()
        }
    }

}
