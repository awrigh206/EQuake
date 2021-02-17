package org.me.gcu.equakestartercode;

import androidx.lifecycle.ViewModel;

import org.me.gcu.equakestartercode.Data.Repository;
import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import java.util.List;

public class MainPageViewModel extends ViewModel {
    private final Repository repository;
    public MainPageViewModel(Repository repository){
        this.repository = repository;
    }

    public List<EarthQuakeModel> getData(){
        return repository.getModels();
    }
}
