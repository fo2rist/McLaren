package com.github.fo2rist.mclaren.ui.models

/**
 * IDs of all known drivers.
 */
enum class DriverId constructor(
    /**
     * Unique string identifier associated with [DriverId].
     * Used as filesystem name to store external resources.
     */
    var filesystemId: String
) {
    ALONSO("alonso"),
    BUTTON("button"),
    CAMARA("camara"),
    NORRIS("norris"),
    SAINZ("sainz"),
    RICCIARDO("ricciardo"),
    TURVEY("turvey"),

    //region removed resources
    //the IDs can not be removed now, before the remote config that mentions these drivers is removed
    //and the app updated to the new version and the app updated so no user have the app with one of these drivers
    DEVRIES("devries"),
    MATSUSHITA("matsushita"),
    VANBUREN("vanburen"),
    VANDOORNE("vandoorne"),
    //endregion
}

/**
 * Driver info for UI layer.
 */
data class Driver(
    val id: DriverId,
    private val properties: Map<DriverProperty, String>
) {
    operator fun get(property: DriverProperty) = properties[property]

    fun getFilesystemId(): String = id.filesystemId
}
