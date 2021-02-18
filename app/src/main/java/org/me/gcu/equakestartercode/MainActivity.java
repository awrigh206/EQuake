package org.me.gcu.equakestartercode;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.me.gcu.equakestartercode.Data.UpdateDataService;
import org.me.gcu.equakestartercode.ViewModels.MainPageViewModel;
import org.me.gcu.equakestartercode.ViewModels.MainViewModelFactory;

public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private TextView rawDataDisplay;
    private Button startButton;
    public MainPageViewModel viewModel;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set up the raw links to the graphical components
//        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
//        startButton = (Button)findViewById(R.id.startButton);
//        startButton.setOnClickListener(this);
        JobScheduler jobScheduler = (JobScheduler)getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName component = new ComponentName(this, UpdateDataService.class);
        JobInfo jobInfoObj = new JobInfo.Builder(1, component)
                .setPeriodic(100000).setRequiresBatteryNotLow(true).build();

    }


    public void onClick(View aview)
    {
//        Log.e("Activity Class", viewModel.getData().toString());
    }
}