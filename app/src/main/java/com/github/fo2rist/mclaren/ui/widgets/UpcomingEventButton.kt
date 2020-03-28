package com.github.fo2rist.mclaren.ui.widgets

import android.content.Context
import android.util.AttributeSet
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.utils.formatRemainingTimeShort
import org.joda.time.DateTime
import org.joda.time.Period

/**
 * Button that shows time to the next event.
 * Use [setUpcomingEvent] instead of [setText].
 */
class UpcomingEventButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : android.support.v7.widget.AppCompatButton(context, attrs, defStyleAttr) {

    fun setUpcomingEvent(eventName: String, timeOfEvent: DateTime) {
        text = context.getString(
                R.string.format_race_in_time_units,
                eventName,
                formatRemainingTimeShort(context, Period(DateTime.now(), timeOfEvent)));
    }
}
