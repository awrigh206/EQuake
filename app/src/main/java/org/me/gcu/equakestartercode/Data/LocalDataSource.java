package org.me.gcu.equakestartercode.Data;

import javax.inject.Inject;


public class LocalDataSource {
    private boolean hasData;
    @Inject
    public LocalDataSource(){
        
    }

    public boolean hasData() {
        return hasData;
    }
}
