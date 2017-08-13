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
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for main page feed of news.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private Context context_;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDate;
        public TextView textViewTime;
        public TextView textViewContent;
        public TextView textViewSource;
        public ImageSwitcher imageSwitcher;
        public ImageView image;
        public ImageView imageItemType;

        public FeedItem currentItem_;

        public ViewHolder(View rootView) {
            super(rootView);
            textViewDate = (TextView) rootView.findViewById(R.id.text_date);
            textViewTime = (TextView) rootView.findViewById(R.id.text_time);
            textViewContent = (TextView) rootView.findViewById(R.id.text_content);
            textViewSource = (TextView) rootView.findViewById(R.id.text_source);

            imageSwitcher = (ImageSwitcher) rootView.findViewById(R.id.image_switcher);
            image = (ImageView) rootView.findViewById(R.id.image);
            imageItemType = (ImageView) rootView.findViewById(R.id.image_type);

            imageSwitcher.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (currentItem_.getType()) {
                        case Video:
                            //Not implemented yet
                            break;
                        case Gallery:
                            image.setImageURI(currentItem_.getImageUris().get(1));
                            break;
                        case Message:
                        case Image:
                            break;
                    }
                }
            });
        }
    }

    private List<FeedItem> items_ = new ArrayList<>();

    public FeedAdapter(Context context) {
        context_ = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feed, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeedItem feedItem = items_.get(position);

        holder.currentItem_ = feedItem;

        holder.textViewDate.setText(DateFormat.getDateFormat(context_).format(feedItem.getDateTime()));
        holder.textViewTime.setText(DateFormat.getTimeFormat(context_).format(feedItem.getDateTime()));
        switch (feedItem.getType()) {
            case Gallery:
                holder.imageSwitcher.setVisibility(View.VISIBLE);
                holder.image.setImageURI(Uri.parse(feedItem.getImageUri().toString()));
                holder.image.setContentDescription(feedItem.getText());
                holder.imageItemType.setImageResource(R.drawable.ic_gallery);
                break;
            case Image:
                holder.imageSwitcher.setVisibility(View.VISIBLE);
                holder.image.setImageURI(Uri.parse(feedItem.getImageUri().toString()));
                holder.image.setContentDescription(feedItem.getText());
                holder.imageItemType.setImageResource(R.drawable.ic_photo);
                break;
            case Video:
                holder.imageSwitcher.setVisibility(View.VISIBLE);
                holder.image.setImageURI(Uri.parse("android.resource://com.github.fo2rist.mclaren/drawable/ic_slideshow"));
                holder.image.setContentDescription(feedItem.getText());
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
        holder.textViewContent.setText(feedItem.getText());
        holder.textViewSource.setText(feedItem.getSourceName());
        switch (feedItem.getSourceType()) {
            case Instagram: //TODO display proper images here 17.04.2017
                holder.textViewSource.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gallery, 0, 0, 0);
                break;
            case Twitter:
                holder.textViewSource.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_share, 0, 0, 0);
                break;
            case Unknown:
                holder.textViewSource.setCompoundDrawables(null, null, null, null);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items_.size();
    }

    public void setItems(List<FeedItem> items) {
        items_.clear();
        items_.addAll(items);
        notifyDataSetChanged();
    }
}
