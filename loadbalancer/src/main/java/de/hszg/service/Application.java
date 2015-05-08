package de.hszg.service;

import de.hszg.service.heartbeat.SharedMemory;
import de.hszg.service.heartbeat.SharedMemoryFeature;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andre on 05.05.2015.
 */
@ApplicationPath("/")
public class Application extends javax.ws.rs.core.Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(GCEHeartbeatService.class);
        classes.add(SharedMemoryFeature.class);
        return classes;
    }
}