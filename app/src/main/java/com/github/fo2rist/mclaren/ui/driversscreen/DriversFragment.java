package com.github.fo2rist.mclaren.ui.driversscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fo2rist.mclaren.R;

/**
 * Shows Team Drivers.
 * Two main drivers with info and summary page for others.
 */
public class DriversFragment extends Fragment {

    public static DriversFragment newInstance() {
        return new DriversFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drivers, container, false);

        DriversPagerAdapter driversAdapter = new DriversPagerAdapter(getFragmentManager());
        ViewPager viewPager = rootView.findViewById(R.id.drivers_pager);
        viewPager.setAdapter(driversAdapter);

        return rootView;
    }
}
