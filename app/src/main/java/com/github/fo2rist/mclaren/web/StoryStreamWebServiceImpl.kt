package com.github.fo2rist.mclaren.web

import android.support.annotation.VisibleForTesting
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
internal class StoryStreamWebServiceImpl @Inject internal constructor(
    @param:Named("web-okhttp")
    private val client: OkHttpClient
) : StoryStreamWebService {

    override suspend fun requestLatestFeed(): String? {
        return client.newCall(createLatestFeedRequest()).executeAsync()
    }

    override suspend fun requestFeedPage(pageNumber: Int): String? {
        return client.newCall(createFeedPageRequest(pageNumber)).executeAsync()
    }

    private fun createLatestFeedRequest(): Request = createFeedPageRequest()

    @VisibleForTesting
    fun createFeedPageRequest(pageNumber: Int = 1): Request {
        // StoryStream reacts on page=1 properly so we can use #1 for the top page.
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

        private val FEED_URL = HttpUrl.get(BuildConfig.STORYSTREAM_FEED_URL)
        private const val ACCESS_TOKEN = BuildConfig.STORYSTREAM_TOKEN
        private const val STORIES_PER_PAGE = 20
        private const val INCLUDE_ALL_MEDIA = true
        private val DEFAULT_HEADERS = Headers.of(
                "Content-Type", "application/json; charset=utf-8"
        )
    }
}
