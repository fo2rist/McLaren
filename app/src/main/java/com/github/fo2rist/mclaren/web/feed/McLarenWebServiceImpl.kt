package com.github.fo2rist.mclaren.web.feed

import androidx.annotation.VisibleForTesting
import com.github.fo2rist.mclaren.BuildConfig
import com.github.fo2rist.mclaren.web.transmission.TransmissionWebService
import okhttp3.Headers
import okhttp3.Headers.Companion.headersOf
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

private val FEED_URL = BuildConfig.MCLAREN_FEED_URL.toHttpUrl()
private val MCLAREN_RACE_LIFE_DATA_URL = BuildConfig.MCLAREN_RACE_LIFE_DATA_URL.toHttpUrl()
private val DEFAULT_CDN_HEADERS = headersOf(
        "Content-Type", "application/json; charset=utf-8",
        "Authorization", BuildConfig.MCLAREN_CDN_AUTH)
private val CONTENT_JSON_HEADERS = headersOf(
        "Content-Type", "application/json; charset=utf-8")

/**
 * Service that provides access to McLaren own APIs.
 * See [McLarenFeedWebService] & [TransmissionWebService]
 */
@Singleton
internal class McLarenWebServiceImpl @Inject internal constructor(
    @Named("web-okhttp")
    client: OkHttpClient
) : BaseFeedWebService(client), McLarenFeedWebService, TransmissionWebService {

    @VisibleForTesting
    override fun createFeedPageRequest(pageNumber: Int?): Request {

        val urlBuilder = FEED_URL.newBuilder()

        if (pageNumber != null) {
            urlBuilder.addQueryParameter("p", pageNumber.toString())
        }
        //By default it's GET unless .method or .post aren't called
        return Request.Builder()
                .url(urlBuilder.build())
                .headers(DEFAULT_CDN_HEADERS)
                .build()
    }

    override suspend fun requestTransmission(): String? {
        return execute(createTransmissionRequest())
    }

    private fun createTransmissionRequest(): Request {
        return Request.Builder()
                .url(MCLAREN_RACE_LIFE_DATA_URL)
                .headers(CONTENT_JSON_HEADERS)
                .build()
    }
}
