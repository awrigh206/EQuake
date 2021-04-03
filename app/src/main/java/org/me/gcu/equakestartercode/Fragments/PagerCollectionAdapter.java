package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.ArrayList;
import java.util.List;

public class PagerCollectionAdapter extends FragmentStateAdapter {
    private Fragment fragment;
    private EarthQuakeModel model;
    public PagerCollectionAdapter(@NonNull Fragment fragment, EarthQuakeModel model) {
        super(fragment);
        this.model = model;
        this.fragment = fragment;
    }

//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
////        return fragment;
//        return new InnerDataFragment();
//    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch(position){
            case 0:
                Fragment fragment = new InnerDataFragment();
                Bundle args = new Bundle();
                args.putInt("arg", position + 1);
                fragment.setArguments(args);
                return fragment;
            case 1:
                Fragment map = new MapsFragment();
                Bundle mapArgs = new Bundle();
                ArrayList<EarthQuakeModel> list = new ArrayList<>();
                list.add(model);
                mapArgs.putParcelableArrayList("data", list);
                map.setArguments(mapArgs);
                return map;
        }

        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
