package com.github.fo2rist.mclaren.tests

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Context
import android.content.Intent
import android.support.annotation.CallSuper
import android.support.test.espresso.intent.Intents
import android.support.test.rule.ActivityTestRule
import com.github.fo2rist.mclaren.pages.MainPage
import com.github.fo2rist.mclaren.ui.MainActivity
import org.hamcrest.Matchers.any
import org.junit.After
import org.junit.Rule

open class BaseMainActivityTest {

    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    protected val context: Context by lazy { activityRule.activity }
    protected val mainPage = MainPage()

    @After
    @CallSuper
    fun tearDown() {
        endIntentsInterception()
    }

    private var intentInterceptionActive = false

    /**
     * Start intercepting all intents and responding with given result.
     * Only active until current test is finished with [tearDown].
     */
    protected fun interceptIntents(responseResult: Int = Activity.RESULT_OK) {
        if (intentInterceptionActive) {
            return
        }

        intentInterceptionActive = true
        Intents.init()
        Intents.intending(any(Intent::class.java))
                .respondWith(ActivityResult(responseResult, null))
    }

    /** Stop interception intents. */
    private fun endIntentsInterception() {
        if (!intentInterceptionActive) {
            return
        }

        try {
            Intents.release()
        } catch (exc: Exception) {
            //we don't really care
        } finally {
            intentInterceptionActive = false
        }
    }
}
