package com.github.fo2rist.mclaren.repository.transmission

import com.github.fo2rist.mclaren.models.TransmissionInfo
import com.github.fo2rist.mclaren.models.TransmissionItem
import com.github.fo2rist.mclaren.web.models.TransmissionData
import com.github.fo2rist.mclaren.web.models.TransmissionMessageType
import com.github.fo2rist.mclaren.web.models.TransmissionMessageType.*
import com.github.fo2rist.mclaren.web.models.TransmissionSession
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

object TransmissionConverter {

    @JvmStatic
    fun convert(webDataModel: TransmissionData): TransmissionInfo {
        val commentaries = webDataModel.flatMap { (session, transmissionFeed) ->
            transmissionFeed.map {
                TransmissionItem(
                        id = it.date.time,
                        dateTime = DateTime(it.date, DateTimeZone.UTC),
                        guestName = it.guestName,
                        message = it.message,
                        session = session.toSessionModel(),
                        type = it.source.toMessageTypeModel())
            }
        }

        return TransmissionInfo(commentaries.sortedByDescending { it.id })
    }
}

@Suppress("ComplexMethod", "LongMethod")
private fun TransmissionMessageType?.toMessageTypeModel(): TransmissionItem.Type {
    return when (this) {
        RIC_TO_PIT ->
            TransmissionItem.Type.RICCIARDO_TO_PIT
        NOR_TO_PIT ->
            TransmissionItem.Type.NORRIS_TO_PIT
        PIT_TO_RIC ->
            TransmissionItem.Type.PIT_TO_RICCIARDO
        PIT_TO_NOR ->
            TransmissionItem.Type.PIT_TO_NORRIS
        GUEST ->
            TransmissionItem.Type.MESSAGE_FROM_GUEST
        COMMENTARY ->
            TransmissionItem.Type.MESSAGE_GENERAL
        else ->
            TransmissionItem.Type.MESSAGE_GENERAL
    }
}

private fun TransmissionSession?.toSessionModel(): TransmissionItem.Session {
    return when (this) {
        TransmissionSession.PRACTICE_1 -> TransmissionItem.Session.PRACTICE_1
        TransmissionSession.PRACTICE_2 -> TransmissionItem.Session.PRACTICE_2
        TransmissionSession.PRACTICE_3 -> TransmissionItem.Session.PRACTICE_3
        TransmissionSession.QUALIFICATION -> TransmissionItem.Session.QUALIFICATION
        TransmissionSession.RACE -> TransmissionItem.Session.RACE
        else -> TransmissionItem.Session.UNKNOWN
    }
}
