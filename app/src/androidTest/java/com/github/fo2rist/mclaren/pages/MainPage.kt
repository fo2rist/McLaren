package com.github.fo2rist.mclaren.pages


import com.agoda.kakao.KDrawerView
import com.agoda.kakao.KNavigationView
import com.agoda.kakao.KView
import com.agoda.kakao.Screen
import com.github.fo2rist.mclaren.R

class MainPage : Screen<MainPage>() {

    val menuStories: KView = KView { withText(R.string.navigation_item_stories) }

    val menuCircuits: KView = KView { withText(R.string.navigation_item_circuits) }

    val menuDrivers: KView = KView { withText(R.string.navigation_item_drivers) }

    val menuCar: KView = KView { withText(R.string.navigation_item_car) }

    val menuOfficialSite: KView = KView { withText(R.string.navigation_item_official_site) }

    val optionMenuAbout: KView = KView { withText(R.string.action_about) }

    fun openNavigationDrawer() = KDrawerView { withId(R.id.drawer_layout) }.open()

    fun navigateToMenuItem(menuItemId: Int) {
        openNavigationDrawer()
        onNavigationView.navigateTo(menuItemId)
    }

    private val onNavigationView = KNavigationView { withId(R.id.nav_view_main) }

    fun navigateToFooterMenuItem(menuItemId: Int) {
        openNavigationDrawer()
        onFooterNavigationView.navigateTo(menuItemId)
    }

    private val onFooterNavigationView = KNavigationView { withId(R.id.nav_view_footer) }
}
