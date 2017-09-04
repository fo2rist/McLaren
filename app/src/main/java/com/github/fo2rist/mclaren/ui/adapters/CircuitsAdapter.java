package com.github.fo2rist.mclaren.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.ui.CircuitsFragment.OnCircuitsFragmentInteractionListener;
import com.github.fo2rist.mclaren.ui.models.CalendarEvent;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.github.fo2rist.mclaren.ui.utils.ResourcesUtils.getCircuitImageUriById;

/**
 * {@link RecyclerView.Adapter} that can display a Circuit map and makes a call to the
 * specified {@link OnCircuitsFragmentInteractionListener}.
 */
public class CircuitsAdapter extends RecyclerView.Adapter<CircuitsAdapter.CircuitViewHolder> {


    class CircuitViewHolder extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView imageCircuitMap;
        final TextView textGrandPrixName;
        final TextView textDetails;

        Animation animation;

        CalendarEvent item;

        CircuitViewHolder(View view) {
            super(view);
            this.rootView = view;
            imageCircuitMap = view.findViewById(R.id.image_map);
            textGrandPrixName = view.findViewById(R.id.text_gp_name);
            textDetails = view.findViewById(R.id.text_details);
        }
    }

    private Context context;
    private SimpleDateFormat dateFormatter;
    private final List<CalendarEvent> calendarEvents;
    private final OnCircuitsFragmentInteractionListener listener;

    private int lastAnimatedItem = -1;

    public CircuitsAdapter(Context context, List<CalendarEvent> calendarEvents, OnCircuitsFragmentInteractionListener listener) {
        this.context = context;
        this.dateFormatter = new SimpleDateFormat(context.getString(R.string.calendar_event_date_format));
        this.calendarEvents = calendarEvents;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return calendarEvents.size();
    }

    @Override
    public CircuitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_circuit, parent, false);
        return new CircuitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CircuitViewHolder holder, int position) {
        CalendarEvent event = calendarEvents.get(position);
        holder.item = event;

        holder.imageCircuitMap.setImageURI(getCircuitImageUriById(event.circuitId));
        holder.imageCircuitMap.setContentDescription(event.trackName);

        holder.textGrandPrixName.setText(event.grandPrixName);
        holder.textDetails.setText(formatDetails(event));

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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

    @NonNull
    private String formatDetails(CalendarEvent event) {
        String start = dateFormatter.format(event.startDate);
        String end = dateFormatter.format(event.endDate);
        return context.getString(R.string.calendar_event_details_format, event.city, start, end);
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
