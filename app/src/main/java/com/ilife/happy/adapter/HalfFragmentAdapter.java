package com.ilife.happy.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class HalfFragmentAdapter extends FragmentStateAdapter {
    private List<Fragment> mFragments;

    public HalfFragmentAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments){
        super(fragmentActivity);
        this.mFragments = fragments;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
