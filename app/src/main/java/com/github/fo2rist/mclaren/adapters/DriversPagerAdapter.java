package com.github.fo2rist.mclaren.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.fo2rist.mclaren.DriverSubFragment;
import com.github.fo2rist.mclaren.models.DriversFactory.DriverId;

import static com.github.fo2rist.mclaren.models.DriversFactory.DriverId.Alonso;
import static com.github.fo2rist.mclaren.models.DriversFactory.DriverId.Button;
import static com.github.fo2rist.mclaren.models.DriversFactory.DriverId.Vandoorne;

/**
 * Represent "tabs" on drivers page
 */
public class DriversPagerAdapter extends FragmentPagerAdapter {
    private DriverId driverIds[] = new DriverId[] {
            Alonso, Vandoorne, Button
    };
    private DriverSubFragment pages[] = new DriverSubFragment[driverIds.length];

    public DriversPagerAdapter(FragmentManager fm) {
        super(fm);
        for (int i = 0; i < driverIds.length; i++) {
            pages[i] = DriverSubFragment.newInstance(driverIds[i]);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return pages[position];
    }

    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return driverIds[position].name();
    }
}
