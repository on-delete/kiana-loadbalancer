package de.hszg.service;

import de.hszg.model.heartbeat.Heartbeat;
import de.hszg.service.heartbeat.HeartbeatModel;
import de.hszg.service.heartbeat.SharedMemory;
import de.hszg.service.util.Schedule;
import org.apache.logging.log4j.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Andre on 03.05.2015.
 *
 * This class is for the rest services so that all GCE can POST their heartbeat to let the loadbalancer know,
 * that they are still "alive".
 */

@Path("/HeartbeatService")
public class GCEHeartbeatService {

    private static Logger log = LogManager.getRootLogger();

    @Inject
    private SharedMemory sharedMemory;

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

        log.info("heartbeat " + heartbeat.getIpAddress() + " " + heartbeat.getLoad());

        return Response.ok().build();
    }

    @GET
    @Path("getAllHeartbeats")
    @Produces("application/json")
    public Response getAllHeartbeats(){
        List<HeartbeatModel> allHeartbeats = new ArrayList<>(sharedMemory.getAllHeartbeats());

        for(HeartbeatModel heartbeat : allHeartbeats){
            if (!Schedule.checkGCEStatus(heartbeat)){
                sharedMemory.deleteHeartbeat(heartbeat);
            }
        }

        allHeartbeats = new ArrayList<>(sharedMemory.getAllHeartbeats());

        Collections.sort(allHeartbeats);

        return Response.ok().entity(allHeartbeats).header("Access-Control-Allow-Origin","*").header("Access-Control-Allow-Methods", "GET").build();
    }
}
