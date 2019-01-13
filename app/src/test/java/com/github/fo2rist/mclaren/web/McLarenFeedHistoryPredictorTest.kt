package com.github.fo2rist.mclaren.web

import com.github.fo2rist.mclaren.BuildConfig
import com.github.fo2rist.mclaren.web.utils.BadResponse
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.URL

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class McLarenFeedHistoryPredictorTest {

    private lateinit var predictor: McLarenFeedHistoryPredictor
    private lateinit var mockWebservice: McLarenFeedWebService

    @Before
    fun setUp() {
        mockWebservice = mock(McLarenFeedWebService::class.java)

        predictor = McLarenFeedHistoryPredictor(mockWebservice)
    }

    @Test
    fun `test Initial State is Unknown`() {
        assertEquals(McLarenFeedHistoryPredictor.UNKNOWN_PAGE, predictor.firstHistoryPage)
    }

    @Test
    fun `test Page Resolved when Responses Correct and Actual Page within Range`() {
        //a page between predicted one and last existing one
        val lastExistingPage = (predictor.guessClosestNotExistingPage() + McLarenFeedHistoryPredictor.LATEST_KNOWN_PAGE) / 2

        testPageDetectedProperly(lastExistingPage)
    }

    @Test
    fun `test After Page is Resolved startPrediction Doesnt Restart`() {
        setupMockServerToReturnExistingPage(McLarenFeedHistoryPredictor.LATEST_KNOWN_PAGE)

        predictor.startPrediction()
        reset(mockWebservice)
        predictor.startPrediction()

        assertTrue(predictor.isFirstHistoryPageKnown)
        verifyNoMoreInteractions(mockWebservice)
    }

    @Test
    fun `test Page Resolved when Responses Correct and Actual Page out of Range`() {
        val lastExistingPage = predictor.guessClosestNotExistingPage() + 100

        testPageDetectedProperly(lastExistingPage)
    }

    private fun testPageDetectedProperly(lastExistingPage: Int) = runBlocking {
        setupMockServerToReturnExistingPage(lastExistingPage)

        predictor.startPrediction()

        verify(mockWebservice, atLeastOnce()).requestFeedPage(anyInt())
        assertEquals(lastExistingPage, predictor.firstHistoryPage + 1)
        assertFalse(predictor.isActive)
    }

    private fun setupMockServerToReturnExistingPage(lastExistingPage: Int) = runBlocking<Unit> {
        whenever(mockWebservice.requestFeedPage(anyInt())).thenAnswer {
            val pageNumber = it.arguments[0] as Int
            if (pageNumber > lastExistingPage) {
                throw BadResponse(createUrlForPage(pageNumber), 404)
            } else {
                ""
            }
        }
    }

    @Test
    fun `test Page Ramains Unknown when Responses Fail`() = runBlocking<Unit> {
        setupMockServerToAlwaysFail()

        predictor.startPrediction()

        verify(mockWebservice, atLeastOnce()).requestFeedPage(anyInt())
        assertFalse(predictor.isActive)
    }

    private fun setupMockServerToAlwaysFail() = runBlocking<Unit> {
        whenever(mockWebservice.requestFeedPage(anyInt())).thenAnswer {
            val pageNumber = it.arguments[0] as Int
            throw BadResponse(createUrlForPage(pageNumber), 500)
        }
    }

    private fun createUrlForPage(page: Int): URL {
        return URL(BuildConfig.MCLAREN_FEED_URL + "?p=" + page)
    }
}
