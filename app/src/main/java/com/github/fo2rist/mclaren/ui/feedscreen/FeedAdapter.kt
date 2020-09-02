package com.github.fo2rist.mclaren.ui.feedscreen

import android.content.Context
import android.text.Html
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.models.FeedItem
import com.github.fo2rist.mclaren.models.FeedItem.SourceType
import com.github.fo2rist.mclaren.models.ImageUrl
import com.github.fo2rist.mclaren.models.Size
import com.github.fo2rist.mclaren.mvp.FeedContract.OnFeedInteractionListener
import com.github.fo2rist.mclaren.mvp.FeedContract.OnFeedScrollingListener
import com.github.fo2rist.mclaren.ui.feedscreen.FeedAdapter.FeedViewHolder
import com.github.fo2rist.mclaren.ui.tools.McLarenImageDownloader
import com.github.fo2rist.mclaren.ui.tools.McLarenImageDownloader.ImageSizeLimit
import com.github.fo2rist.mclaren.utils.LinkUtils
import com.github.fo2rist.mclaren.utils.exhaustive
import io.github.armcha.autolink.AutoLinkItem
import io.github.armcha.autolink.AutoLinkTextView
import io.github.armcha.autolink.MODE_CUSTOM
import io.github.armcha.autolink.MODE_HASHTAG
import io.github.armcha.autolink.MODE_MENTION
import io.github.armcha.autolink.MODE_URL
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.ArrayList

/**
 * Adapter for main page feed of news.
 */
