package com.github.fo2rist.mclaren.web.feed

import android.support.annotation.VisibleForTesting
import com.github.fo2rist.mclaren.web.utils.BadResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.joda.time.Days
import org.joda.time.LocalDate
import java.io.IOException
import java.net.ConnectException
import javax.inject.Inject

/**
 * Predictor's page status.
 * Used to distinguish not checked pages and non-existing pages for bisection.
 */
private class PageStatus internal constructor(
    internal val pageNumber: Int,
    internal val exists: Boolean
)

/**
 * Predictor for page number which should be next after the latest posts.
 * Hack for McLaren web service. App have to guess the page number
 */
class McLarenFeedHistoryPredictor @Inject internal constructor(
    webService: McLarenFeedWebService
) : FeedHistoryPredictor {

    private val mainScope = CoroutineScope(Dispatchers.Main)

    // initially set it to the page way in the future (we assume)
    @Suppress("MagicNumber")
    private var newestCheckedPage = PageStatus(LATEST_KNOWN_PAGE * 100, false)
    private var oldestCheckedPage = PageStatus(LATEST_KNOWN_PAGE, true)

    // First page that contains items older than latest feed page.
    private var firstHistoryPage = UNKNOWN_PAGE

    @get:VisibleForTesting
    internal var isActive = false
        private set

    private val webService: FeedWebService

    init {
        this.webService = webService
    }

    /**
     * @return page number if already known or [.UNKNOWN_PAGE].
     */
    override fun getFirstHistoryPage(): Int {
        return firstHistoryPage
    }

    /**
     * Start prediction process in the background.
     * At the end if server response value provided by [.getFirstHistoryPage] should be correct.
     */
    override fun startPrediction() {
        if (isFirstHistoryPageKnown) {
            return
        }
        if (isActive) { //already in progress
            //TODO this behavior is not tested, need to figure out how to simulate multiple requests from parallel
            // threads. 2019.01.06
            return
        }
        isActive = true

        //once we define a starting point to lookup we just wait for next answer and either shift top or bottom line
        //of possibilities window
        requestPage(guessClosestNotExistingPage())
    }

    override fun isFirstHistoryPageKnown(): Boolean {
        return firstHistoryPage != UNKNOWN_PAGE
    }

    @VisibleForTesting
    internal fun guessClosestNotExistingPage(): Int {
        val daysBetween = Days.daysBetween(LATEST_KNOWN_DATE, LocalDate.now()).days

        return LATEST_KNOWN_PAGE + daysBetween / APPROXIMATED_DAYS_PER_PAGE + APPROXIMATED_EXTRA_PAGES_BUFFER
    }

    private fun requestPage(nextPageToAsk: Int) {
        mainScope.launch {
            try {
                webService.requestFeedPage(nextPageToAsk)
                onSuccess(nextPageToAsk)
            } catch (exc: BadResponse) {
                onFailure(nextPageToAsk, exc.responseCode, null)
            } catch (exc: ConnectException) {
                onFailure(nextPageToAsk, 0, exc)
            }
        }
    }

    private fun onSuccess(requestedPage: Int) {
        recordHit(requestedPage)
        analyzeUpdatedState()
    }

    @SuppressWarnings("MagicNumber")
    private fun onFailure(requestedPage: Int, responseCode: Int, connectionError: IOException?) {
        if (connectionError != null || responseCode >= 500) {
            //give up on precessing if server gives not valuable response
            isActive = false
            return
        }
        recordMiss(requestedPage)
        analyzeUpdatedState()
    }

    private fun recordHit(pageNumber: Int) {
        oldestCheckedPage = PageStatus(pageNumber, true)
    }

    private fun recordMiss(pageNumber: Int) {
        newestCheckedPage = PageStatus(pageNumber, false)
    }

    private fun analyzeUpdatedState() {
        if (isFirstPageDetected()) {
            //first page in history is 1 page before the latest one
            firstHistoryPage = oldestCheckedPage.pageNumber - 1
            isActive = false
        } else {
            //otherwise, bisect
            requestPage((oldestCheckedPage.pageNumber + newestCheckedPage.pageNumber + 1) / 2)
        }
    }

    private fun isFirstPageDetected(): Boolean {
        return newestCheckedPage.pageNumber - oldestCheckedPage.pageNumber == 1
                && !newestCheckedPage.exists
                && oldestCheckedPage.exists
    }


    companion object {

        // As of 2017.09.24 it was 454
        // As of 2018.02.07 it was 504
        // As of 2019.01.21 it was 615
        @VisibleForTesting
        const val LATEST_KNOWN_PAGE = 615

        @JvmStatic
        private val LATEST_KNOWN_DATE = LocalDate(2019, 1, 21)

        private const val APPROXIMATED_DAYS_PER_PAGE = 3
        private const val APPROXIMATED_EXTRA_PAGES_BUFFER = 10

        const val UNKNOWN_PAGE = -1
    }
}
