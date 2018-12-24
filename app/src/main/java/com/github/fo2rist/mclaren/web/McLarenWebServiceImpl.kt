package com.github.fo2rist.mclaren.web

import com.github.fo2rist.mclaren.BuildConfig
import com.github.fo2rist.mclaren.web.utils.executeAsync
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class McLarenWebServiceImpl @Inject
internal constructor(
    @param:Named("web-okhttp")
    private val client: OkHttpClient
) : McLarenFeedWebService, TransmissionWebService {

    override suspend fun requestLatestFeed(): String? {
        return client.newCall(createLatestFeedRequest()).executeAsync()
    }

    override fun requestFeedPage(pageNumber: Int, callback: FeedWebService.FeedRequestCallback) {
        client.newCall(createFeedPageRequest(pageNumber))
                .enqueue(FeedWebService.FeedCallbackWrapper(pageNumber, callback))
    }

    private fun createLatestFeedRequest(): Request = createFeedPageRequest(null)

    private fun createFeedPageRequest(pageNumber: Int? = null): Request {

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
        return client.newCall(createTransmissionRequest()).executeAsync()
    }

    private fun createTransmissionRequest(): Request {
        return Request.Builder()
                .url(MCLAREN_RACE_LIFE_DATA_URL)
                .headers(DEFAULT_HEADERS)
                .build()
    }

    companion object {

        private val FEED_URL = HttpUrl.parse(BuildConfig.MCLAREN_FEED_URL)!!
        private val MCLAREN_RACE_INFO_URL = HttpUrl.parse(BuildConfig.MCLAREN_RACE_INFO_URL)!!
        private val MCLAREN_RACE_LIFE_DATA_URL = HttpUrl.parse(BuildConfig.MCLAREN_RACE_LIFE_DATA_URL)!!
        private val DEFAULT_HEADERS = Headers.of(
                "Content-Type", "application/json; charset=utf-8",
                "Authorization", BuildConfig.MCLAREN_CDN_AUTH
        )
    }
}
