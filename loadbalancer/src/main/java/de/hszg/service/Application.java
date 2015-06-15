package de.hszg.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import de.hszg.service.heartbeat.SharedMemoryFeature;
import de.hszg.service.persistence.JobResponseFeature;

import javax.ws.rs.ApplicationPath;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        classes.add(JobResponseFeature.class);

        return classes;
    }
}
