package com.github.fo2rist.mclaren.repository.transmission

/**
 * Race Team radio & News repository.
 * @see com.github.fo2rist.mclaren.web.TransmissionWebService
 */
interface TransmissionRepository {

    /** Load Transmission and give existing if present. */
    fun loadTransmission()

    /** Reload Transmission. */
    fun refreshTransmission()
}
