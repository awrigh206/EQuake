package org.me.gcu.equakestartercode.ViewModels;

import androidx.lifecycle.ViewModel;

import org.me.gcu.equakestartercode.Data.ResourcePool;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import javax.inject.Inject;

public class DataViewModel extends ViewModel {
    private ResourcePool resourcePool;
    @Inject
    public DataViewModel(ResourcePool resourcePool){
        this.resourcePool = resourcePool;
    }

    private void findLocation(){

    }

}
