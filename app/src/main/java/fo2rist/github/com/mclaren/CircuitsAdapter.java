package fo2rist.github.com.mclaren;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fo2rist.github.com.mclaren.CircuitsFragment.OnListFragmentInteractionListener;

import java.util.Arrays;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a Circuit map and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class CircuitsAdapter extends RecyclerView.Adapter<CircuitsAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View rootView_;
        public final ImageView imageMap_;
        public final TextView textName_;
        public final TextView textDetails;

        public String item_;

        public ViewHolder(View view) {
            super(view);
            this.rootView_ = view;
            imageMap_ = (ImageView) view.findViewById(R.id.image_map);
            textName_ = (TextView) view.findViewById(R.id.text_name);
            textDetails = (TextView) view.findViewById(R.id.text_details);
        }
    }

    private final List<String> circuitNames_;
    private final OnListFragmentInteractionListener listener_;

    public CircuitsAdapter(String[] items, OnListFragmentInteractionListener listener) {
        circuitNames_ = Arrays.asList(items);
        listener_ = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_circuit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.item_ = circuitNames_.get(position);

        holder.imageMap_.setImageURI(Uri.parse(String.format("android.resource://fo2rist.github.com.mclaren/drawable/circuit_%02d", position+1)));

        holder.rootView_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener_) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener_.onCircuitsFragmentInteraction(holder.item_, position+1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return circuitNames_.size();
    }

}
