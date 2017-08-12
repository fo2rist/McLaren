package com.github.fo2rist.mclaren.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.fo2rist.mclaren.CircuitsFragment.OnCircuitsFragmentInteractionListener;
import com.github.fo2rist.mclaren.R;
import java.util.Arrays;
import java.util.List;

import static com.github.fo2rist.mclaren.uiutils.ResourcesUtils.getCircuitImageUriByNumber;

/**
 * {@link RecyclerView.Adapter} that can display a Circuit map and makes a call to the
 * specified {@link OnCircuitsFragmentInteractionListener}.
 */
public class CircuitsAdapter extends RecyclerView.Adapter<CircuitsAdapter.CircuitViewHolder> {


    class CircuitViewHolder extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView imageMap;
        final TextView textName;
        final TextView textDetails;

        Animation animation;

        String item;

        CircuitViewHolder(View view) {
            super(view);
            this.rootView = view;
            imageMap = (ImageView) view.findViewById(R.id.image_map);
            textName = (TextView) view.findViewById(R.id.text_name);
            textDetails = (TextView) view.findViewById(R.id.text_details);
        }
    }

    private Context context;
    private final List<String> circuitNames;
    private final OnCircuitsFragmentInteractionListener listener;

    private int lastAnimatedItem = -1;

    public CircuitsAdapter(Context context, String[]items, OnCircuitsFragmentInteractionListener listener) {
        this.context = context;
        circuitNames = Arrays.asList(items);
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return circuitNames.size();
    }

    @Override
    public CircuitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_circuit, parent, false);
        return new CircuitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CircuitViewHolder holder, int position) {
        String circuitName = circuitNames.get(position);
        holder.item = circuitName;
        holder.imageMap.setImageURI(getCircuitImageUriByNumber(position));
        holder.imageMap.setContentDescription(circuitName);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    listener.onCircuitSelected(holder.item, holder.getAdapterPosition());
                }
            }
        });

        if (needToAnimateItemAtPosition(position)) {
            holder.animation = AnimationUtils.loadAnimation(context,
                    position % 2 == 0 ? R.anim.slide_in_from_left : R.anim.slide_in_from_right);
            holder.itemView.startAnimation(holder.animation);
        }
    }

    private boolean needToAnimateItemAtPosition(int position) {
        if (lastAnimatedItem < position) {
            lastAnimatedItem = position;
            return true;
        } else {
            return false;
        }
    }

}
