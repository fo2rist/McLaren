package com.github.fo2rist.mclaren.ui.circuitsscreen;

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
import com.github.fo2rist.mclaren.ui.circuitsscreen.CircuitsFragment.OnCircuitsFragmentInteractionListener;
import com.github.fo2rist.mclaren.ui.models.CalendarEvent;
import com.github.fo2rist.mclaren.ui.models.RaceCalendar;
import com.github.fo2rist.mclaren.utils.DateTimeUtils;
import org.joda.time.DateTime;

import static com.github.fo2rist.mclaren.utils.ResourcesUtils.getCircuitImageUriById;

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
    private final RaceCalendar calendarEvents;
    private final OnCircuitsFragmentInteractionListener listener;

    private int lastAnimatedItem = -1;

    CircuitsAdapter(Context context, RaceCalendar calendarEvents, OnCircuitsFragmentInteractionListener listener) {
        this.context = context;
        this.calendarEvents = calendarEvents;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return calendarEvents.size();
    }

    @NonNull
    @Override
    public CircuitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_circuit, parent, false);
        return new CircuitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CircuitViewHolder holder, int position) {
        CalendarEvent event = calendarEvents.get(position);
        holder.item = event;
        boolean isActiveNow = event.isActiveAt(DateTime.now());

        holder.imageCircuitMap.setSelected(isActiveNow);
        holder.imageCircuitMap.setImageURI(getCircuitImageUriById(event.getCircuitId()));
        holder.imageCircuitMap.setContentDescription(event.getTrackName());

        holder.textGrandPrixName.setText(event.getGrandPrixName());
        holder.textDetails.setText(formatDetails(event));

        holder.rootView.setOnClickListener(view -> {
            if (null != listener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                listener.onCircuitSelected(holder.item);
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
        String start = DateTimeUtils.formatShort(context, event.getStartDate());
        String end = DateTimeUtils.formatShort(context, event.getEndDate());
        return context.getString(R.string.calendar_event_details_format, event.getCity(), start, end);
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
