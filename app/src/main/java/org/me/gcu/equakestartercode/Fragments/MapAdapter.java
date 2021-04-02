package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.ArrayList;

public class MapAdapter extends FragmentStateAdapter {
    private EarthQuakeModel model;
    public MapAdapter(@NonNull Fragment fragment, EarthQuakeModel model) {
        super(fragment);
        this.model = model;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new MapsFragment();
        Bundle args = new Bundle();
        ArrayList<EarthQuakeModel> list = new ArrayList<>();
        list.add(model);
        args.putParcelableArrayList("data",list);
        // Our object is just an integer :-P
        args.putInt("data", position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
