package de.hszg.service;

import de.hszg.service.heartbeat.SharedMemory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Andre on 05.05.2015.
 */
@ApplicationPath("/")
public class Application extends javax.ws.rs.core.Application {

}
