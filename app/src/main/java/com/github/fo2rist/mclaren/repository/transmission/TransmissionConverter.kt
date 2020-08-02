package com.github.fo2rist.mclaren.repository.transmission

import com.github.fo2rist.mclaren.models.TransmissionInfo
import com.github.fo2rist.mclaren.models.TransmissionItem
import com.github.fo2rist.mclaren.web.models.TransmissionData
import com.github.fo2rist.mclaren.web.models.TransmissionItemData
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
                        name = it.toName(),
                        message = it.message,
                        session = session.toSessionModel(),
                        type = it.source.toMessageTypeModel())
            }
        }

        return TransmissionInfo(commentaries.sortedByDescending { it.id })
    }
}

private fun TransmissionItemData?.toName(): String? {
    return when (this?.source) {
        SAI_TO_PIT,
        PIT_TO_SAI -> "SAI"
        NOR_TO_PIT,
        PIT_TO_NOR -> "NOR"
        GUEST -> this.guestName
        else -> null
    }
}

@Suppress("ComplexMethod", "LongMethod")
private fun TransmissionMessageType?.toMessageTypeModel(): TransmissionItem.Type {
    return when (this) {
        SAI_TO_PIT ->
            TransmissionItem.Type.DRIVER_A_TO_PIT
        NOR_TO_PIT ->
            TransmissionItem.Type.DRIVER_B_TO_PIT
        PIT_TO_SAI ->
            TransmissionItem.Type.PIT_TO_DRIVER_A
        PIT_TO_NOR ->
            TransmissionItem.Type.PIT_TO_DRIVER_B
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
