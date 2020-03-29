package com.github.fo2rist.mclaren.web

import android.support.annotation.VisibleForTesting
import com.github.fo2rist.mclaren.BuildConfig
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

private val FEED_URL = HttpUrl.get(BuildConfig.MCLAREN_FEED_URL)
private val MCLAREN_RACE_INFO_URL = HttpUrl.get(BuildConfig.MCLAREN_RACE_INFO_URL)
private val MCLAREN_RACE_LIFE_DATA_URL = HttpUrl.get(BuildConfig.MCLAREN_RACE_LIFE_DATA_URL)
private val DEFAULT_HEADERS = Headers.of(
        "Content-Type", "application/json; charset=utf-8",
        "Authorization", BuildConfig.MCLAREN_CDN_AUTH)

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
                .headers(DEFAULT_HEADERS)
                .build()
    }

    override suspend fun requestTransmission(): String? {
        return execute(createTransmissionRequest())
    }

    private fun createTransmissionRequest(): Request {
        return Request.Builder()
                .url(MCLAREN_RACE_LIFE_DATA_URL)
                .headers(DEFAULT_HEADERS)
                .build()
    }
}
