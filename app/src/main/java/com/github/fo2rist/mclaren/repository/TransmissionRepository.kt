package com.github.fo2rist.mclaren.repository

interface TransmissionRepository {
    /** Load Transmission and give existing if present. */
    fun loadTransmission()
    /** Reload Transmission. */
    fun refreshTransmission()
}
