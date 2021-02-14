package com.github.fo2rist.mclaren.web.remoteconfig

import com.github.fo2rist.mclaren.BuildConfig
import com.github.fo2rist.mclaren.R
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.internal.ConfigFetchHandler.DEFAULT_MINIMUM_FETCH_INTERVAL_IN_SECONDS
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

    /**
     * Drivers display order list JSON.
     */
    val driversOrderList: String
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
                .setMinimumFetchIntervalInSeconds(
                        if (BuildConfig.DEBUG) 30 else DEFAULT_MINIMUM_FETCH_INTERVAL_IN_SECONDS)
                .build()
        this.setConfigSettingsAsync(configSettings)
        this.setDefaultsAsync(R.xml.app_config_defautls)
    }

    override fun fetchConfig() {
        firebaseConfig.fetchAndActivate()
                .addOnCompleteListener(this::onRemoteConfigFetched)
    }

    private fun onRemoteConfigFetched(task: Task<Boolean>) {
        if (!task.isSuccessful) {
            Timber.e(task.exception, "Unable to fetch config from Firebase")
        }
    }

    override val calendar: String
        get() = firebaseConfig.getString(RACE_CALENDAR)

    override val circuits: String
        get() = firebaseConfig.getString(CIRCUITS)

    override val drivers: String
        get() = firebaseConfig.getString(DRIVERS)

    override val driversOrderList: String
        get() = firebaseConfig.getString(DRIVERS_ORDER_LIST)

    private companion object ConfigKeys {
        const val RACE_CALENDAR = "calendar"
        const val CIRCUITS = "circuits"
        const val DRIVERS = "drivers"
        const val DRIVERS_ORDER_LIST = "drivers_order_list"
    }
}
