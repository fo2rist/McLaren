package com.github.fo2rist.mclaren.ui.previewscreen

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.github.fo2rist.mclaren.R
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startVisibleFragment

open class BasePreviewActivityTest {

    protected fun startWithFragment(fragment: Fragment): FragmentActivity {
        startVisibleFragment(fragment, PreviewActivity::class.java, R.id.content_frame)

        return fragment.activity!!
    }
}
