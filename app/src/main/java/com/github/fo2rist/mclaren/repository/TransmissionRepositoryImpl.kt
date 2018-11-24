package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.models.TransmissionInfo
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.web.SafeJsonParser
import com.github.fo2rist.mclaren.web.TransmissionWebService
import com.github.fo2rist.mclaren.web.models.Transmission
import java.io.IOException
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransmissionRepositoryImpl @Inject constructor(
        private val webService: TransmissionWebService,
        private val eventBus: TransmissionRepositoryEventBus
) : TransmissionRepository {

    private var transmissionData: TransmissionInfo? = null

    private val webResponseHandler = object : TransmissionWebService.TransmissionRequestCallback {
        override fun onSuccess(url: URL, responseCode: Int, data: String?) {
            val transmission = parse(data)

            cache(transmission)
            eventBus.publish(LoadingEvent.TransmissionUpdateReady(transmission))
            eventBus.publish(LoadingEvent.LoadingFinished)
        }

        override fun onFailure(url: URL, responseCode: Int, connectionError: IOException?) {
            eventBus.publish(LoadingEvent.LoadingError)
            eventBus.publish(LoadingEvent.LoadingFinished)
        }
    }

    private fun parse(data: String?): TransmissionInfo {
        val parsedWebResponse = SafeJsonParser<Transmission>(Transmission::class.java).parse(data)
        return TransmissionConverter.convert(parsedWebResponse)
    }

    override fun loadTransmission() {
        publishCachedData()
        refreshTransmission()
    }

    override fun refreshTransmission() {
        eventBus.publish(LoadingEvent.LoadingStarted)
        webService.requestTransmission(webResponseHandler)
    }

    private fun cache(transmission: TransmissionInfo) {
        this.transmissionData = transmission
    }

    private fun publishCachedData() {
        val cachedData = transmissionData

        if (cachedData != null) {
            eventBus.publish(LoadingEvent.TransmissionUpdateReady(cachedData))
        }
    }
}
