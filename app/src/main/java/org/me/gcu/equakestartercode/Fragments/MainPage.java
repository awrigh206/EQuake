package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.ViewModels.ListViewModel;
import org.me.gcu.equakestartercode.ViewModels.ListViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends Fragment implements View.OnClickListener,  SearchView.OnQueryTextListener{
    ImageButton mapButton;
    SearchView searchBox;
    ListViewModel listViewModel;
    Bundle bundle;

    public MainPage(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        mapButton = (ImageButton)view.findViewById(R.id.mapButton);
        searchBox = (SearchView)view.findViewById(R.id.searchBox);
        mapButton.setOnClickListener(this);
        ListViewModelFactory listViewModelFactory = new ListViewModelFactory();
        listViewModel = (ListViewModel)new ViewModelProvider(this, listViewModelFactory).get(ListViewModel.class);
        listViewModel.setContext(getContext());
        bundle = new Bundle();
        List tempList = listViewModel.getData().getValue();
        //convert to ArrayList for the sake of Bundle
        ArrayList<EarthQuakeModel> dataList = new ArrayList<>();
        dataList.addAll(tempList);
        bundle.putParcelableArrayList("data",dataList);
        return view;
    }

    @Override
    public void onClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_mainPage_to_mapsFragment,bundle);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}