package org.me.gcu.equakestartercode.Fragments;

import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import org.me.gcu.equakestartercode.Data.Repository;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.ViewModels.ListViewModel;
import org.me.gcu.equakestartercode.ViewModels.ListViewModelFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private ListViewModel viewModel;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment
     */
    public ListFragment() {
    }

    public static ListFragment newInstance(int columnCount) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    public void updateListView(List<EarthQuakeModel> listItems, Method getMethod){
        recyclerView.removeAllViews();
        recyclerView.setAdapter(new MyItemRecyclerViewAdapter(listItems,getMethod));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_list, container, false);
        ListViewModelFactory listViewModelFactory = new ListViewModelFactory(getContext());
        viewModel = new ViewModelProvider(requireActivity(), listViewModelFactory).get(ListViewModel.class);

        viewModel.getData().observe(getViewLifecycleOwner(), models -> {
            try {
                updateListView(models, EarthQuakeModel.class.getMethod("getLocationWithMagnitude",null));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        });

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            try {
                recyclerView.setAdapter(new MyItemRecyclerViewAdapter(viewModel.getData().getValue(),EarthQuakeModel.class.getMethod("getLocation",null)));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            this.recyclerView = recyclerView;
        }
        return view;
    }

}