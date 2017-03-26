package com.github.fo2rist.mclaren;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fo2rist.mclaren.adapters.CircuitsAdapter;

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
        void onCircuitsFragmentInteraction(String circuitName, int number);
    }

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int columnCount_ = 2;
    private OnCircuitsFragmentInteractionListener listener_;

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
            columnCount_ = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_circuits_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (columnCount_ <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, columnCount_));
            }

            recyclerView.setAdapter(
                    new CircuitsAdapter(getContext(),
                            getResources().getStringArray(R.array.circuit_names),
                            listener_));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCircuitsFragmentInteractionListener) {
            listener_ = (OnCircuitsFragmentInteractionListener) context;
        } else {
            Timber.e("%s must implement OnCircuitsFragmentInteractionListener", context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener_ = null;
    }
}
