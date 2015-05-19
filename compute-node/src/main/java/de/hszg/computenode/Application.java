package de.hszg.computenode;

import javax.ws.rs.ApplicationPath;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import de.hszg.computenode.service.GCEComputeMacCountService;
import de.hszg.computenode.service.GCESchedulingAggregationService;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by daniel on 13.05.15.
 */
@ApplicationPath("/")
public class Application extends javax.ws.rs.core.Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(GCESchedulingAggregationService.class);
        classes.add(GCEComputeMacCountService.class);
        classes.add(JacksonJsonProvider.class);
        return classes;
    }
}
