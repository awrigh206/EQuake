package org.me.gcu.equakestartercode.ViewModels;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import org.me.gcu.equakestartercode.DaggerIApplicationGraph;
import org.me.gcu.equakestartercode.Data.Repository;
import org.me.gcu.equakestartercode.IApplicationGraph;

import java.lang.reflect.InvocationTargetException;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class ListViewModelFactory implements ViewModelProvider.Factory{
    private Context context;
    public ListViewModelFactory(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
//             Create an instance of the application graph
            IApplicationGraph applicationGraph = DaggerIApplicationGraph.create();
            // Grab an instance of UserRepository from the application graph
            Repository repo = applicationGraph.repository();
            return modelClass.getConstructor(Repository.class,Context.class).newInstance(repo,context);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
