package com.github.fo2rist.mclaren.pages

import androidx.annotation.StringRes
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.pager.KViewPager
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.github.fo2rist.mclaren.R

class CircuitDetailsPage : Screen<CircuitDetailsPage>() {
    private val viewPager = KViewPager { withId(R.id.circuits_pager) }

    fun swipeLeft() {
        viewPager.swipeLeft()
    }

    val title = KTextView {
        withId(R.id.circuit_title)
        withParent { isDisplayed() }
    }

    val circuitImage = KImageView {
        withId(R.id.circuit_image)
        withParent { isDisplayed() }
    }

    val circuitDetails = KTextView {
        withId(R.id.circuit_details)
        withParent { isDisplayed() }
    }

    val detailsItemLaps = detailsItem(R.string.circuit_details_laps)
    val detailsItemLength = detailsItem(R.string.circuit_details_length)
    val detailsItemDistance = detailsItem(R.string.circuit_details_distance)
    val detailsItemSeasons = detailsItem(R.string.circuit_details_seasons)
    val detailsItemGpHeld = detailsItem(R.string.circuit_details_gp_held)

    private fun detailsItem(@StringRes title: Int) = KTextView {
        withId(R.id.value)
        withSibling { withText(title) }
        isDescendantOfA {
            withId(R.id.circuit_details_root)
            isDisplayed()
        }
    }
}
