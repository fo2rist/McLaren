package com.github.fo2rist.mclaren.ui.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.web.McLarenImageDownloader;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

import static com.github.fo2rist.mclaren.utils.IntentUtils.openInBrowser;
import static com.github.fo2rist.mclaren.utils.LinkUtils.getFeedHashtagLink;
import static com.github.fo2rist.mclaren.utils.LinkUtils.getFeedMentionLink;

/**
 * Adapter for main page feed of news.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {


    public interface OnFeedInteractionListener {
        void onItemDetailsRequested(FeedItem item);
    }

    public interface OnFeedScrollingListener {
        void onScrolledToSecondThird();

        void onScrolledToBottom();
    }

    class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, AutoLinkOnClickListener {
        View rootView;
        TextView textViewDate;
        TextView textViewTime;
        AutoLinkTextView textViewContent;
        TextView textViewSource;
        ImageSwitcher imageSwitcher;
        ImageView image;
        ImageView imageItemType;
        ImageView imageSource;
        View containerSource;

        private FeedItem currentItem;
        private int currentGalleryIndex;

        FeedViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.textViewDate = rootView.findViewById(R.id.text_date);
            this.textViewTime = rootView.findViewById(R.id.text_time);
            this.textViewContent = rootView.findViewById(R.id.text_content);
            this.textViewSource = rootView.findViewById(R.id.text_source);
            this.containerSource = rootView.findViewById(R.id.container_source);

            this.imageSwitcher = rootView.findViewById(R.id.image_switcher);
            this.image = rootView.findViewById(R.id.image);
            this.imageItemType = rootView.findViewById(R.id.image_type);
            this.imageSource = rootView.findViewById(R.id.image_source);

            this.imageSwitcher.setOnClickListener(this);
            this.containerSource.setOnClickListener(this);

            this.textViewContent.addAutoLinkMode(
                    AutoLinkMode.MODE_HASHTAG,
                    AutoLinkMode.MODE_MENTION,
                    AutoLinkMode.MODE_URL);
            this.textViewContent.setAutoLinkOnClickListener(this);
            this.textViewContent.setMentionModeColor(ContextCompat.getColor(context, R.color.textSecondaryBlack));
            this.textViewContent.setHashtagModeColor(ContextCompat.getColor(context, R.color.textSecondaryBlack));
            this.textViewContent.setUrlModeColor(ContextCompat.getColor(context, R.color.colorAccent));
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_switcher:
                    onFeedImageClicked(currentItem);
                    break;
                case R.id.container_source:
                    onFeedSourceClicked(currentItem);
                    break;
            }
        }

        private void onFeedImageClicked(FeedItem feedItem) {
            notifyItemRequested(feedItem);
        }

        private void onFeedSourceClicked(FeedItem feedItem) {
            openInBrowser(context, getFeedMentionLink(feedItem, feedItem.sourceName));
        }

        @Override
        public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String autoLink) {
            String link;
            switch (autoLinkMode) {
                case MODE_HASHTAG:
                    link = getFeedHashtagLink(currentItem, autoLink);
                    break;
                case MODE_MENTION:
                    link = getFeedMentionLink(currentItem, autoLink);
                    break;
                case MODE_URL:
                    link = autoLink;
                    break;
                default:
                    Timber.w("Clicked unknown link: " + autoLink + " in mode: " + autoLinkMode);
                    link = null;
                    break;
            }
            if (!TextUtils.isEmpty(link)) {
                openInBrowser(context, link);
            }
        }

        void display(Context context, FeedItem feedItem) {
            setCurrentItem(feedItem);

            displayDateTime(context, feedItem);
            displayImage(feedItem);
            displayDataTypeIcon(feedItem);
            displayText(feedItem);
            displaySource(feedItem);
        }

        private void displayDateTime(Context context, FeedItem feedItem) {
            this.textViewDate.setText(DateFormat.getDateFormat(context).format(feedItem.dateTime));
            this.textViewTime.setText(DateFormat.getTimeFormat(context).format(feedItem.dateTime));
        }

        private void displayImage(FeedItem feedItem) {
            switch (feedItem.type) {
                case Message:
                    hideImage();
                    break;
                case Gallery:
                case Image:
                case Video:
                case Article:
                    displayImage(getCurrentImageUri());
                    break;
            }
        }

        void setCurrentItem(FeedItem item) {
            currentItem = item;
            currentGalleryIndex = 0;
        }

        private void displayImage(String imageUri) {
            if (imageUri == null) {
                hideImage();
            } else {
                this.imageSwitcher.setVisibility(View.VISIBLE);
                McLarenImageDownloader.loadImage(this.image, imageUri);
                this.image.setContentDescription(currentItem.text);
            }
        }

        private void displayDataTypeIcon(FeedItem feedItem) {
            switch (feedItem.type) {
                case Gallery:
                    this.imageItemType.setImageResource(R.drawable.ic_gallery);
                    break;
                case Image:
                    this.imageItemType.setImageResource(R.drawable.ic_photo);
                    break;
                case Video:
                    this.imageItemType.setImageResource(R.drawable.ic_video);
                    break;
                case Message:
                    this.imageItemType.setImageResource(R.drawable.ic_text);
                    break;
                case Article:
                    this.imageItemType.setImageResource(R.drawable.ic_web);
                    break;
            }
        }

        private void displayText(FeedItem feedItem) {
            this.textViewContent.setAutoLinkText(feedItem.text);
        }

        private void displaySource(FeedItem feedItem) {
            this.textViewSource.setText(feedItem.sourceName);
            switch (feedItem.sourceType) {
                case Instagram:
                    this.imageSource.setImageResource(R.drawable.ic_instagram);
                    break;
                case Twitter:
                    this.imageSource.setImageResource(R.drawable.ic_twitter);
                    break;
                case Unknown:
                    this.imageSource.setImageResource(R.drawable.ic_transmission);
                    break;
            }
        }

        private void hideImage() {
            this.imageSwitcher.setVisibility(View.GONE);
        }

        @Nullable
        private String getCurrentImageUri() {
            if (currentItem == null || currentItem.imageUrls.length == 0) {
                return null;
            }
            return currentItem.imageUrls[currentGalleryIndex];
        }
    }

    private final Context context;
    private final WeakReference<OnFeedInteractionListener> interactionListenerReference;
    private final WeakReference<OnFeedScrollingListener> scrollingListenerReference;
    private List<FeedItem> items = new ArrayList<>();

    public FeedAdapter(Context context,
            OnFeedInteractionListener interactionListener,
            OnFeedScrollingListener scrollingListener) {
        this.context = context;
        this.interactionListenerReference = new WeakReference<>(interactionListener);
        this.scrollingListenerReference = new WeakReference<>(scrollingListener);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        notifiAboutScrollEventsIfNecessary(position);
        FeedItem feedItem = items.get(position);
        holder.display(context, feedItem);
    }

    private void notifiAboutScrollEventsIfNecessary(int position) {
        if (position == getItemCount() / 3 + 1) {
            notifyItemFromSecondThirdDisplayed();
        }
        if (position == getItemCount() - 1) {
            notifyLastItemDisplayed();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public boolean setItems(List<FeedItem> items) {
        boolean hasNewerItems = hasNewerItems(items);
        DiffUtil.DiffResult diff = DiffUtil.calculateDiff(createListComparisonCallback(this.items, items), false);
        this.items.clear();
        this.items.addAll(items);
        diff.dispatchUpdatesTo(this);
        return hasNewerItems;
    }

    private boolean hasNewerItems(List<FeedItem> newFeedItems) {
        if (this.items.isEmpty()) {
            return !newFeedItems.isEmpty();
        }

        if (newFeedItems.isEmpty()) {
            return false;
        }

        return this.items.get(0).id < newFeedItems.iterator().next().id;
    }

    private DiffUtil.Callback createListComparisonCallback(final List<FeedItem> oldItems, final List<FeedItem> newItems) {
        return new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldItems.size();
            }

            @Override
            public int getNewListSize() {
                return newItems.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return areContentsTheSame(oldItemPosition, newItemPosition);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                FeedItem oldItem = oldItems.get(oldItemPosition);
                FeedItem newItem = newItems.get(newItemPosition);
                return oldItem.equals(newItem);
            }
        };
    }

    private void notifyItemRequested(FeedItem item) {
        OnFeedInteractionListener listener = interactionListenerReference.get();
        if (listener != null) {
            listener.onItemDetailsRequested(item);
        }
    }

    private void notifyItemFromSecondThirdDisplayed() {
        OnFeedScrollingListener listener = scrollingListenerReference.get();
        if (listener != null) {
            listener.onScrolledToSecondThird();
        }
    }

    private void notifyLastItemDisplayed() {
        OnFeedScrollingListener listener = scrollingListenerReference.get();
        if (listener != null) {
            listener.onScrolledToBottom();
        }
    }
}
