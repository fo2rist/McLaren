package com.github.fo2rist.mclaren.ui.driversscreen;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.github.fo2rist.mclaren.ui.models.DriverId;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent "tabs" on drivers page.
 */
public class DriversPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<DriverSubFragment> pages = new ArrayList<>();

    DriversPagerAdapter(FragmentManager fm, List<DriverId> driversList) {
        super(fm);

        for (DriverId driver: driversList) {
            pages.add(DriverSubFragment.newInstance(driver));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position);
    }

    @Override
    public int getCount() {
        return pages.size();
    }

}
