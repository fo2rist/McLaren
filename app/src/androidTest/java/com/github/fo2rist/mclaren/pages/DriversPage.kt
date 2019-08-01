package com.github.fo2rist.mclaren.pages


import com.agoda.kakao.common.views.KView
import com.agoda.kakao.pager.KViewPager
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.ui.widgets.InformationLineView

class DriversPage : Screen<DriversPage>() {

    private val viewPager = KViewPager { withId(R.id.drivers_pager) }

    fun swipeLeft() {
        viewPager.swipeLeft()
    }

    val driverNumber = KTextView {
        withParent { isDisplayed() }
        withId(R.id.driver_number_text)
    }

    val driverPageLink = KView {
        withParent { isDisplayed() }
        withId(R.id.team_link_button)
    }

    fun infoItemWithTitle(title: String) = KTextView {
        withId(R.id.value)
        withParent {
            isInstanceOf(InformationLineView::class.java)
            withParent { isDisplayed() }
        }
        withSibling {
            withId(R.id.title)
            withText(title)
        }
    }
}
