package com.github.fo2rist.mclaren.ui.circuitsscreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.github.fo2rist.mclaren.ui.models.RaceCalendar

/**
 * Provides fragments for each race event in calendar by its position.
 */
class CircuitDetailsAdapter(
    fm: FragmentManager,
    private val calendar: RaceCalendar
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        val event = calendar[position]
        return CircuitDetailsFragment.newInstance(event)
    }

    override fun getCount(): Int {
        return calendar.size
    }
}
