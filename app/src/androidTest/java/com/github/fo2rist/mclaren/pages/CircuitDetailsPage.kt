package com.github.fo2rist.mclaren.pages

import android.support.annotation.StringRes
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.github.fo2rist.mclaren.R

class CircuitDetailsPage : Screen<CircuitDetailsPage>() {
    val title = KTextView { withId(R.id.circuit_title) }

    val circuitImage = KImageView{ withId(R.id.circuit_image) }

    val circuitDetails = KTextView { withId(R.id.circuit_details)}

    val detailsItemLaps = detailsItem(R.string.circuit_details_laps)
    val detailsItemLength = detailsItem(R.string.circuit_details_length)
    val detailsItemDistance = detailsItem(R.string.circuit_details_distance)
    val detailsItemSeasons = detailsItem(R.string.circuit_details_seasons)
    val detailsItemGpHeld = detailsItem(R.string.circuit_details_gp_held)

    private fun detailsItem(@StringRes title: Int) = KTextView {
        withId(R.id.value)
        withSibling { withText(title) }
    }
}
