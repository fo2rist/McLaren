package com.github.fo2rist.mclaren.web.feed

import com.github.fo2rist.mclaren.web.utils.BadResponse
import java.net.ConnectException

/** Special page number to request the latest page via [FeedWebService]. */
const val DEFAULT_PAGE = -1

/**
 * Base interface for any feed provider web-service.
 */
interface FeedWebService {

    /**
     * Request latest posts.
     * @throws ConnectException if networking error occurs
     * @throws BadResponse if server responds with 4xx-5xx code
     */
    @Throws(BadResponse::class, ConnectException::class)
    suspend fun requestLatestFeed(): String?

    /** Request specific page from history, which may be latest.  */
    suspend fun requestFeedPage(pageNumber: Int): String?
}

/**
 * Webservice for McLaren feed API.
 * Note that this service uses non-standard pagination: each page should be addressed by the absolute number
 * Where 1 - is the oldest page.
 * Marker interface for McLaren API feed implementation.
 */
interface McLarenFeedWebService : FeedWebService

/**
 * Webservice for story-stream API.
 * Marker interface for story-stream feed implementation.
 */
interface StoryStreamWebService : FeedWebService
