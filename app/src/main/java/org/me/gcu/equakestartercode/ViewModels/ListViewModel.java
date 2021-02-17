package org.me.gcu.equakestartercode.ViewModels;

import androidx.lifecycle.ViewModel;

import org.me.gcu.equakestartercode.Data.Repository;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;

import java.util.List;

public class ListViewModel extends ViewModel {
    private Repository repository;

    public ListViewModel (Repository repository){
        this.repository = repository;
    }

    public List<EarthQuakeModel> getData(){
        return repository.getModels();
    }

}
