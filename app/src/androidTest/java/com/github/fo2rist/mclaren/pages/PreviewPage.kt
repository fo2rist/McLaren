package com.github.fo2rist.mclaren.pages

import com.agoda.kakao.pager.KViewPager
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.web.KWebView
import com.github.fo2rist.mclaren.R

class PreviewPage : Screen<PreviewPage>() {

    val toolbarImages = KViewPager { withId(R.id.header_image_pager) }

    val webView = KWebView { withId(R.id.web_view) }

    val galleryView = KViewPager { withId(R.id.images_pager) }
}
