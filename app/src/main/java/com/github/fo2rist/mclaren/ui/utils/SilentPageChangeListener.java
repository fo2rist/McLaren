package com.github.fo2rist.mclaren.ui.utils;

import androidx.viewpager.widget.ViewPager;

/**
 * Default empty implementation of {@link ViewPager.OnPageChangeListener}
 */
@SuppressWarnings("PMD.UncommentedEmptyMethod")
public class SilentPageChangeListener implements ViewPager.OnPageChangeListener {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
