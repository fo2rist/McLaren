package com.github.fo2rist.mclaren.repository

import com.github.fo2rist.mclaren.models.TransmissionItem
import com.github.fo2rist.mclaren.testdata.REAL_TRANSMISSION_RESPONSE
import com.github.fo2rist.mclaren.testdata.REAL_TRANSMISSION_RESPONSE_SIZE
import com.github.fo2rist.mclaren.web.SafeJsonParser
import com.github.fo2rist.mclaren.web.models.Transmission
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TransmissionConverterTest {
    private val converter = TransmissionConverter
    private lateinit var webDataModel: Transmission

    @Before
    fun setUp() {
        webDataModel = SafeJsonParser<Transmission>(Transmission::class.java).parse(REAL_TRANSMISSION_RESPONSE)
        assertNotNull(webDataModel)
        assertNotNull(webDataModel.commentaries)
        assertNotNull(webDataModel.name)
        assertEquals(REAL_TRANSMISSION_RESPONSE_SIZE, webDataModel.commentaries.size)
    }

    @Test
    fun testBasicConversion() {
        val transmissionModel = converter.convert(webDataModel)

        assertEquals(REAL_TRANSMISSION_RESPONSE_SIZE, transmissionModel.size)
        assertEquals(
                TransmissionItem(
                        26990,
                        DateTime.parse("2018-04-06T10:46:23+00:00"),
                        null,
                        "We’re live in the garage now here on TEAMStream. It’s 1345 in Bahrain and we’re 15 minutes away from the start of practice. You may not be shocked to hear it’s warm and sunny this afternoon.",
                        TransmissionItem.Session.UNKNOWN,
                        TransmissionItem.Type.MESSAGE_GENERAL),
                transmissionModel.get(0))

        assertEquals(
                TransmissionItem(
                        26995,
                        DateTime.parse("2018-04-06T11:01:03+00:00"),
                        null,
                        "Green light on, pitlane open, FP1 is underway. Flo-vis being applied to Fernando's sidepod and Stoffel's front wing.",
                        TransmissionItem.Session.PRACTICE,
                        TransmissionItem.Type.MESSAGE_GENERAL),
                transmissionModel.get(5))

        assertEquals(
                TransmissionItem(
                        27350,
                        DateTime.parse("2018-04-08T16:36:22+00:00"),
                        "Fernando",
                        "I need every sector.",
                        TransmissionItem.Session.RACE,
                        TransmissionItem.Type.DRIVER_A_TO_PIT),
                transmissionModel.get(61))
    }
}
