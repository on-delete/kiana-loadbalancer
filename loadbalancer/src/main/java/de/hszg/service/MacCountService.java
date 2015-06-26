package de.hszg.service;

import de.hszg.model.MultipleAPRequest;
import de.hszg.model.scheduling.JobScheduleModel;
import de.hszg.service.heartbeat.HeartbeatModel;
import de.hszg.service.heartbeat.SharedMemory;
import de.hszg.service.util.Schedule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Andre on 22.04.2015.
 *
 * This class is for the rest services to receive a request to count all mac addresses for one or multiple AccessPoints and forward it to the "start" compute node.
 */
@Path("/MacCountService")
public class MacCountService {
    private static Logger log = LogManager.getRootLogger();
    @Inject
    private SharedMemory sharedMemory;

    /**
     * This function is to receive the request to count all mac addresses for multiple AccessPoint and forward it to the "start" compute node.
     * @param multipleAPRequest DTO for the request. Is marshalled automatically.
     * @return
     */
    @POST
    @Path("/getMacCountForAP")
    @Consumes("application/json")
    public Response getMacCountForAP(MultipleAPRequest multipleAPRequest){
        boolean gceFound = false;
        String response = "";
        try{
            while(!gceFound) {
                HeartbeatModel heartbeat = sharedMemory.getGCEWithLeastLoad();
                if (Schedule.checkGCEStatus(heartbeat)) {
                    multipleAPRequest.setGceCount(sharedMemory.getAllHeartbeats().size());

                    log.info("MacCount vor Scheduling: " + multipleAPRequest);

                    response = Schedule.startAggregateJob(multipleAPRequest, heartbeat.getIpAddress());
                    gceFound = true;
                }
                else{
                    sharedMemory.deleteHeartbeat(heartbeat);
                }
            }

            return Response.ok().entity(response).build();
        }
        catch (IndexOutOfBoundsException |IOException e){
            /*TODO
            In dem Fall wurde keine GCE aufgezeichnet, starten einer GCE?
             */
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
