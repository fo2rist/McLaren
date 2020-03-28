package com.github.fo2rist.mclaren.ui.circuitsscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.repository.RaceCalendarRepository;
import com.github.fo2rist.mclaren.ui.models.RaceCalendar;
import dagger.android.support.AndroidSupportInjection;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * A fragment showing this year Calendar as the list of Circuits.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnCircuitsFragmentInteractionListener}
 * interface.
 */
public class CircuitsFragment extends Fragment {

    /**
     * This interface must be implemented by activities that contain this fragment.
     */
    public interface OnCircuitsFragmentInteractionListener {
        /**
         * Circuit with given name and number selected.
         * @param eventNumber 0-based number of event in calendar.
         */
        void onCircuitSelected(int eventNumber);
    }

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final int DEFAULT_COLUMNS_COUNT = 2;

    @Inject
    RaceCalendarRepository raceCalendarRepository;

    private int columnCount = DEFAULT_COLUMNS_COUNT;
    private OnCircuitsFragmentInteractionListener listener;

    public static CircuitsFragment newInstanceForColumns(int columnCount) {
        CircuitsFragment fragment = new CircuitsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof OnCircuitsFragmentInteractionListener) {
            listener = (OnCircuitsFragmentInteractionListener) context;
        } else {
            Timber.e("%s must implement OnCircuitsFragmentInteractionListener", context.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchBundleParameters();
    }

    private void fetchBundleParameters() {
        Bundle args = getArguments();
        if (args != null) {
            columnCount = args.getInt(ARG_COLUMN_COUNT, DEFAULT_COLUMNS_COUNT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circuits_list, container, false);

        if (!(view instanceof RecyclerView)) {
            throw new IllegalArgumentException("Layout should be a single Recycler View");
        }

        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        if (columnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount));
        }

        RaceCalendar eventsCalendar = raceCalendarRepository.loadCalendar();
        recyclerView.setAdapter(
                new CircuitsAdapter(context, eventsCalendar, listener));
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
