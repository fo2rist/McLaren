package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.models.TransmissionInfo
import com.github.fo2rist.mclaren.repository.TransmissionRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.web.SafeJsonParser
import com.github.fo2rist.mclaren.web.TransmissionWebService
import com.github.fo2rist.mclaren.web.models.Transmission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransmissionRepositoryImpl @Inject constructor(
        private val webService: TransmissionWebService,
        private val eventBus: TransmissionRepositoryEventBus
) : TransmissionRepository {
    private val mainScope = CoroutineScope(Main)

    private var transmissionData: TransmissionInfo? = null

    override fun loadTransmission() {
        publishCachedData()
        refreshTransmission()
    }

    override fun refreshTransmission() {
        mainScope.launch {
            eventBus.publish(LoadingEvent.LoadingStarted)
            try {
                val transmission = webService.requestTransmission().parse()

                cache(transmission)
                eventBus.publish(LoadingEvent.TransmissionUpdateReady(transmission))
            } catch (exc: Exception) {
                eventBus.publish(LoadingEvent.LoadingError)
            }
            eventBus.publish(LoadingEvent.LoadingFinished)
        }
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

/* Takes raw transmission response as string and parses it. */
private fun String?.parse(): TransmissionInfo {
    val parsedWebResponse = SafeJsonParser<Transmission>(Transmission::class.java).parse(this)
    return TransmissionConverter.convert(parsedWebResponse)
}
