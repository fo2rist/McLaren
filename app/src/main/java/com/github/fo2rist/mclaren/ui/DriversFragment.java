package com.github.fo2rist.mclaren.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.ui.adapters.DriversPagerAdapter;

import timber.log.Timber;


/**
 * Shows Team Drivers.
 * Two main drivers with info and summary page for others.
 */
public class DriversFragment extends Fragment {

    /**
     * This interface must be implemented by activities that contain this fragment.
     */
    public interface OnDriversFragmentInteractionListener {
        void onDriversFragmentInteraction(Uri uri);
    }

    private OnDriversFragmentInteractionListener listener;

    public DriversFragment() {
        // Required empty public constructor
    }

    public static DriversFragment newInstance() {
        DriversFragment fragment = new DriversFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDriversFragmentInteractionListener) {
            listener = (OnDriversFragmentInteractionListener) context;
        } else {
            Timber.e("%s must implement OnDriversFragmentInteractionListener", context.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drivers, container, false);

        DriversPagerAdapter driversAdapter = new DriversPagerAdapter(getFragmentManager());
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.drivers_pager);
        viewPager.setAdapter(driversAdapter);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
