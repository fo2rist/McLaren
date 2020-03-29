package com.github.fo2rist.mclaren.ui.previewscreen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.ImageUrl
import com.github.fo2rist.mclaren.mvp.PreviewContract
import com.github.fo2rist.mclaren.ui.models.Orientation
import com.github.fo2rist.mclaren.ui.models.PreviewContent
import com.github.fo2rist.mclaren.ui.previewscreen.ImageGalleryAdapter.ImageViewType
import com.github.fo2rist.mclaren.ui.tools.McLarenImageDownloader.ImageSizeLimit
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import me.relex.circleindicator.CircleIndicator
import javax.inject.Inject

/**
 * Displays content of item, web-page, text.
 */
class PreviewActivity : AppCompatActivity(), PreviewContract.View, HasAndroidInjector {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var presenter: PreviewContract.Presenter

    private lateinit var actionBar: ActionBar
    private lateinit var toolBarImages: ViewPager
    private lateinit var dotIndicator: CircleIndicator

    private val deviceOrientation: Orientation
        get() {
            return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Orientation.LANDSCAPE
            } else {
                Orientation.PORTRAIT
            }
        }

    override fun androidInjector(): AndroidInjector<Any> {
        return fragmentInjector
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        bindViews()
        setupToolBar()

        if (intent.hasExtra(KEY_URL)) {
            val url = intent.getStringExtra(KEY_URL)
            presenter.onStartWith(url!!)
        } else if (intent.hasExtra(KEY_FEED_ITEM)) {
            val feedItem = intent.getSerializableExtra(KEY_FEED_ITEM) as FeedItem
            presenter.onStartWith(deviceOrientation, feedItem)
        } else {
            finish()
        }
    }

    private fun bindViews() {
        this.setSupportActionBar(findViewById(R.id.toolbar))
        actionBar = requireNotNull(supportActionBar)

        toolBarImages = findViewById(R.id.header_image_pager)
        dotIndicator = findViewById(R.id.dot_indicator)
    }

    private fun setupToolBar() {
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setTitle(R.string.app_name)
    }

    override fun displayFragment(content: PreviewContent) {
        //try to retain fragment first to not create a new one when not necessary
        //in addition to that a retained fragment may have its own state
        val fragment = supportFragmentManager.findFragmentByTag(CONTENT_FRAGMENT_TAG)
                ?: createFragmentForContent(content)
        startFragment(fragment)
    }

    private fun createFragmentForContent(content: PreviewContent): Fragment {
        return when (content) {
            is PreviewContent.Url -> WebPreviewFragment.newInstanceForUrl(content.url)
            is PreviewContent.Html -> WebPreviewFragment.newInstanceForMcLarenHtml(content.html)
            is PreviewContent.FeedItem -> ImagePreviewFragment.newInstanceForFeedItem(content.feedItem)
        }
    }

    override fun setTitle(text: String) {
        actionBar.title = text
    }

    override fun setToolBarImage(imageUrls: List<ImageUrl>) {
        if (imageUrls.isEmpty()) {
            toolBarImages.visibility = View.GONE
        } else {
            toolBarImages.visibility = View.VISIBLE
            toolBarImages.adapter = ImageGalleryAdapter(this, imageUrls, ImageSizeLimit.TILE, ImageViewType.STATIC)
            //display dots if there is more than one item
            if (imageUrls.size > 1) {
                dotIndicator.setViewPager(toolBarImages)
            }
        }
    }

    override fun lockToolBar() {
        val collapsingToolBar = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        val params = collapsingToolBar.layoutParams as AppBarLayout.LayoutParams
        params.scrollFlags = 0  // clear all scroll flags
    }

    override fun hideToolBar() {
        val collapsingToolBar = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
        collapsingToolBar.visibility = View.GONE
    }

    override fun enterFullScreen() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE
    }

    private fun startFragment(previewFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, previewFragment, CONTENT_FRAGMENT_TAG)
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish() //TODO this better be delegated to presenter. 2019-03-03
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    companion object {

        private const val CONTENT_FRAGMENT_TAG = "content_fragment"
        private const val KEY_URL = "url"
        private const val KEY_FEED_ITEM = "feed_item"

        @JvmStatic
        fun createUrlIntent(context: Context, url: String): Intent {
            val intent = Intent(context, PreviewActivity::class.java)
            intent.putExtra(KEY_URL, url)
            return intent
        }

        @JvmStatic
        fun createFeedItemIntent(context: Context, item: FeedItem): Intent {
            val intent = Intent(context, PreviewActivity::class.java)
            intent.putExtra(KEY_FEED_ITEM, item)
            return intent
        }
    }
}