class FeedAdapter internal constructor(
    private val context: Context,
    interactionListener: OnFeedInteractionListener,
    scrollingListener: OnFeedScrollingListener
) : RecyclerView.Adapter<FeedViewHolder>() {

    @Suppress("UndocumentedPublicClass")
    inner class FeedViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView), View.OnClickListener {

        private lateinit var rootView: View
        private lateinit var textViewDate: TextView
        private lateinit var textViewTime: TextView
        private lateinit var textViewContent: AutoLinkTextView
        private lateinit var textViewSource: TextView
        private lateinit var imageSwitcher: ImageSwitcher
        private lateinit var image: ImageView
        private lateinit var imageItemType: ImageView
        private lateinit var imageSource: ImageView
        private lateinit var playIcon: View
        private lateinit var containerSource: View

        private var currentItem: FeedItem? = null
        private var currentGalleryIndex = 0

        init {
            bindViews(rootView)
            setupAutoLinkTextView(textViewContent)
            setupViewListeners()
        }

        private fun bindViews(rootView: View) {
            this.rootView = rootView

            textViewDate = rootView.findViewById(R.id.text_date)
            textViewTime = rootView.findViewById(R.id.text_time)
            textViewContent = rootView.findViewById(R.id.text_content)
            textViewSource = rootView.findViewById(R.id.text_source)
            containerSource = rootView.findViewById(R.id.container_source)
            imageSwitcher = rootView.findViewById(R.id.image_switcher)
            image = rootView.findViewById(R.id.image)
            imageItemType = rootView.findViewById(R.id.image_type)
            imageSource = rootView.findViewById(R.id.image_source)
            playIcon = rootView.findViewById(R.id.play_icon)
        }

        private fun setupAutoLinkTextView(textView: AutoLinkTextView) {
            textView.addAutoLinkMode(
                    // simplistic regex for <a href links with quotes or double quotes around link and any text
                    MODE_CUSTOM("""<a\s+(?:[^>]*?\s+)?href=(["'])(.*?)\1.*>(.*)<\s*\/a\s*>"""),
                    MODE_HASHTAG,
                    MODE_MENTION,
                    MODE_URL,
                    // regex for incomplete pseudo URL "words/more-words" that appeared in stream response
                    MODE_CUSTOM("""\b(\S+\.)+\w+\/\S*[^.…](\s|$)"""))
            textView.mentionModeColor = ContextCompat.getColor(context, R.color.textSecondaryBlack)
            textView.hashTagModeColor = ContextCompat.getColor(context, R.color.textSecondaryBlack)
            textView.urlModeColor = ContextCompat.getColor(context, R.color.colorAccent)
            textView.customModeColor = ContextCompat.getColor(context, R.color.colorAccent)
            // that only works with URL type, not with custom
            // possible options:
            // - fix the library to preserve original spans
            // - swap URL text to actual URLs, store original text, then use processor to restore
            textView.attachUrlProcessor { originalUrl ->
                originalUrl
            }
        }

        private fun setupViewListeners() {
            imageSwitcher.setOnClickListener(this)
            containerSource.setOnClickListener(this)
            textViewContent.onAutoLinkClick(this::onAutoLinkTextClick)
            textViewContent.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            when (view.id) {
                R.id.image_switcher, R.id.text_content -> notifyItemClicked(currentItem!!)
                R.id.container_source -> notifyItemSourceClicked(currentItem!!)
                else -> throw IllegalArgumentException("Unknown view clicked. Id: " + view.id)
            }
        }

        private fun onAutoLinkTextClick(autoLinkItem: AutoLinkItem) {
            val link = when (autoLinkItem.mode) {
                MODE_HASHTAG -> LinkUtils.getFeedHashtagLink(currentItem, autoLinkItem.originalText)

                MODE_MENTION -> LinkUtils.getFeedMentionLink(currentItem, autoLinkItem.originalText)

                MODE_URL,
                is MODE_CUSTOM -> autoLinkItem.originalText

                else -> {
                    Timber.w("Clicked unknown link: ${autoLinkItem.originalText} in mode: ${autoLinkItem.mode}")
                    null
                }
            }

            if (!link.isNullOrEmpty()) {
                notifyLinkClicked(currentItem!!, link)
            }
        }

        fun display(context: Context, feedItem: FeedItem) {
            setCurrentItem(feedItem)

            displayDateTime(context, feedItem)
            displayImage(feedItem)
            displayPlayIcon(feedItem)
            displayDataTypeIcon(feedItem)
            displayText(feedItem)
            displaySource(feedItem)
        }

        private fun setCurrentItem(item: FeedItem) {
            currentItem = item
            currentGalleryIndex = 0
        }

        private fun displayDateTime(context: Context, feedItem: FeedItem) {
            textViewDate.text = DateFormat.getDateFormat(context).format(feedItem.dateTime)
            textViewTime.text = DateFormat.getTimeFormat(context).format(feedItem.dateTime)
        }

        private fun displayImage(feedItem: FeedItem) {
            when (feedItem.type) {
                FeedItem.Type.Message ->
                    hideImage()
                FeedItem.Type.Gallery,
                FeedItem.Type.Image,
                FeedItem.Type.Video,
                FeedItem.Type.Article ->
                    displayImage(currentImageUri(), currentItem!!.text)
            }.exhaustive
        }

        private fun displayImage(imageUri: ImageUrl?, contentDescription: String?) {
            if (imageUri == null) {
                hideImage()
                return
            }
            showImage(imageUri.size)
            setImageContent(imageUri, contentDescription)
        }

        private fun displayPlayIcon(feedItem: FeedItem) {
            if (feedItem.type === FeedItem.Type.Video) {
                playIcon.visibility = View.VISIBLE
            } else {
                playIcon.visibility = View.GONE
            }
        }

        private fun displayDataTypeIcon(feedItem: FeedItem) {
            when (feedItem.type) {
                FeedItem.Type.Gallery -> imageItemType.setImageResource(R.drawable.ic_gallery)
                FeedItem.Type.Image -> imageItemType.setImageResource(R.drawable.ic_photo)
                FeedItem.Type.Video -> imageItemType.setImageResource(R.drawable.ic_video)
                FeedItem.Type.Message -> imageItemType.setImageResource(R.drawable.ic_text)
                FeedItem.Type.Article -> imageItemType.setImageResource(R.drawable.ic_web)
            }.exhaustive
        }

        @Suppress("Deprecation") // new Html.fromHtml only available in API 24+
        private fun displayText(feedItem: FeedItem) {
            textViewContent.text = feedItem.text
        }

        private fun displaySource(feedItem: FeedItem) {
            textViewSource.text = feedItem.sourceName
            when (feedItem.sourceType) {
                SourceType.Instagram -> imageSource.setImageResource(R.drawable.ic_instagram)
                SourceType.Twitter -> imageSource.setImageResource(R.drawable.ic_twitter)
                SourceType.Unknown -> imageSource.setImageResource(R.drawable.ic_transmission)
            }.exhaustive
        }

        private fun currentImageUri(): ImageUrl? {
            val currentItem = this.currentItem
            return if (currentItem == null || currentItem.imageUrls.isEmpty()) {
                null
            } else {
                currentItem.imageUrls[currentGalleryIndex]
            }
        }

        private fun showImage(size: Size) {
            imageSwitcher.visibility = View.VISIBLE

            //prepare image size before the image loaded to prevent jarring scrolling behavior
            if (imageWidth != 0 && !size.isUnknown) {
                //scale proportionally
                val estimatedImageHeight = imageWidth * size.height / size.width
                image.layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        estimatedImageHeight)
            } else {
                //or reset the min limit
                image.layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        defaultImageHeight)
            }
        }

        private fun hideImage() {
            imageSwitcher.visibility = View.GONE
        }

        private fun setImageContent(imageUri: ImageUrl, contentDescription: String?) {
            image.contentDescription = contentDescription
            McLarenImageDownloader.loadImage(image, imageUri, ImageSizeLimit.TILE)
        }
    }

    private val items: MutableList<FeedItem> = ArrayList()

    private val interactionListenerReference = WeakReference(interactionListener)
    private val scrollingListenerReference = WeakReference(scrollingListener)

    private val defaultImageHeight = context.resources.getDimensionPixelSize(R.dimen.feed_image_height)

    /** Stores last known width of image to be used for preliminary image size adjustment.  */
    private var imageWidth = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        cacheImageWidth(parent)
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_feed, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.display(context, items[position])
        notifyAboutScrollEventsIfNecessary(position)
    }

    private fun cacheImageWidth(listRootView: View?) {
        if (listRootView != null && listRootView.width != 0) {
            //This code depends on particular layout but the only thing we have when we inflate the first view
            //is it's parent so it's faster to guess view size based on parent's size.
            val sideMargins = 2 * context.resources.getDimensionPixelSize(R.dimen.margin_one) +
                    context.resources.getDimensionPixelSize(R.dimen.margin_half)
            imageWidth = listRootView.width - sideMargins
        }
    }

    private fun notifyAboutScrollEventsIfNecessary(position: Int) {
        @Suppress("MagicNumber") // notify after one third is scrolled
        if (position == itemCount / 3 + 1) {
            notifyItemFromSecondThirdDisplayed()
        }
        // then after the last item is shown
        if (position == itemCount - 1) {
            notifyLastItemDisplayed()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<FeedItem>): Boolean {
        val hasNewerItems = hasNewerItems(items)
        val diff = DiffUtil.calculateDiff(createListComparisonCallback(this.items, items), false)
        this.items.clear()
        this.items.addAll(items)
        diff.dispatchUpdatesTo(this)
        return hasNewerItems
    }

    @Suppress("ReturnCount")
    private fun hasNewerItems(newFeedItems: List<FeedItem>): Boolean {
        if (items.isEmpty()) {
            return newFeedItems.isNotEmpty()
        }
        if (newFeedItems.isEmpty()) {
            return false
        }

        return items[0].id < newFeedItems[0].id
    }

    private fun createListComparisonCallback(
        oldItems: List<FeedItem>,
        newItems: List<FeedItem>
    ): DiffUtil.Callback {
        return object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return areContentsTheSame(oldItemPosition, newItemPosition)
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return oldItem == newItem
            }
        }
    }

    private fun notifyItemClicked(item: FeedItem) {
        val listener = interactionListenerReference.get()
        listener?.onItemClicked(item)
    }

    private fun notifyItemSourceClicked(item: FeedItem) {
        val listener = interactionListenerReference.get()
        listener?.onItemSourceClicked(item)
    }

    private fun notifyLinkClicked(item: FeedItem, link: String) {
        val listener = interactionListenerReference.get()
        listener?.onLinkClicked(item, link)
    }

    private fun notifyItemFromSecondThirdDisplayed() {
        val listener = scrollingListenerReference.get()
        listener?.onScrolledToSecondThird()
    }

    private fun notifyLastItemDisplayed() {
        val listener = scrollingListenerReference.get()
        listener?.onScrolledToBottom()
    }
}
