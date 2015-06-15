package de.hszg.service.persistence;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by Andre on 15.06.2015.
 */
public class JobResponseConfig extends ResourceConfig{

    public JobResponseConfig(){
        register(new JobResponseBinder());

        packages(true, "de.hszg.service");
    }
}
