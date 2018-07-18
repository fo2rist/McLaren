package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.models.TransmissionInfo
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryPubSub.PubSubEvent
import com.github.fo2rist.mclaren.web.SafeJsonParser
import com.github.fo2rist.mclaren.web.TransmissionWebService
import com.github.fo2rist.mclaren.web.models.Transmission
import java.io.IOException
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransmissionRepositoryImpl
@Inject constructor(
        private val webService: TransmissionWebService,
        private val pubSub: TransmissionRepositoryPubSub
) : TransmissionRepository {
    private var trasmissionData: TransmissionInfo? = null

    private val webResponseHandler = object : TransmissionWebService.TransmissionRequestCallback {
        override fun onSuccess(url: URL, responseCode: Int, data: String?) {
            val transmission = parse(data)

            cache(transmission)
            pubSub.publish(PubSubEvent.TransmissionUpdateReady(transmission))
            pubSub.publish(PubSubEvent.LoadingFinished)
        }

        override fun onFailure(url: URL, responseCode: Int, connectionError: IOException?) {
            pubSub.publish(PubSubEvent.LoadingError)
            pubSub.publish(PubSubEvent.LoadingFinished)
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
        pubSub.publish(PubSubEvent.LoadingStarted)
        webService.requestTransmission(webResponseHandler)
    }

    private fun cache(transmission: TransmissionInfo) {
        this.trasmissionData = transmission
    }

    private fun publishCachedData() {
        val cachedData = trasmissionData

        if (cachedData != null) {
            pubSub.publish(PubSubEvent.TransmissionUpdateReady(cachedData))
        }
    }
}
