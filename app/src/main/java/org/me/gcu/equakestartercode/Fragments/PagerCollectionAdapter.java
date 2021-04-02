package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class PagerCollectionAdapter extends FragmentStateAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    public PagerCollectionAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new InnerDataFragment();
        Bundle args = new Bundle();
        args.putInt("arg", position + 1);
        fragment.setArguments(args);
        return fragment;

    }

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
