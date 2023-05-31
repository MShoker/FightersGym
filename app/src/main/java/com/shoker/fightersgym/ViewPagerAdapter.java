package com.shoker.fightersgym;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }
        @NonNull @Override public Fragment createFragment(int position) {
            return mFragmentList.get(position);
        }
        @Override public int getItemCount() {
            return mFragmentList.size();
        }

    public void addFrag(Fragment f,String s) {
        mFragmentList.add(f);
    }
}
