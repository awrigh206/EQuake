package org.me.gcu.equakestartercode.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import org.me.gcu.equakestartercode.ViewModels.ListViewModel;
import org.me.gcu.equakestartercode.ViewModels.ListViewModelFactory;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static java.util.Calendar.*;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class DatePickerFragment extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener {
    private boolean isStart;
    private MutableLiveData<Calendar> date;
    public DatePickerFragment(){
        this.date = new MutableLiveData<Calendar>();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Log.e("date", day+"/"+month+"/"+year);
        Calendar date = Calendar.getInstance();
        date.set(year,month,day);
        this.date.postValue(date);
    }

    public MutableLiveData<Calendar> getDate(){
        return this.date;
    }
}
