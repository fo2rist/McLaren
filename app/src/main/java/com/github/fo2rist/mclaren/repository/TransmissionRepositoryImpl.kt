package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.web.TransmissionWebService
import com.github.fo2rist.mclaren.web.models.Transmission
import com.google.gson.Gson
import timber.log.Timber
import java.io.IOException
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransmissionRepositoryImpl
@Inject constructor(
        private val webService: TransmissionWebService
) : TransmissionRepository {

    private val webResponseHandler = object : TransmissionWebService.TransmissionRequestCallback {
        override fun onSuccess(url: URL, responseCode: Int, data: String?) {
            val transmission = Gson().fromJson<Transmission>(data, Transmission::class.java)
            Timber.d(transmission.toString())
        }

        override fun onFailure(url: URL, responseCode: Int, connectionError: IOException?) {
            Timber.d("ERROR: %d", responseCode)
        }

    }

    override fun loadTransmission() {
        webService.requestTransmission(webResponseHandler)
    }
}
