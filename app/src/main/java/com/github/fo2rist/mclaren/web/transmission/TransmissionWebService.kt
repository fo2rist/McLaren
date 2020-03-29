package com.github.fo2rist.mclaren.web.transmission

import com.github.fo2rist.mclaren.web.utils.BadResponse
import java.net.ConnectException

/**
 * Web-service that provides real-time team radio & news transmission during the race.
 */
interface TransmissionWebService {

    /**
     * Request transmission data.
     * @return content as string
     * @throws ConnectException if networking error occurs
     * @throws BadResponse if server responds with 4xx-5xx code
     */
    @Throws(BadResponse::class, ConnectException::class)
    suspend fun requestTransmission(): String?
}
