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
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for main page feed of news.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

        ViewHolder(View rootView) {
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
                    loadImage(image, getNextImage());
                    break;
                case Message:
                case Image:
                    break;
            }
        }

        void setCurrentItem(FeedItem item) {
            currentItem = item;
            currentGalleryIndex = 0;
        }

        Uri getCurrentImage() {
            return currentItem.imageUris[currentGalleryIndex];
        }

        Uri getNextImage() {
            currentGalleryIndex = (currentGalleryIndex + 1) % currentItem.imageUris.length;
            return getCurrentImage();
        }
    }

    private Context context;
    private List<FeedItem> items = new ArrayList<>();

    public FeedAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeedItem feedItem = items.get(position);

        holder.setCurrentItem(feedItem);

        holder.textViewDate.setText(DateFormat.getDateFormat(context).format(feedItem.dateTime));
        holder.textViewTime.setText(DateFormat.getTimeFormat(context).format(feedItem.dateTime));
        switch (feedItem.type) {
            case Gallery:
                holder.imageSwitcher.setVisibility(View.VISIBLE);
                loadImage(holder.image, holder.getCurrentImage());
                holder.image.setContentDescription(feedItem.text);
                holder.imageItemType.setImageResource(R.drawable.ic_gallery);
                break;
            case Image:
                holder.imageSwitcher.setVisibility(View.VISIBLE);
                loadImage(holder.image, holder.getCurrentImage());
                holder.image.setContentDescription(feedItem.text);
                holder.imageItemType.setImageResource(R.drawable.ic_photo);
                break;
            case Video:
                holder.imageSwitcher.setVisibility(View.VISIBLE);
                holder.image.setImageURI(Uri.parse("android.resource://com.github.fo2rist.mclaren/drawable/ic_video"));
                holder.image.setContentDescription(feedItem.text);
                holder.imageItemType.setImageResource(R.drawable.ic_video);
                break;
            case Message:
                holder.imageSwitcher.setVisibility(View.GONE);
                holder.imageItemType.setImageResource(R.drawable.ic_text);
                break;
            case Article:
                holder.imageSwitcher.setVisibility(View.GONE); //TODO display preview here 23.12.2016 fo2rist
                holder.imageItemType.setImageResource(R.drawable.ic_web);
                break;
        }
        holder.textViewContent.setText(feedItem.text);
        holder.textViewSource.setText(feedItem.sourceName);
        switch (feedItem.sourceType) {
            case Instagram: //TODO display proper images here 17.04.2017
                holder.imageSource.setImageResource(R.drawable.ic_gallery);
                holder.imageSource.setVisibility(View.VISIBLE);
                break;
            case Twitter:
                holder.imageSource.setImageResource(R.drawable.ic_share);
                holder.imageSource.setVisibility(View.VISIBLE);
                break;
            case Unknown:
                holder.imageSource.setImageResource(0);
                holder.imageSource.setVisibility(View.GONE);
                break;
        }
    }

    private static void loadImage(ImageView imageView, Uri imageUri) {
        Picasso.with(imageView.getContext()).load(imageUri).into(imageView);
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
