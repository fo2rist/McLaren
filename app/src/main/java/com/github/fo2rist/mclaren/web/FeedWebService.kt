package com.github.fo2rist.mclaren.web

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
