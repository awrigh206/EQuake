package org.me.gcu.equakestartercode.ViewModels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.me.gcu.equakestartercode.Data.Repository;
import org.me.gcu.equakestartercode.Data.ResourcePool;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

public class DataViewModelFactory implements ViewModelProvider.Factory{
    @Inject
    ResourcePool resourcePool;
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(ResourcePool.class).newInstance(resourcePool);
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
