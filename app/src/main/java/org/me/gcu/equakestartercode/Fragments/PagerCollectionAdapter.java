package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerCollectionAdapter extends FragmentStateAdapter {
    public PagerCollectionAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new InnerDataFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt("arg", position + 1);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}