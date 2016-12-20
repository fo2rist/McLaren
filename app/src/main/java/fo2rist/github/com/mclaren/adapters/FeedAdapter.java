package fo2rist.github.com.mclaren.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import fo2rist.github.com.mclaren.R;

/**
 * Adapter for main page feed of news.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_feed, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewContent.setText("test");
    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
