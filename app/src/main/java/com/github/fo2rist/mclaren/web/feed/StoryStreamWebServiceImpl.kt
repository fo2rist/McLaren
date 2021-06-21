package com.github.fo2rist.mclaren.web.feed

import com.github.fo2rist.mclaren.BuildConfig
import okhttp3.Headers
import okhttp3.Headers.Companion.headersOf
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
internal class StoryStreamWebServiceImpl @Inject internal constructor(
    @Named("web-okhttp")
    client: OkHttpClient
) : BaseFeedWebService(client), StoryStreamWebService {

    override fun createFeedPageRequest(pageNumber: Int?): Request {
        val url = FEED_URL
                .newBuilder()
                .addQueryParameter("access_token", ACCESS_TOKEN)
                .addQueryParameter("rpp", STORIES_PER_PAGE.toString())
                .addQueryParameter("all_media", INCLUDE_ALL_MEDIA.toString())
                .apply {
                    pageNumber?.let {
                        addQueryParameter("page", pageNumber.toString())
                    }
                }
                .build()

        return Request.Builder()
                .url(url)
                .headers(DEFAULT_HEADERS)
                .build()
    }

    companion object {

        private val FEED_URL = BuildConfig.STORYSTREAM_FEED_URL.toHttpUrl()
        private const val ACCESS_TOKEN = BuildConfig.STORYSTREAM_TOKEN
        private const val STORIES_PER_PAGE = 20
        private const val INCLUDE_ALL_MEDIA = true
        private val DEFAULT_HEADERS = headersOf(
                "Content-Type", "application/json; charset=utf-8"
        )
    }
}
