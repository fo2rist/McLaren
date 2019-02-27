package com.github.fo2rist.mclaren.ui.driversscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.github.fo2rist.mclaren.ui.driversscreen.DriverSubFragment;
import com.github.fo2rist.mclaren.ui.models.DriversFactory.DriverId;

import static com.github.fo2rist.mclaren.ui.models.DriversFactory.DriverId.CAMARA;
import static com.github.fo2rist.mclaren.ui.models.DriversFactory.DriverId.DEVRIES;
import static com.github.fo2rist.mclaren.ui.models.DriversFactory.DriverId.NORRIS;
import static com.github.fo2rist.mclaren.ui.models.DriversFactory.DriverId.SAINZ;
import static com.github.fo2rist.mclaren.ui.models.DriversFactory.DriverId.TURVEY;
import static com.github.fo2rist.mclaren.ui.models.DriversFactory.DriverId.VANBUREN;

/**
 * Represent "tabs" on drivers page
 */
public class DriversPagerAdapter extends FragmentStatePagerAdapter {
    private DriverId[] driverIds = new DriverId[]{SAINZ, NORRIS, TURVEY, VANBUREN, DEVRIES, CAMARA};

    private DriverSubFragment[] pages = new DriverSubFragment[driverIds.length];

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

}
