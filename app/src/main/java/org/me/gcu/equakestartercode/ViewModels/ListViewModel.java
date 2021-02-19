package org.me.gcu.equakestartercode.ViewModels;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.ViewModel;

import org.me.gcu.equakestartercode.Data.Repository;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.List;

public class ListViewModel extends ViewModel {
    private Repository repository;
    private Context context;

    public ListViewModel (Repository repository){
        this.repository = repository;
    }
    public List<EarthQuakeModel> getData(){
        return repository.getModels(isOnline());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        repository.setContext(context);
    }

    private boolean isOnline() {
        ConnectivityManager manager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
