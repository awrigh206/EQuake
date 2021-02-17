package org.me.gcu.equakestartercode.ViewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import org.me.gcu.equakestartercode.DaggerIApplicationGraph;
import org.me.gcu.equakestartercode.Data.Repository;
import org.me.gcu.equakestartercode.IApplicationGraph;
import java.lang.reflect.InvocationTargetException;

public class ViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            // Create an instance of the application graph
            IApplicationGraph applicationGraph = DaggerIApplicationGraph.create();
            // Grab an instance of UserRepository from the application graph
            Repository repo = applicationGraph.repository();
            return modelClass.getConstructor(Repository.class).newInstance(repo);
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
