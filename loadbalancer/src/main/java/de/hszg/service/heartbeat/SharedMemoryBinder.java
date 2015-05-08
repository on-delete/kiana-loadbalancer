package de.hszg.service.heartbeat;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * Created by Andre on 08.05.2015.
 */
public class SharedMemoryBinder extends AbstractBinder{
    @Override
    protected void configure() {
        bind(new SharedMemory()).to(SharedMemory.class);
    }
}
