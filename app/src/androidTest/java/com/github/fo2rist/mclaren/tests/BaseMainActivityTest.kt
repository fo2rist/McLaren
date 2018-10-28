package com.github.fo2rist.mclaren.tests

import android.content.Context
import android.support.annotation.CallSuper
import android.support.test.rule.ActivityTestRule

import com.github.fo2rist.mclaren.pages.MainPage

import com.github.fo2rist.mclaren.ui.MainActivity
import org.junit.Before
import org.junit.Rule

open class BaseMainActivityTest {

    protected lateinit var context: Context
    protected lateinit var mainPage: MainPage

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    @CallSuper
    open fun setUp() {
        context = activityRule.activity
        mainPage = MainPage()
    }
}
