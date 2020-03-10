package com.github.fo2rist.mclaren.ui.driversscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.repository.DriversRepository;
import dagger.android.support.AndroidSupportInjection;
import javax.inject.Inject;

/**
 * Shows Team Drivers.
 * Two main drivers with info and summary page for others.
 */
public class DriversFragment extends Fragment {
    @Inject
    DriversRepository driversRepository;

    public static DriversFragment newInstance() {
        return new DriversFragment();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drivers, container, false);

        DriversPagerAdapter driversAdapter = new DriversPagerAdapter(
                getFragmentManager(), driversRepository.getDriversList());
        ViewPager viewPager = rootView.findViewById(R.id.drivers_pager);
        viewPager.setAdapter(driversAdapter);

        return rootView;
    }
}
