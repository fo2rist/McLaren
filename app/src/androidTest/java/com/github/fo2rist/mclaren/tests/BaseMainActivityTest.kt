package com.github.fo2rist.mclaren.tests

import android.content.Context
import android.support.test.rule.ActivityTestRule

import com.github.fo2rist.mclaren.pages.MainPage

import com.github.fo2rist.mclaren.ui.MainActivity
import org.junit.Before
import org.junit.Rule

open class BaseMainActivityTest {
    @JvmField
    protected var context: Context? = null
    @JvmField
    protected var mainPage: MainPage? = null

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    @Throws(InterruptedException::class)
    open fun setUp() {
        context = activityRule.activity
        mainPage = MainPage()
    }

}
