package com.github.fo2rist.mclaren.pages

import com.agoda.kakao.KViewPager
import com.agoda.kakao.KWebView
import com.agoda.kakao.Screen
import com.github.fo2rist.mclaren.R

class PreviewPage : Screen<PreviewPage>() {

    val toolbarImages = KViewPager { withId(R.id.header_image_pager) }

    val webView = KWebView { withId(R.id.web_view) }

    val galleryView = KViewPager { withId(R.id.images_pager) }
}
