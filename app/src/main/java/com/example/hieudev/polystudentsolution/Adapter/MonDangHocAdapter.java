package com.example.hieudev.polystudentsolution.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.hieudev.polystudentsolution.Fragment.DeadlineBaiTapFragment;
import com.example.hieudev.polystudentsolution.Fragment.DeadlineFragment;
import com.example.hieudev.polystudentsolution.Fragment.DeadlineGhiNhoFragment;
import com.example.hieudev.polystudentsolution.Fragment.HomeFragment;

public class MonDangHocAdapter extends FragmentStatePagerAdapter {
    public MonDangHocAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                //Fragement for Stories Tab
                return new DeadlineBaiTapFragment();
            case 1:
                //Fragment for Legends Tab
                return new DeadlineGhiNhoFragment();
        }
        return null;

    }

    public CharSequence getPageTitle(int position) {

        if (position == 0)
        {
            return "Bài tập";
        }
        if (position == 1)
        {
            return "Ghi nhớ";
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2; //No of Tabs
    }
}
