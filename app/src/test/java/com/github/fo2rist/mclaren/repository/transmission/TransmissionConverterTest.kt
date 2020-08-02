package com.github.fo2rist.mclaren.repository.transmission

import com.github.fo2rist.mclaren.models.TransmissionItem
import com.github.fo2rist.mclaren.testdata.REAL_TRANSMISSION_RESPONSE
import com.github.fo2rist.mclaren.testdata.REAL_TRANSMISSION_RESPONSE_SIZE
import com.github.fo2rist.mclaren.web.utils.SafeJsonParser
import com.github.fo2rist.mclaren.web.models.TransmissionData
import com.github.fo2rist.mclaren.web.models.TransmissionSession
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
    private lateinit var webDataModel: TransmissionData

    @Before
    fun setUp() {
        webDataModel = SafeJsonParser(TransmissionData::class.java).parse(REAL_TRANSMISSION_RESPONSE)
        assertNotNull(webDataModel)
        assertNotNull(webDataModel[TransmissionSession.RACE])
        assertNotNull(webDataModel[TransmissionSession.QUALIFICATION])
        assertEquals(REAL_TRANSMISSION_RESPONSE_SIZE, webDataModel.flatMap { it.value }.size)
    }

    @Test
    fun testBasicConversion() {
        val transmissionModel = converter.convert(webDataModel)

        assertEquals(REAL_TRANSMISSION_RESPONSE_SIZE, transmissionModel.messages.size)
        assertEquals(
                TransmissionItem(
                        1595170265000,
                        DateTime.parse("2020-07-19T14:51:05+00:00"),
                        "",
                        "No worries dude. ",
                        TransmissionItem.Session.RACE,
                        TransmissionItem.Type.PIT_TO_NORRIS),
                transmissionModel.messages[1])

        assertEquals(
                TransmissionItem(
                        1594996531000,
                        DateTime.parse("2020-07-17T14:35:31+00:00"),
                        "",
                        "And that's it from us today. Not the most stimulating of afternoon sessions, but plenty more to come tomorrow. Stick with TEAMStream this afternoon. The drivers will be along shortly with their assessment of conditions on this first day in Hungary. We'll be back with live commentary tomorrow at 1145 CEST / 1045 BST , 15min before the start of FP3.",
                        TransmissionItem.Session.PRACTICE_2,
                        TransmissionItem.Type.MESSAGE_GENERAL),
                transmissionModel.messages[15])

        assertEquals(
                TransmissionItem(
                        1594978591000,
                        DateTime.parse("2020-07-17T09:36:31+00:00"),
                        "TheFifthDriver",
                        "Lando completes his run, he has a practice pitstop in the box an is pulled back into his stall. That's not entirely straightforward at the Hungaroring. It's a tight pitlane, and the garages have unhelpful pillars. It's even more difficult for the drivers leaving the garage: at the start of the weekend they always think they're going to hit the wall",
                        TransmissionItem.Session.PRACTICE_1,
                        TransmissionItem.Type.MESSAGE_FROM_GUEST),
                transmissionModel.messages[32])
    }
}
