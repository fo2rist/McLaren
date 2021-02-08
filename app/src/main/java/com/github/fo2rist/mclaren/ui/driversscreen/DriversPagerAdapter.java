package com.github.fo2rist.mclaren.ui.driversscreen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.github.fo2rist.mclaren.ui.models.DriverId;
import java.util.List;

/**
 * Represent "tabs" on drivers page.
 */
public class DriversPagerAdapter extends FragmentStatePagerAdapter {

    @NonNull
    private final List<DriverId> driverIds;

    DriversPagerAdapter(@NonNull FragmentManager fm, @NonNull List<DriverId> driversList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        driverIds = driversList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return DriverSubFragment.newInstance(driverIds.get(position));
    }

    @Override
    public int getCount() {
        return driverIds.size();
    }
}
