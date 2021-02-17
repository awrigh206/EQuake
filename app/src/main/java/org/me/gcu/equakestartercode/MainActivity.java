package org.me.gcu.equakestartercode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.me.gcu.equakestartercode.ViewModels.MainPageViewModel;
import org.me.gcu.equakestartercode.ViewModels.MainViewModelFactory;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView rawDataDisplay;
    private Button startButton;
    public MainPageViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the raw links to the graphical components
//        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
//        startButton = (Button)findViewById(R.id.startButton);
//        startButton.setOnClickListener(this);
        MainViewModelFactory mainViewModelFactory = new MainViewModelFactory();
        viewModel = new ViewModelProvider(this, mainViewModelFactory).get(MainPageViewModel.class);
    }


    public void onClick(View aview)
    {
//        Log.e("Activity Class", viewModel.getData().toString());
    }
}