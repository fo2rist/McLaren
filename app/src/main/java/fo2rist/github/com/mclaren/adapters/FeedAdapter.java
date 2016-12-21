package fo2rist.github.com.mclaren.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import android.text.format.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fo2rist.github.com.mclaren.R;
import fo2rist.github.com.mclaren.models.FeedItem;

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
        public ImageView imageView;

        public ViewHolder(View rootView) {
            super(rootView);
            textViewDate = (TextView) rootView.findViewById(R.id.text_date);
            textViewTime = (TextView) rootView.findViewById(R.id.text_time);
            textViewContent = (TextView) rootView.findViewById(R.id.text_content);
            textViewSource = (TextView) rootView.findViewById(R.id.text_source);

            imageSwitcher = (ImageSwitcher) rootView.findViewById(R.id.image_switcher);
            imageView = (ImageView) rootView.findViewById(R.id.image);
        }
    }

    private List<FeedItem> items_ = new ArrayList<>();

    public FeedAdapter(Context context) {
        context_ = context;
        /*DEBUG*/
        FeedItem feedItem = new FeedItem(Calendar.getInstance().getTime(), "One bla", FeedItem.SourceType.Twitter, "@fo2rist");
        items_.add(feedItem);
        feedItem = new FeedItem(Calendar.getInstance().getTime(), "Two bla", FeedItem.SourceType.Instagram, "@fo2rist");
        items_.add(feedItem);
        feedItem = new FeedItem(Calendar.getInstance().getTime(), "Bla bla image", FeedItem.SourceType.Other, "@fo2rist", Uri.parse("android.resource://fo2rist.github.com.mclaren/drawable/image_splash"));
        items_.add(feedItem);
        feedItem = new FeedItem(Calendar.getInstance().getTime(), "Bla bla image same", FeedItem.SourceType.Twitter, "@fo2rist", Uri.parse("https://static.pexels.com/photos/33045/lion-wild-africa-african.jpg"));
        items_.add(feedItem);

        ArrayList<Uri> imageUrls = new ArrayList<>();
        imageUrls.add(Uri.parse("android.resource://fo2rist.github.com.mclaren/drawable/image_background_pattern_small"));
        imageUrls.add(Uri.parse("android.resource://fo2rist.github.com.mclaren/drawable/image_splash"));
        imageUrls.add(Uri.parse("https://static.pexels.com/photos/33045/lion-wild-africa-african.jpg"));
        feedItem = new FeedItem(Calendar.getInstance().getTime(), "Bla bla gallery", FeedItem.SourceType.Instagram, "@fo2rist", imageUrls);
        items_.add(feedItem);

        feedItem = new FeedItem(Calendar.getInstance().getTime(), "Bla bla gallery same", FeedItem.SourceType.Twitter, "@fo2rist", imageUrls);
        items_.add(feedItem);
        /*END DEBUG*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_feed, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeedItem feedItem = items_.get(position);
        holder.textViewDate.setText(DateFormat.getDateFormat(context_).format(feedItem.getDateTime()));
        holder.textViewTime.setText(DateFormat.getTimeFormat(context_).format(feedItem.getDateTime()));
        switch (feedItem.getType()) {
            case Gallery:
                holder.imageSwitcher.setVisibility(View.VISIBLE);
                holder.imageView.setImageURI(Uri.parse(feedItem.getImageUri().toString()));
                break;
            case Photo:
                holder.imageSwitcher.setVisibility(View.VISIBLE);
                holder.imageView.setImageURI(Uri.parse(feedItem.getImageUri().toString()));
                break;
            case Video:
                holder.imageSwitcher.setVisibility(View.VISIBLE);
                holder.imageView.setImageURI(Uri.parse("android.resource://fo2rist.github.com.mclaren/drawable/ic_menu_slideshow"));
                break;
            case Text:
                holder.imageSwitcher.setVisibility(View.GONE);
                break;
        }
        holder.textViewContent.setText(feedItem.getText());
        holder.textViewSource.setText(feedItem.getSourceName());
        switch (feedItem.getSourceType()) {
            case Instagram:
                holder.textViewSource.setCompoundDrawables(ContextCompat.getDrawable(context_, R.drawable.ic_menu_gallery), null, null, null);
                break;
            case Twitter:
                holder.textViewSource.setCompoundDrawables(ContextCompat.getDrawable(context_, R.drawable.ic_menu_manage), null, null, null);
                break;
            case Other:
                holder.textViewSource.setCompoundDrawables(null, null, null, null);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items_.size();
    }
}
