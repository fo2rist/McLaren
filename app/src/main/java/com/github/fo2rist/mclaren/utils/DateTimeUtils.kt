@file:JvmName("DateTimeUtils")
package com.github.fo2rist.mclaren.utils

import android.content.Context
import com.github.fo2rist.mclaren.R
import org.joda.time.DateTime
import java.util.Locale


fun formatShort(context: Context, startDate: DateTime): String {
    return startDate.toString(context.getString(R.string.calendar_event_date_format), Locale.US)
}

