package org.me.gcu.equakestartercode;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class MainActivity extends AppCompatActivity
{
    private TextView rawDataDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}