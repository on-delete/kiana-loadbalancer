package de.hszg.service;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andre on 05.05.2015.
 */
@ApplicationPath("/")
public class Application extends ResourceConfig {
    public Application() {
        packages("de.hszg.service");
    }
}
