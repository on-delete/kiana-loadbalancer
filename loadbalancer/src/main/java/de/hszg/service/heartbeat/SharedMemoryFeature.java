package de.hszg.service.heartbeat;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

/**
 * Created by Andre on 08.05.2015.
 */
public class SharedMemoryFeature implements Feature{

    @Override
    public boolean configure(FeatureContext context) {
        context.register(new SharedMemoryBinder());
        return true;
    }
}
