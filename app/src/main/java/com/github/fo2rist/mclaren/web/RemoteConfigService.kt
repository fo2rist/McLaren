package com.github.fo2rist.mclaren.web

import com.github.fo2rist.mclaren.BuildConfig
import com.github.fo2rist.mclaren.R
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Shared global application configuration that's same for all users.
 * Configuration not guaranteed to be the latest one, fetching is async by nature.
 */
interface RemoteConfigService {
    /** Load latest config from server. */
    fun fetchConfig()

    /**
     * Race calendar JSON.
     */
    val calendar: String

    /**
     * Circuits info JSON.
     */
    val circuits: String

    /**
     * Drivers info JSON.
     */
    val drivers: String
}

/**
 * [RemoteConfigService] provided by Firebase.
 */
@Singleton
class FirebaseRemoteConfigService @Inject constructor() : RemoteConfigService {

    private val firebaseConfig: FirebaseRemoteConfig by lazy {
        FirebaseRemoteConfig.getInstance().also { it.init() }
    }

    private fun FirebaseRemoteConfig.init() {
        // set low latency debug mode for debug builds
        val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()
        this.setConfigSettings(configSettings)
        this.setDefaults(R.xml.app_config_defautls)
    }

    override fun fetchConfig() {
        firebaseConfig.fetch()
                .addOnCompleteListener(this::onRemoteConfigFetched)
    }

    private fun onRemoteConfigFetched(task: Task<Void>) {
        if (task.isSuccessful) {
            firebaseConfig.activateFetched()
        } else {
            Timber.e(task.exception, "Unable to fetch config from Firebase")
        }
    }

    override val calendar: String
        get() = firebaseConfig.getString(RACE_CALENDAR)

    override val circuits: String
        get() = firebaseConfig.getString(CIRCUITS)

    override val drivers: String
        get() = firebaseConfig.getString(DRIVERS)

    private companion object ConfigKeys {
        const val RACE_CALENDAR = "calendar"
        const val CIRCUITS = "circuits"
        const val DRIVERS = "drivers"
    }
}
