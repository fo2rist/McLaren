package com.github.fo2rist.mclaren.pages


import com.agoda.kakao.common.views.KView
import com.agoda.kakao.screen.Screen
import com.github.fo2rist.mclaren.R

class FeedPage : Screen<FeedPage>() {

    val feedList = KView { withId(R.id.feed_list) }
}
