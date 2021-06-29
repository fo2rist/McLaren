package com.github.fo2rist.mclaren.repository.converters.utils

import com.github.fo2rist.mclaren.models.ImageUrl.DYNAMIC_HEIGHT_PLACEHOLDER
import com.github.fo2rist.mclaren.models.ImageUrl.DYNAMIC_WIDTH_PLACEHOLDER
import com.github.fo2rist.mclaren.repository.converters.utils.ImageUrlParser.CDN_API_HOST
import com.github.fo2rist.mclaren.repository.converters.utils.ImageUrlParser.CDN_API_PATH
import com.github.fo2rist.mclaren.repository.converters.utils.ImageUrlParser.TAB_API_HOST
import com.github.fo2rist.mclaren.repository.converters.utils.ImageUrlParser.TAB_API_PATH
import com.github.fo2rist.mclaren.testdata.IMAGE_URL_TAB_API_WITH_PLACEHOLDERS
import com.github.fo2rist.mclaren.testdata.IMAGE_URL_TAB_API_WITH_PLACEHOLDERS_0
import com.github.fo2rist.mclaren.testdata.IMAGE_URL_TAB_API_WITH_PLACEHOLDERS_INCOMPLETE
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsString
import org.junit.Assert.assertEquals
import org.junit.Test

class ImageUrlParserTest {

    @Test
    fun `test null url interpreted as empty`() {
        assertEquals("", ImageUrlParser.convertToInternalUrl(null));
    }

    @Test
    fun `test plain url not modified`() {
        val validUrl = "http://some_valid.url/image"

        assertEquals(validUrl, ImageUrlParser.convertToInternalUrl(validUrl));
    }

    @Test
    fun `test tab-api url with placeholders converted to internal format`() {
        val convertedUrl = ImageUrlParser.convertToInternalUrl(IMAGE_URL_TAB_API_WITH_PLACEHOLDERS)

        assertPlaceholderUrlWithPath(convertedUrl, TAB_API_HOST, TAB_API_PATH)
    }

    @Test
    fun `test CDN url with placeholders converted to internal format`() {
        val convertedUrl = ImageUrlParser.convertToInternalUrl(IMAGE_URL_TAB_API_WITH_PLACEHOLDERS_0)

        assertPlaceholderUrlWithPath(convertedUrl, CDN_API_HOST, CDN_API_PATH)
    }

    @Test
    fun `test tab-api path converted to full URL`() {
        val convertedUrl = ImageUrlParser.convertToInternalUrl(IMAGE_URL_TAB_API_WITH_PLACEHOLDERS_INCOMPLETE)

        assertPlaceholderUrlWithPath(convertedUrl, TAB_API_HOST, TAB_API_PATH)
    }

    private fun assertPlaceholderUrlWithPath(convertedUrl: String?, host: String, path: String) {
        assertThat(convertedUrl, containsString(host))
        assertThat(convertedUrl, containsString(path))
        assertThat(convertedUrl, containsString(DYNAMIC_WIDTH_PLACEHOLDER))
        assertThat(convertedUrl, containsString(DYNAMIC_HEIGHT_PLACEHOLDER))
    }
}
