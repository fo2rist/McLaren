package com.github.fo2rist.mclaren.web.remoteconfig

import com.github.fo2rist.mclaren.web.remoteconfig.FirebaseRemoteConfigService
import com.google.firebase.FirebaseApp
import org.json.JSONArray
import org.json.JSONObject
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
        // this will load default config XML supplied with the app
        FirebaseApp.initializeApp(RuntimeEnvironment.application)
    }

    @Test
    fun `default config is not empty valid JSON`() {
        val firebaseRemoteConfigService = FirebaseRemoteConfigService()

        assertNotEquals(0, JSONArray(firebaseRemoteConfigService.calendar).length())
        assertNotEquals(0, JSONArray(firebaseRemoteConfigService.circuits).length())
        assertNotEquals(0, JSONArray(firebaseRemoteConfigService.driversOrderList).length())
        assertNotEquals(0, JSONObject(firebaseRemoteConfigService.drivers).length())
    }
}
