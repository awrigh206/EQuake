package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import org.me.gcu.equakestartercode.R;
import org.me.gcu.equakestartercode.ViewModels.ListViewModel;
import org.me.gcu.equakestartercode.ViewModels.ListViewModelFactory;

public class MainPage extends Fragment implements View.OnClickListener {
    ImageButton mapButton;

    public MainPage(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        mapButton = (ImageButton)view.findViewById(R.id.mapButton);
        mapButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        Navigation.findNavController(view).navigate(R.id.action_mainPage_to_mapsFragment);
    }
}