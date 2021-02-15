package com.github.fo2rist.mclaren.pages


import com.agoda.kakao.common.views.KView
import com.agoda.kakao.drawer.KDrawerView
import com.agoda.kakao.navigation.KNavigationView
import com.agoda.kakao.screen.Screen
import com.github.fo2rist.mclaren.R

class MainPage : Screen<MainPage>() {

    val menuStories: KView = KView { withText(R.string.navigation_item_stories) }

    val menuCircuits: KView = KView { withText(R.string.navigation_item_season_calendar) }

    val menuLandoTwitter: KView = KView { withText(R.string.navigation_item_lando_twitter) }

    val menuDanielTwitter: KView = KView { withText(R.string.navigation_item_daniel_twitter) }

    val menuTeamTwitter: KView = KView { withText(R.string.navigation_item_team_twitter) }

    val menuDriver1Twitter: KView = KView { withText(R.string.navigation_item_lando_twitter) }

    val menuDriver2Twitter: KView = KView { withText(R.string.navigation_item_daniel_twitter) }

    val menuDrivers: KView = KView { withText(R.string.navigation_item_drivers) }

    val menuCar: KView = KView { withText(R.string.navigation_item_car) }

    val menuOfficialSite: KView = KView { withText(R.string.navigation_item_official_site) }

    val optionMenuAbout: KView = KView { withText(R.string.action_about) }

    val transmissionButton: KView = KView { withId(R.id.floating_button_transmission) }

    val upcomingEventButton: KView = KView { withId(R.id.floating_button_upcoming_event) }

    fun openNavigationDrawer() = KDrawerView { withId(R.id.drawer_layout) }.open()

    fun navigateToMenuItem(menuItemId: Int) {
        openNavigationDrawer()
        navigationView.navigateTo(menuItemId)
    }

    private val navigationView = KNavigationView { withId(R.id.nav_view_main) }

    fun navigateToFooterMenuItem(menuItemId: Int) {
        openNavigationDrawer()
        footerNavigationView.navigateTo(menuItemId)
    }

    private val footerNavigationView = KNavigationView { withId(R.id.nav_view_footer) }
}
