package org.me.gcu.equakestartercode.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.Models.EarthQuakeModelComparator;
import org.me.gcu.equakestartercode.Models.EarthQuakeModelReverseComparator;
import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.ViewModels.ListViewModel;
import org.me.gcu.equakestartercode.ViewModels.ListViewModelFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainPage extends Fragment implements View.OnClickListener,  SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    private ImageButton mapButton;
    private SearchView searchBox;
    private ListViewModel listViewModel;
    private ListFragment listFragment;
    private ArrayList<EarthQuakeModel> dataList = new ArrayList<>();
    private Spinner searchTermSpinner;
    private String searchText;
    private String[] categories = {"Title","Description","Location","Latitude","Longitude", "Magnitude"};
    private String searchCategory = "Title";
    private Switch orderSwitch;
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
        orderSwitch = (Switch) view.findViewById(R.id.orderSwitch);

        ArrayAdapter adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_dropdown_item_1line);
        adapter.addAll(categories);
        searchTermSpinner.setAdapter(adapter);
        searchTermSpinner.setOnItemSelectedListener(this);
        mapButton.setOnClickListener(this);
        searchBox.setOnQueryTextListener(this);
        orderSwitch.setOnCheckedChangeListener(this);

        ListViewModelFactory listViewModelFactory = new ListViewModelFactory(getContext());
        listViewModel = new ViewModelProvider(requireActivity(), listViewModelFactory).get(ListViewModel.class);
        listViewModel.getData().observe(getViewLifecycleOwner(), models -> {
            // Use this observer to update the user interface when the values inside the repository change
            dataList.clear();
            dataList.addAll(models);
        });
        return view;
    }

    private ArrayList<EarthQuakeModel> search (String searchTerm){
        //Switch everything to lower case to ensure fair comparison
        ArrayList<EarthQuakeModel> results = new ArrayList<>();
        for (EarthQuakeModel current : dataList){
            Method getMethod = getCategoryMethod(searchCategory);
            try {
                if(getMethod.invoke(current).toString().toLowerCase().contains(searchTerm.toLowerCase())){
                    results.add(current);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    private Method getCategoryMethod(String category )  {
        category = category.toLowerCase();
        try{
            switch(category){
                case "title":
                    return EarthQuakeModel.class.getMethod("getTitle",null);
                case "description":
                    return EarthQuakeModel.class.getMethod("getDescription",null);
                case "location":
                    return EarthQuakeModel.class.getMethod("getLocation",null);
                case "magnitude":
                    return EarthQuakeModel.class.getMethod("getMagnitude", null);
                default:
                    return EarthQuakeModel.class.getMethod("getLocation",null);
            }
        }
        catch(NoSuchMethodException noMethod){
            noMethod.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        Bundle mapBundle = new Bundle();
        ArrayList listToSendToMap;
        if(searchText != null){
            if(!searchText.isEmpty()){
                listToSendToMap = search(searchText);
            }
            else{
                listToSendToMap = dataList;
            }
        }
        else{
            listToSendToMap = dataList;
        }
        mapBundle.putParcelableArrayList("data",listToSendToMap);
        Navigation.findNavController(view).navigate(R.id.action_mainPage_to_mapsFragment,mapBundle);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        listFragment.updateListView(search(s),getCategoryMethod(searchCategory));
        this.searchText = s;
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        listFragment.updateListView(search(s),getCategoryMethod(searchCategory));
        this.searchText = s;
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Log.e("Spinner", "You have selected: " + categories[position]);
        this.searchCategory = categories[position];
        listFragment.updateListView(search(""),getCategoryMethod(searchCategory));

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            dataList.sort(new EarthQuakeModelComparator());
        }
        else{
            dataList.sort(new EarthQuakeModelReverseComparator());
        }
        listFragment.updateListView(dataList, getCategoryMethod(searchCategory));
    }
}