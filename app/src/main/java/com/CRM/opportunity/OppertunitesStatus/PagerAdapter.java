package com.CRM.opportunity.OppertunitesStatus;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                OpportunityNewFragment opportunityNewFragment = new OpportunityNewFragment();
                return opportunityNewFragment;
            case 1:
                OpportunityQualifiedFragment opportunityQualifiedFragment = new OpportunityQualifiedFragment();
                return opportunityQualifiedFragment;
            case 2:
                OpportunityPropositionFragment opportunityPropositionFragment = new OpportunityPropositionFragment();
                return opportunityPropositionFragment;
            case 3:
                OpportunityWonFragment opportunityWonFragment = new OpportunityWonFragment();
                return opportunityWonFragment;
            case 4:
                OpportunityLostFragment opportunityLostFragment = new OpportunityLostFragment();
                return opportunityLostFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
