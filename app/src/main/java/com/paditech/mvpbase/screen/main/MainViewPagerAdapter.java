package com.paditech.mvpbase.screen.main;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.paditech.mvpbase.screen.category.CategoryFragment;
import com.paditech.mvpbase.screen.home.HomeFragment;
import com.paditech.mvpbase.screen.search.SearchFragment;

/**
 * Created by hung on 1/22/2018.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {
    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    Activity act;

    public Activity getAct() {
        return act;
    }

    public void setAct(Activity act) {
        this.act = act;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        android.support.v4.app.Fragment fragment = null ;
        switch (position){
            case 0:
                fragment =HomeFragment.getInstance(getAct());
                return fragment;
            case 1:
                fragment = new CategoryFragment();
                return fragment;
            default:
                fragment = new SearchFragment();
                return fragment;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Home";
                break;
            case 1:
                title = "Category";
                break;
            default:
                title = "Search";
                break;
        }
        return title;
    }
}
