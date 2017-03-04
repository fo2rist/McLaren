package com.github.fo2rist.mclaren.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.github.fo2rist.mclaren.DriverSubFragment;

/**
 * Represet "tabs" on drivers page
 */
public class DriversPagerAdapter extends FragmentPagerAdapter {
    DriverSubFragment pages[] = new DriverSubFragment[3];

    public DriversPagerAdapter(FragmentManager fm) {
        super(fm);
        pages[0] = DriverSubFragment.newInstance("Alonso");
        pages[1] = DriverSubFragment.newInstance("VanDorne");
        pages[2] = DriverSubFragment.newInstance("Button");
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
        return pages[position].getTitle();
    }
}
