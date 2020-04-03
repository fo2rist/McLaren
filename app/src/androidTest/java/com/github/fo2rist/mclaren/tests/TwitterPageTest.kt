package com.github.fo2rist.mclaren.tests

import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.pages.FeedPage
import org.junit.Before
import org.junit.Test

class TwitterPageTest : BaseMainActivityTest() {

    private val feedPage = FeedPage()

    @Before
    fun setUp() {
        mainPage.navigateToMenuItem(R.id.nav_twitter_team)
    }

    @Test
    fun testFeedListPresent() {
        feedPage.feedList.isDisplayed()
    }
}
