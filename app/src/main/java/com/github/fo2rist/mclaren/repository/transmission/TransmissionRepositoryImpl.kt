package com.github.fo2rist.mclaren.repository.transmission

import com.github.fo2rist.mclaren.models.TransmissionInfo
import com.github.fo2rist.mclaren.repository.transmission.TransmissionRepositoryEventBus.LoadingEvent
import com.github.fo2rist.mclaren.web.utils.SafeJsonParser
import com.github.fo2rist.mclaren.web.transmission.TransmissionWebService
import com.github.fo2rist.mclaren.web.models.TransmissionData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TransmissionRepositoryImpl @Inject constructor(
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
            } catch (exc: IOException) {
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
    val parsedWebResponse = SafeJsonParser(TransmissionData::class.java).parse(this)
    return TransmissionConverter.convert(parsedWebResponse)
}
