package org.me.gcu.equakestartercode.ViewModels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import org.me.gcu.equakestartercode.Data.ResourcePool;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class DataViewModelFactory implements ViewModelProvider.Factory{
    private Context context;
    @Inject
    ResourcePool resourcePool;
    public DataViewModelFactory(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(ResourcePool.class,Context.class).newInstance(resourcePool,context);
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
