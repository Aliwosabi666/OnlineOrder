package com.CRM.opportunity.oppertunites.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.CRM.opportunity.oppertunites.DynamicFragment;


public class TabAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;


    public TabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
//        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
            return DynamicFragment.addfrag(position+1);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}