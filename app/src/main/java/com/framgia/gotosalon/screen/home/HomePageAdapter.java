package com.framgia.gotosalon.screen.home;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.framgia.gotosalon.R;

public class HomePageAdapter extends FragmentPagerAdapter {
    private static final int NUMBER_NEW_FRAGMENT = 0;
    private static final int NUMBER_HOT_FRAGMENT = 1;
    private static final int TOTAL_FRAGMENT = 2;
    private Context mContext;

    public HomePageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case NUMBER_HOT_FRAGMENT:
                return new HotFragment();
            case NUMBER_NEW_FRAGMENT:
                return new NewFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return TOTAL_FRAGMENT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case NUMBER_NEW_FRAGMENT:
                return mContext.getResources().getString(R.string.title_new_fragment);
            case NUMBER_HOT_FRAGMENT:
                return mContext.getResources().getString(R.string.title_hot_fragment);
        }
        return super.getPageTitle(position);
    }
}
