package org.me.gcu.equakestartercode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Provides
    @Singleton
    public ExecutorService getExecutorService() {
        return executorService;
    }

//    @Provides
//    public MainPageViewModel provideViewModel(){
//        return new MainPageViewModel();
//    }
}
