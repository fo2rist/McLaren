@file:JvmName("DateTimeUtils")
package com.github.fo2rist.mclaren.utils

import android.content.Context
import com.github.fo2rist.mclaren.R
import org.joda.time.DateTime
import org.joda.time.Period
import org.joda.time.PeriodType
import java.util.Locale


fun formatShort(context: Context, startDate: DateTime): String {
    return startDate.toString(context.getString(R.string.calendar_event_date_format), Locale.US)
}

/**
 * Formats [remainingTime] as "10D" or "2H" etc.
 * Only shows major unit so "1D and 2H" period is formatted as "1D".
 * Doesn't support years.
 */
fun formatRemainingTimeShort(context: Context, remainingTime: Period): String {
    return when {
        remainingTime.months > 0 ->
            context.getString(R.string.time_unit_month_format, remainingTime.months)
        remainingTime.weeks > 0 ->
            context.getString(R.string.time_unit_week_format, remainingTime.weeks)
        remainingTime.days > 0 ->
            context.getString(R.string.time_unit_day_format, remainingTime.days)
        remainingTime.hours > 0 ->
            context.getString(R.string.time_unit_hour_format, remainingTime.hours)
        remainingTime.minutes > 0 ->
            context.getString(R.string.time_unit_minute_format, remainingTime.minutes)
        else ->
            context.getString(R.string.time_unit_seconds)
    }
}

