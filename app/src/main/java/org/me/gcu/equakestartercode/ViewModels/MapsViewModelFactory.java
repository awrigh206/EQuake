package org.me.gcu.equakestartercode.ViewModels;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.me.gcu.equakestartercode.Data.ResourcePool;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import static android.content.Context.LOCATION_SERVICE;

public class MapsViewModelFactory implements ViewModelProvider.Factory{
    private Context context;

    public MapsViewModelFactory(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(Context.class).newInstance(context);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

}
