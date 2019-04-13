package com.github.fo2rist.mclaren.ui.models

/**
 * IDs of all known drivers.
 */
enum class DriverId constructor(var id: String) {
    ALONSO("alonso"),
    BUTTON("button"),
    CAMARA("camara"),
    DEVRIES("devries"),
    MATSUSHITA("matsushita"),
    NORRIS("norris"),
    SAINZ("sainz"),
    TURVEY("turvey"),
    VANBUREN("vanburen"),
    VANDOORNE("vandoorne")
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
