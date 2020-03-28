package com.github.fo2rist.mclaren.ui.circuitsscreen

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.github.fo2rist.mclaren.ui.models.RaceCalendar

/**
 * Provides fragments for each race event in calendar by its position.
 */
class CircuitDetailsAdapter(
    fm: FragmentManager,
    private val calendar: RaceCalendar
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val event = calendar[position]
        return CircuitDetailsFragment.newInstance(CircuitDetailsFragment.createLaunchBundle(event))
    }

    override fun getCount(): Int {
        return calendar.size
    }
}
