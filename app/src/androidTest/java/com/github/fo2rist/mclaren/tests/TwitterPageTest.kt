package com.github.fo2rist.mclaren.tests

import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.pages.FeedPage
import org.junit.Test

class TwitterPageTest : BaseMainActivityTest() {

    private val feedPage = FeedPage()

    @Test
    fun testTeamTweetsPresent() {
        mainPage.navigateToMenuItem(R.id.nav_twitter_team)

        feedPage.feedList.isDisplayed()
    }

    @Test
    fun testDriver1TweetsPresent() {
        mainPage.navigateToMenuItem(R.id.nav_twitter_lando)

        feedPage.feedList.isDisplayed()
    }

    @Test
    fun testDriver2TweetsPresent() {
        mainPage.navigateToMenuItem(R.id.nav_twitter_daniel)

        feedPage.feedList.isDisplayed()
    }
}
