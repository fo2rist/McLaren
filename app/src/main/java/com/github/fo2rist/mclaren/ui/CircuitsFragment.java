package com.github.fo2rist.mclaren.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.ui.adapters.CircuitsAdapter;

import timber.log.Timber;

/**
 * A fragment representing a list of Circuits.
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
         * Circuit with given name and number selected
         * @param number 1-based number of circuit in the championship
         */
        void onCircuitSelected(String circuitName, int number);
    }

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int columnCount = 2;
    private OnCircuitsFragmentInteractionListener listener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CircuitsFragment() {
    }

    public static CircuitsFragment newInstanceForColumns(int columnCount) {
        CircuitsFragment fragment = new CircuitsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            columnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentt_circuits_list, container, false);

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

        recyclerView.setAdapter(
                new CircuitsAdapter(getContext(),
                        getResources().getStringArray(R.array.circuit_names),
                        listener));
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCircuitsFragmentInteractionListener) {
            listener = (OnCircuitsFragmentInteractionListener) context;
        } else {
            Timber.e("%s must implement OnCircuitsFragmentInteractionListener", context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
