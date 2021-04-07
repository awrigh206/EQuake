package org.me.gcu.equakestartercode.ViewModels;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import org.me.gcu.equakestartercode.Data.Repository;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.Calendar;
import java.util.List;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class ListViewModel extends ViewModel {
    private Repository repository;
    private Context context;
    private Calendar startDate;
    private Calendar endDate;

    public ListViewModel (Repository repository, Context context){
        this.repository = repository;
        this.context = context;
        repository.setContext(context,isOnline());
    }
    public MutableLiveData<List<EarthQuakeModel>> getData(){
        return repository.getLiveData(isOnline());
    }

    public Context getContext() {
        return context;
    }

    public Repository getRepository(){return repository;}

    public void setContext(Context context) {
        this.context = context;
        repository.setContext(context,isOnline());
    }

    private boolean isOnline() {
        ConnectivityManager manager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public boolean isDateSet(){
        if(startDate != null && endDate != null){
            return true;
        }
        else{
            return false;
        }
    }
}
