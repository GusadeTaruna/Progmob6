package com.example.koperasiku;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TransaksiPagerAdapter extends FragmentStatePagerAdapter {
    public TransaksiPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ApproveFragment();
            case 1: return new RiwayatApprovalFragment();
        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
    @Override    public CharSequence getPageTitle(int position) {        switch (position){
        case 0: return "Approving";
        case 1: return "History";
        default: return null;
    }
    }
}
