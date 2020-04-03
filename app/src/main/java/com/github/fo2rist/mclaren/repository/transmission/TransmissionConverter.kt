package com.github.fo2rist.mclaren.repository.transmission

import com.github.fo2rist.mclaren.models.TransmissionInfo
import com.github.fo2rist.mclaren.models.TransmissionItem
import com.github.fo2rist.mclaren.web.models.Transmission
import com.github.fo2rist.mclaren.web.models.TransmissionMessageType
import com.github.fo2rist.mclaren.web.models.TransmissionSession
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

object TransmissionConverter {

    @JvmStatic
    fun convert(webDataModel: Transmission): TransmissionInfo {
        //commentaries - latest first
        val commentaries = webDataModel.commentaries.map {
            TransmissionItem(
                    id = it.id,
                    dateTime = DateTime(it.date, DateTimeZone.UTC),
                    driverName = it.driverName,
                    message = it.message,
                    session = it.session.toSessionModel(),
                    type = it.type.toMessageTypeModel())
        }.reversed()
        return TransmissionInfo(webDataModel.id, webDataModel.name, commentaries)
    }
}

@Suppress("ComplexMethod", "LongMethod")
private fun TransmissionMessageType?.toMessageTypeModel(): TransmissionItem.Type {
    return when(this) {
        TransmissionMessageType.ATP ->
            TransmissionItem.Type.DRIVER_A_TO_PIT
        TransmissionMessageType.BTP ->
            TransmissionItem.Type.DRIVER_B_TO_PIT
        TransmissionMessageType.PTA ->
            TransmissionItem.Type.PIT_TO_DRIVER_A
        TransmissionMessageType.PTB ->
            TransmissionItem.Type.PIT_TO_DRIVER_B
        TransmissionMessageType.PIT ->
            TransmissionItem.Type.PIT_TO_EVERYONE
        TransmissionMessageType.CAA ->
            TransmissionItem.Type.MESSAGE_ABOUT_DRIVER_A
        TransmissionMessageType.CAB ->
            TransmissionItem.Type.MESSAGE_ABOUT_DRIVER_B
        TransmissionMessageType.ALY,
        TransmissionMessageType.COM ->
            TransmissionItem.Type.MESSAGE_GENERAL
        else ->
            TransmissionItem.Type.MESSAGE_GENERAL
    }
}

private fun TransmissionSession?.toSessionModel(): TransmissionItem.Session {
    return when(this) {
        TransmissionSession.PRACTICE -> TransmissionItem.Session.PRACTICE
        TransmissionSession.QUALIFICATION -> TransmissionItem.Session.QUALIFICATION
        TransmissionSession.RACE -> TransmissionItem.Session.RACE
        else -> TransmissionItem.Session.UNKNOWN
    }
}
