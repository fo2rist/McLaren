package com.github.fo2rist.mclaren.tests.utils

import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.runner.AndroidJUnit4
import com.github.fo2rist.mclaren.utils.IntentUtils
import org.hamcrest.Matchers.any
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IntentUtilsTest{

    private val context: Context by lazy { InstrumentationRegistry.getTargetContext() }

    @Before
    fun setUp() {
        Intents.init()
        Intents.intending(any(Intent::class.java))
                .respondWith(Instrumentation.ActivityResult(0, null))
    }

    @Test
    fun test_openInBrowser_launches_view_intent() {
        val launched = IntentUtils.openInBrowser(context, "http://dummy.url")

        Assert.assertTrue(launched)
        Intents.intended(hasAction(Intent.ACTION_VIEW))
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}
