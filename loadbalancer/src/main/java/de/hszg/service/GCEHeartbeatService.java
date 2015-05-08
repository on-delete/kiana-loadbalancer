package de.hszg.service;

import de.hszg.model.heartbeat.Heartbeat;
import de.hszg.service.heartbeat.SharedMemory;
import org.glassfish.jersey.process.internal.RequestScoped;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Andre on 03.05.2015.
 *
 * This class is for the rest services so that all GCE can POST their heartbeat to let the loadbalancer know,
 * that they are still "alive".
 */

@Path("/HeartbeatService")
public class GCEHeartbeatService {

    @Inject
    private SharedMemory sharedMemory;

    /**
     * All compute nodes can post their heartbeat to this function to register them to the loadbalancer.
     * @param heartbeat
     * @return
     */
    @POST
    @Path("/postHeartbeat")
    @Consumes("application/json")
    public Response postHeartbeat(Heartbeat heartbeat){
        sharedMemory.storeToMemory(heartbeat);

        return Response.ok().build();
    }

    /**
     * All compute nodes can put their heartbeat to this function to update the last known heartbeat.
     * @param heartbeat
     * @return
     */
    @PUT
    @Path("/updateHeartbeat")
    @Consumes("application/json")
    public Response updateHeartbeat(Heartbeat heartbeat){
        sharedMemory.updateMemory(heartbeat);

        return Response.ok().build();
    }
}
