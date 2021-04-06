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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import org.me.gcu.equakestartercode.Data.DateHelper;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.me.gcu.equakestartercode.Models.Comparators.EarthQuakeModelComparator;
import org.me.gcu.equakestartercode.Models.Comparators.EarthQuakeModelDeepestComparator;
import org.me.gcu.equakestartercode.Models.Comparators.EarthQuakeModelReverseComparator;
import org.me.gcu.equakestartercode.Models.Comparators.EarthQuakeModelShallowestComparator;
import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.ViewModels.ListViewModel;
import org.me.gcu.equakestartercode.ViewModels.ListViewModelFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

public class MainPage extends Fragment implements View.OnClickListener,  SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    private ImageButton mapButton;
    private ImageButton startDateButton;
    private ImageButton endDateButton;
    private SearchView searchBox;
    private ListViewModel listViewModel;
    private ListFragment listFragment;
    private ArrayList<EarthQuakeModel> dataList = new ArrayList<>();
    private Spinner searchTermSpinner;
    private String searchText;
    private String[] categories = {"Location&Magnitude","Description","Location","Latitude","Longitude", "Magnitude", "Depth"};
    private String searchCategory = "Location&Magnitude";
    private Switch orderSwitch;
    public MainPage(){}

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        startDateButton = (ImageButton) view.findViewById(R.id.startDateButton);
        endDateButton = (ImageButton) view.findViewById(R.id.endDateButton);

        ArrayAdapter adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_dropdown_item_1line);
        adapter.addAll(categories);
        searchTermSpinner.setAdapter(adapter);
        searchTermSpinner.setOnItemSelectedListener(this);
        mapButton.setOnClickListener(this);
        searchBox.setOnQueryTextListener(this);
        orderSwitch.setOnCheckedChangeListener(this);

        startDateButton.setOnClickListener(view1 -> {
            DatePickerFragment dialog = new DatePickerFragment();
            FragmentManager manager = getChildFragmentManager();
            dialog.show(getChildFragmentManager(), "startDatePicker");
            dialog.getDate().observe(getViewLifecycleOwner(), date -> {
                listViewModel.setStartDate(date);
            });;
        });

        endDateButton.setOnClickListener(view1 -> {
            DatePickerFragment dialog = new DatePickerFragment();
            dialog.show(getChildFragmentManager(), "endDatePicker");
            dialog.getDate().observe(getViewLifecycleOwner(), date -> {
                listViewModel.setEndDate(date);
                listFragment.updateListView(searchDates(listViewModel.getStartDate(),listViewModel.getEndDate()),getCategoryMethod(searchCategory));
            });;
        });

        ListViewModelFactory listViewModelFactory = new ListViewModelFactory(getContext());
        listViewModel = new ViewModelProvider(requireActivity(), listViewModelFactory).get(ListViewModel.class);
        listViewModel.getData().observe(getViewLifecycleOwner(), models -> {
            // Use this observer to update the user interface when the values inside the repository change
            dataList.clear();
            dataList.addAll(models);
            dataList.sort(new EarthQuakeModelReverseComparator());
        });

        if(listViewModel.isDateSet()){
            listFragment.updateListView(searchDates(listViewModel.getStartDate(),listViewModel.getEndDate()),getCategoryMethod(searchCategory));
        }
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

    private ArrayList<EarthQuakeModel> searchDates (Calendar startDate, Calendar endDate){
        DateHelper dateHelper = new DateHelper();
        ArrayList<EarthQuakeModel> results = new ArrayList<>();
        for (EarthQuakeModel current : dataList){
            Calendar quakeDate = dateHelper.parseStandardDate(current.getDateString());
            if(quakeDate.before(endDate)){
                if(quakeDate.after(startDate)){
                    Log.e("date search", "adding");
                    results.add(current);
                }
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
                case"depth":
                    return EarthQuakeModel.class.getMethod("getDepth",null);
                case"Location&Magnitude":
                    return EarthQuakeModel.class.getMethod("getLocationWithMagnitude", null);
                default:
                    return EarthQuakeModel.class.getMethod("getLocationWithMagnitude",null);
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
        this.searchCategory = categories[position];
        listFragment.updateListView(search(""),getCategoryMethod(searchCategory));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(searchCategory.equalsIgnoreCase("depth")){
            if(b){
                dataList.sort(new EarthQuakeModelDeepestComparator());
            }
            else{
                dataList.sort(new EarthQuakeModelShallowestComparator());
            }
        }
        else{
            if(b){
                dataList.sort(new EarthQuakeModelComparator());
            }
            else{
                dataList.sort(new EarthQuakeModelReverseComparator());
            }
        }
        listFragment.updateListView(dataList, getCategoryMethod(searchCategory));
    }
}