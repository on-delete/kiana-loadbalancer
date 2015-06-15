package de.hszg.service.persistence;

import de.hszg.service.heartbeat.SharedMemoryBinder;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

/**
 * Created by Andre on 15.06.2015.
 */
public class JobResponseFeature implements Feature {
    @Override
    public boolean configure(FeatureContext context) {
        context.register(new JobResponseBinder());
        return true;
    }
}
