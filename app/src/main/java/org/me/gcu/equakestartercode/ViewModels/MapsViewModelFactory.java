package org.me.gcu.equakestartercode.ViewModels;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.lang.reflect.InvocationTargetException;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
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
