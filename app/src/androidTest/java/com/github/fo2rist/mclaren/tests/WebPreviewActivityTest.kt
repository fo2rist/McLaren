package com.github.fo2rist.mclaren.tests

import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.web.assertion.WebViewAssertions.webContent
import android.support.test.espresso.web.matcher.DomMatchers.hasElementWithXpath
import android.support.test.espresso.web.webdriver.Locator
import android.support.test.runner.AndroidJUnit4
import com.agoda.kakao.WebElementBuilder
import com.github.fo2rist.mclaren.pages.PreviewPage
import com.github.fo2rist.mclaren.testdata.FeedItems.HTML_ARTICLE_ITEM
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewActivity
import com.github.fo2rist.mclaren.utils.ActivityTestBase
import com.github.fo2rist.mclaren.utils.WebActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WebPreviewActivityTest : ActivityTestBase() {
    @get:Rule
    var rule = WebActivityTestRule(PreviewActivity::class.java, false, false)
    private val page = PreviewPage()

    @Test
    fun testLoadsArticle() {
        rule.launchActivity(PreviewActivity.createFeedItemIntent(context, HTML_ARTICLE_ITEM))

        page {
            webView {
                rootItem {
                    web.check(webContent(hasElementWithXpath("/html/body/div/p/strong")))
                    web.check(webContent(hasElementWithXpath("/html/body/div/h2/strong")))
                    web.check(webContent(hasElementWithXpath("/html/body/div/table/tbody/tr/td")))
                }
            }
        }
    }

    @Test
    fun testLayout() {
        rule.launchActivity(PreviewActivity.createFeedItemIntent(context, HTML_ARTICLE_ITEM))

        page {
            toolbarImage { isVisible() }
            webView { isDisplayed() }
            galleryView { doesNotExist() }
        }
    }

    @Test
    fun testToolbarImageInvisibleInUrlMode() {
        rule.launchActivity(PreviewActivity.createUrlIntent(context, "http://address_that_doesnt_exist"))

        page {
            toolbarImage { isGone() }
        }
    }

    fun WebElementBuilder.rootItem(interaction: WebElementBuilder.KWebInteraction.() -> Unit) =
            this.withElement(Locator.XPATH, "/", interaction)
}
