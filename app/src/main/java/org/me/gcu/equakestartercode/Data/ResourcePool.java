package org.me.gcu.equakestartercode.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Inject;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class ResourcePool {
    private final ExecutorService executorService;

    @Inject
    public ResourcePool(){
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
