package com.github.fo2rist.mclaren.tests

import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.web.assertion.WebViewAssertions.webContent
import androidx.test.espresso.web.matcher.DomMatchers.hasElementWithXpath
import androidx.test.espresso.web.webdriver.Locator
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.web.WebElementBuilder
import com.github.fo2rist.mclaren.pages.PreviewPage
import com.github.fo2rist.mclaren.testdata.FeedItems.ARTICLE_ITEM_WITH_LINKS
import com.github.fo2rist.mclaren.testdata.FeedItems.ARTICLE_ITEM_WITH_TABLES
import com.github.fo2rist.mclaren.testutilities.ActivityTestBase
import com.github.fo2rist.mclaren.testutilities.WebActivityTestRule
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WebPreviewActivityTest : ActivityTestBase() {
    @get:Rule
    var rule = WebActivityTestRule(PreviewActivity::class.java, false, false)
    private val page = PreviewPage()

    @Test
    fun testLayout_with_SpecificContent_in_ArticleMode() {
        rule.launchActivity(PreviewActivity.createFeedItemIntent(context, ARTICLE_ITEM_WITH_TABLES))

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
    fun testLayout_with_HeaderImages_and_WebView_in_ArticleMode() {
        rule.launchActivity(PreviewActivity.createFeedItemIntent(context, ARTICLE_ITEM_WITH_LINKS))

        page {
            toolbarImages {
                isDisplayed()
                isAtPage(0)
                swipeLeft()
                isAtPage(1)
            }
            webView {
                isDisplayed()
            }
            galleryView {
                doesNotExist()
            }
        }
    }

    @Test
    fun testLayout_without_HeaderImages_in_UrlMode() {
        rule.launchActivity(PreviewActivity.createUrlIntent(context, "http://address_that_doesnt_exist"))

        page {
            toolbarImages { isGone() }
        }
    }

    private fun WebElementBuilder.rootItem(interaction: WebElementBuilder.KWebInteraction.() -> Unit) =
            this.withElement(Locator.XPATH, "/", interaction)
}
