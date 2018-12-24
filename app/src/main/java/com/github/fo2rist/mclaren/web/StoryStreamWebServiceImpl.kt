package com.github.fo2rist.mclaren.web

import com.github.fo2rist.mclaren.BuildConfig
import com.github.fo2rist.mclaren.web.utils.executeAsync
import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.Objects.requireNonNull
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class StoryStreamWebServiceImpl @Inject
internal constructor(@param:Named("web-okhttp") private val client: OkHttpClient) : StoryStreamWebService {

    override suspend fun requestLatestFeed(): String? {
        return client.newCall(createLatestFeedRequest()).executeAsync()
    }

    override fun requestFeedPage(pageNumber: Int, callback: FeedWebService.FeedRequestCallback) {
        client.newCall(createFeedPageRequest(pageNumber))
                .enqueue(FeedWebService.FeedCallbackWrapper(pageNumber, callback))
    }

    private fun createLatestFeedRequest(): Request {
        return createFeedPageRequest(1) //StoryStream reacts on page=1 properly so we can use it for the top page
    }

    private fun createFeedPageRequest(pageNumber: Int): Request {
        val url = FEED_URL
                .newBuilder()
                .addQueryParameter("access_token", ACCESS_TOKEN)
                .addQueryParameter("page", pageNumber.toString())
                .addQueryParameter("rpp", STORIES_PER_PAGE.toString())
                .addQueryParameter("all_media", INCLUDE_ALL_MEDIA.toString())
                .build()

        return Request.Builder()
                .url(url)
                .headers(DEFAULT_HEADERS)
                .build()
    }

    companion object {

        private val FEED_URL = requireNonNull<HttpUrl>(HttpUrl.parse(BuildConfig.STORYSTREAM_FEED_URL))
        private const val ACCESS_TOKEN = BuildConfig.STORYSTREAM_TOKEN
        private const val STORIES_PER_PAGE = 20
        private const val INCLUDE_ALL_MEDIA = true
        private val DEFAULT_HEADERS = Headers.of(
                "Content-Type", "application/json; charset=utf-8"
        )
    }
}
