package com.example.tom.mineclicker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitlelList = new ArrayList<>();


    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void AddFragment (Fragment fragment, String title){
        fragmentList.add(fragment);
        fragmentTitlelList.add(title);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
       return fragmentList.size();
    }
}
