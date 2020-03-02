package com.github.fo2rist.mclaren.ui.models

/**
 * IDs of all known drivers.
 */
enum class DriverId constructor(var id: String) {
    ALONSO("alonso"),
    BUTTON("button"),
    CAMARA("camara"),
    NORRIS("norris"),
    SAINZ("sainz"),
    TURVEY("turvey"),
    //removed resources
    //the IDs can not be removed now, before the remote config that mentions these drivers is removed
    //and the app updated to the new version and the app updated so no user have the app with one of these drivers
    DEVRIES("devries"),
    MATSUSHITA("matsushita"),
    VANBUREN("vanburen"),
    VANDOORNE("vandoorne"),
}

/**
 * Driver info for UI layer.
 */
data class Driver(
    private val id: DriverId,
    private val properties: Map<DriverProperty, String>
) {
    operator fun get(property: DriverProperty) = properties[property]

    fun getId(): String = id.id
}
