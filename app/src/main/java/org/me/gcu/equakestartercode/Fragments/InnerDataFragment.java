package org.me.gcu.equakestartercode.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.me.gcu.equakestartercode.R;

public class InnerDataFragment extends Fragment {
    private TextView text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inner_data, container, false);
        Bundle args = getArguments();
        text = ((TextView) view.findViewById(R.id.energyText));
        text.setText("Hello there");

        return view;
    }
}