package com.github.fo2rist.mclaren.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.models.FeedItem;
import com.github.fo2rist.mclaren.ui.utils.ImageUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for main page feed of news.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    static class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View rootView;
        TextView textViewDate;
        TextView textViewTime;
        TextView textViewContent;
        TextView textViewSource;
        ImageSwitcher imageSwitcher;
        ImageView image;
        ImageView imageItemType;
        ImageView imageSource;

        private FeedItem currentItem;
        private int currentGalleryIndex;

        FeedViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.textViewDate = rootView.findViewById(R.id.text_date);
            this.textViewTime = rootView.findViewById(R.id.text_time);
            this.textViewContent = rootView.findViewById(R.id.text_content);
            this.textViewSource = rootView.findViewById(R.id.text_source);

            this.imageSwitcher = rootView.findViewById(R.id.image_switcher);
            this.image = rootView.findViewById(R.id.image);
            this.imageItemType = rootView.findViewById(R.id.image_type);
            this.imageSource = rootView.findViewById(R.id.image_source);

            this.imageSwitcher.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (currentItem.type) {
                case Video:
                    //Not implemented yet
                    break;
                case Gallery:
                    ImageUtils.loadImage(image, getNextImageUri());
                    break;
                case Message:
                case Image:
                case Article:
                    break;
            }
        }

        void display(Context context, FeedItem feedItem) {
            this.setCurrentItem(feedItem);

            this.displayDateTime(context);

            switch (feedItem.type) {
                case Gallery:
                    displayImage(getCurrentImageUri());
                    displayDataTypeIcon(R.drawable.ic_gallery);
                    break;
                case Image:
                    displayImage(getCurrentImageUri());
                    displayDataTypeIcon(R.drawable.ic_photo);
                    break;
                case Video:
                    this.image.setImageURI(Uri.parse("android.resource://com.github.fo2rist.mclaren/drawable/ic_video"));
                    displayDataTypeIcon(R.drawable.ic_video);
                    break;
                case Message:
                    hideImage();
                    displayDataTypeIcon(R.drawable.ic_text);
                    break;
                case Article:
                    displayImage(getCurrentImageUri());
                    displayDataTypeIcon(R.drawable.ic_web);
                    break;
            }
            this.textViewContent.setText(feedItem.text);
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

        void setCurrentItem(FeedItem item) {
            currentItem = item;
            currentGalleryIndex = 0;
        }

        private void displayDateTime(Context context) {
            this.textViewDate.setText(DateFormat.getDateFormat(context).format(currentItem.dateTime));
            this.textViewTime.setText(DateFormat.getTimeFormat(context).format(currentItem.dateTime));
        }

        private void displayDataTypeIcon(int ic_gallery) {
            this.imageItemType.setImageResource(ic_gallery);
        }

        private void displayImage(String imageUri) {
            if (imageUri == null) {
                hideImage();
            } else {
                this.imageSwitcher.setVisibility(View.VISIBLE);
                ImageUtils.loadImage(this.image, imageUri);
                this.image.setContentDescription(currentItem.text);
            }
        }

        private void hideImage() {
            this.imageSwitcher.setVisibility(View.GONE);
        }

        private String getCurrentImageUri() {
            return currentItem.imageUris[currentGalleryIndex];
        }

        private String getNextImageUri() {
            currentGalleryIndex = (currentGalleryIndex + 1) % currentItem.imageUris.length;
            return getCurrentImageUri();
        }
    }

    private Context context;
    private List<FeedItem> items = new ArrayList<>();

    public FeedAdapter(Context context) {
        this.context = context;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new FeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        FeedItem feedItem = items.get(position);
        holder.display(context, feedItem);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<FeedItem> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

}
