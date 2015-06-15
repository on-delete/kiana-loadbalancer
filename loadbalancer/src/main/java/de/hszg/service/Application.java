package de.hszg.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import de.hszg.service.heartbeat.SharedMemoryFeature;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andre on 05.05.2015.
 */
@ApplicationPath("/loadbalancer")
public class Application extends javax.ws.rs.core.Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(MacCountService.class);
        classes.add(GCEComputeNodeManager.class);
        classes.add(GCESchedulingService.class);
        classes.add(GCEHeartbeatService.class);
        classes.add(SharedMemoryFeature.class);
        classes.add(JacksonJsonProvider.class);
        classes.add(JobResponseTimeTrackerService.class);

        return classes;
    }
}
