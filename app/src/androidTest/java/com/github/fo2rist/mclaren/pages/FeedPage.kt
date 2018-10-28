package com.github.fo2rist.mclaren.pages


import com.agoda.kakao.KView
import com.agoda.kakao.Screen
import com.github.fo2rist.mclaren.R

class FeedPage : Screen<FeedPage>() {

    val feedList = KView { withId(R.id.feed_list) }
}
