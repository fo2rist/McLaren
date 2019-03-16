package com.github.fo2rist.mclaren.web

import com.google.firebase.FirebaseApp
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class FirebaseRemoteConfigServiceTest {

    @Before
    fun setUp() {
        // in tests Firebase is not initialized by default, so have to do that manually
        FirebaseApp.initializeApp(RuntimeEnvironment.application)
    }

    @Test
    fun `test initial config is not empty`() {
        assertNotEquals(0, FirebaseRemoteConfigService().calendar.length)
    }
}
