package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.models.TransmissionItem
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
    private var trasmissionData: List<TransmissionItem>? = null

    private val webResponseHandler = object : TransmissionWebService.TransmissionRequestCallback {
        override fun onSuccess(url: URL, responseCode: Int, data: String?) {
            val transmission = parse(data).reversed()

            cache(transmission)
            pubSub.publish(PubSubEvent.TransmissionUpdateReady(transmission))
            pubSub.publish(PubSubEvent.LoadingFinished)
        }

        override fun onFailure(url: URL, responseCode: Int, connectionError: IOException?) {
            pubSub.publish(PubSubEvent.LoadingError)
            pubSub.publish(PubSubEvent.LoadingFinished)
        }
    }

    private fun parse(data: String?): List<TransmissionItem> {
        val parsedWebResponse = SafeJsonParser<Transmission>(Transmission::class.java).parse(data)
        return TransmissionConverter.convert(parsedWebResponse)
    }

    override fun loadTransmission() {
        publishCachedData()

        pubSub.publish(PubSubEvent.LoadingStarted)
        webService.requestTransmission(webResponseHandler)
    }


    private fun cache(transmission: List<TransmissionItem>) {
        this.trasmissionData = transmission
    }

    private fun publishCachedData() {
        val cachedData = trasmissionData

        if (cachedData?.isEmpty() == false) {
            pubSub.publish(PubSubEvent.TransmissionUpdateReady(cachedData))
        }
    }
}
