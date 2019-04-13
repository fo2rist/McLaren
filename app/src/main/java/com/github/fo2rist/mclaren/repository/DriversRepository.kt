package com.github.fo2rist.mclaren.repository

import android.support.annotation.VisibleForTesting
import com.github.fo2rist.mclaren.ui.models.Driver
import com.github.fo2rist.mclaren.ui.models.DriverId
import com.github.fo2rist.mclaren.ui.models.DriverProperty
import com.github.fo2rist.mclaren.utils.parseJson
import com.github.fo2rist.mclaren.web.RemoteConfigService
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * Provides info about Drivers.
 */
interface DriversRepository {

    /**
     * Get driver info by ID.
     * @throws IllegalArgumentException if the driver is not present.
     */
    fun getDriver(driver: DriverId): Driver
}

internal class DriversRepositoryImpl @Inject constructor(
    private val remoteConfigService: RemoteConfigService
) : DriversRepository {

    @VisibleForTesting
    val drivers: Map<DriverId, Driver> by lazy { loadDrivers() }

    private fun loadDrivers(): Map<DriverId, Driver> {
        val driversModelTypeToken = object : TypeToken<DriversConfigModel>() {}

        return parseJson(remoteConfigService.drivers, driversModelTypeToken)?.let {
            it.mapValues { (id, properties) ->
                Driver(id, properties)
            }
        } ?: emptyMap()
    }

    override fun getDriver(driver: DriverId): Driver {
        return drivers[driver]
                ?: throw IllegalArgumentException("Unknown driver: $driver") //TODO maybe use empty model instead
    }
}

private typealias DriverPropertiesConfigModel = Map<DriverProperty, String>
private typealias DriversConfigModel = Map<DriverId, DriverPropertiesConfigModel>
