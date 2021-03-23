package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.ViewModels.ListViewModel;
import org.me.gcu.equakestartercode.ViewModels.ListViewModelFactory;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends Fragment implements View.OnClickListener,  SearchView.OnQueryTextListener{
    private ImageButton mapButton;
    private SearchView searchBox;
    private ListViewModel listViewModel;
    private Bundle bundle;
    private ListFragment listFragment;
    private ArrayList<EarthQuakeModel> dataList = new ArrayList<>();
    private Spinner searchTermSpinner;
    private String[] categories = {"Title","Description","Location","Latitude","Longitude"};
    public MainPage(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        listFragment = (ListFragment) getChildFragmentManager().findFragmentById(R.id.listFragment);
        mapButton = (ImageButton)view.findViewById(R.id.mapButton);
        searchBox = (SearchView)view.findViewById(R.id.searchBox);
        searchTermSpinner = (Spinner) view.findViewById(R.id.spinner);

        ArrayAdapter adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_dropdown_item_1line);
        adapter.addAll(categories);
        searchTermSpinner.setAdapter(adapter);

        mapButton.setOnClickListener(this);
        searchBox.setOnQueryTextListener(this);
        ListViewModelFactory listViewModelFactory = new ListViewModelFactory();
        listViewModel = (ListViewModel)new ViewModelProvider(this, listViewModelFactory).get(ListViewModel.class);
        listViewModel.setContext(getContext());
        bundle = new Bundle();
        List tempList = listViewModel.getData().getValue();
        //convert to ArrayList for the sake of Bundle
        dataList.addAll(tempList);
        bundle.putParcelableArrayList("data",dataList);
        return view;
    }

    private ArrayList<EarthQuakeModel> search (String searchTerm){
        //Switch everything to lower case to ensure fair comparison
        ArrayList<EarthQuakeModel> results = new ArrayList<>();
        for (EarthQuakeModel current : dataList){
            if(current.getLocation().toLowerCase().contains(searchTerm.toLowerCase())){
                results.add(current);
            }
        }
        return results;
    }

    @Override
    public void onClick(View view) {
        Navigation.findNavController(view).navigate(R.id.action_mainPage_to_mapsFragment,bundle);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        listFragment.updateListView(search(s));
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        listFragment.updateListView(search(s));
        return true;
    }
}