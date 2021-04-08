package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;

/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class DataFragment extends Fragment {
    private EarthQuakeModel earthQuakeModel;
    private TabLayout tabLayout;
    private ViewPager2 pager;
    private TextView titleText;

    public DataFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        earthQuakeModel = getArguments().getParcelable("data");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        pager = (ViewPager2) view.findViewById(R.id.pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        titleText = (TextView) view.findViewById(R.id.dateText);
        titleText.setText(earthQuakeModel.getTitle());

        PagerCollectionAdapter adapter = new PagerCollectionAdapter(this,earthQuakeModel);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.e("Page", String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        pager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, pager,
                (tab, position) -> {
                    if(position == 1){
                        tab.setText("Quake & You");
                    }
                    else if (position == 0){
                        tab.setText("Details");
                    }
                    else if (position ==2){
                        tab.setText("Comparison");
                    }
                }
        ).attach();
        return view;
    }
}