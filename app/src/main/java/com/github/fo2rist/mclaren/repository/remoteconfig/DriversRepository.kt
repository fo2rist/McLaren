package com.github.fo2rist.mclaren.repository.remoteconfig

import com.github.fo2rist.mclaren.ui.models.Driver
import com.github.fo2rist.mclaren.ui.models.DriverId
import com.github.fo2rist.mclaren.ui.models.DriverProperty
import com.github.fo2rist.mclaren.utils.parseJson
import com.github.fo2rist.mclaren.web.remoteconfig.RemoteConfigService
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * Provides info about Drivers.
 */
interface DriversRepository {

    /**
     * Get list of supported drivers in priority order.
     */
    val driversList: List<DriverId>

    /**
     * Get driver info by ID.
     * @throws IllegalArgumentException if the driver is not present in [driversList].
     */
    fun getDriver(driverId: DriverId): Driver
}

private typealias DriverPropertiesConfigModel = Map<DriverProperty, String>
private typealias DriversConfigModel = Map<DriverId, DriverPropertiesConfigModel>

internal class DriversRepositoryImpl @Inject constructor(
    private val remoteConfigService: RemoteConfigService
) : DriversRepository {

    val drivers: List<Driver> by lazy { loadDrivers() }

    private fun loadDrivers(): List<Driver> {
        val driversModelTypeToken = object : TypeToken<DriversConfigModel>() {}
        val driversListTypeToken = object : TypeToken<List<DriverId>>() {}

        val driversInfoMap = parseJson(remoteConfigService.drivers, driversModelTypeToken)
                ?.mapValues { (id, properties) ->
                    Driver(id, properties)
                } ?: emptyMap()
        val driversOrderList = parseJson(remoteConfigService.driversOrderList, driversListTypeToken)
                ?: emptyList()

        return driversOrderList.mapNotNull {
            driversInfoMap[it]
        }
    }

    override val driversList: List<DriverId>
        get() = drivers.map { it.id }

    override fun getDriver(driverId: DriverId): Driver {
        return drivers.find { it.id == driverId }
                ?: throw IllegalArgumentException("Unknown driver: $driverId")
    }
}
