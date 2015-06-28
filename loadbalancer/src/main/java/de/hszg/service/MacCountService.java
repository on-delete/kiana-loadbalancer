package de.hszg.service;

import de.hszg.model.MultipleAPRequest;
import de.hszg.service.heartbeat.HeartbeatModel;
import de.hszg.service.heartbeat.SharedMemory;
import de.hszg.service.util.Schedule;

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
        HeartbeatModel heartbeat = sharedMemory.getGCEWithLeastLoad();

        multipleAPRequest.setGceCount(sharedMemory.getAllHeartbeats().size());

        try {
            sharedMemory.increaseNumberJobs(heartbeat);
            String response = Schedule.startAggregateJob(multipleAPRequest, heartbeat.getIpAddress());

            try{
                int responseInt = Integer.parseInt(response);
                return Response.ok().entity(response).build();
            }
            catch (NumberFormatException e){
                return Response.serverError().build();
            }
        } catch (IndexOutOfBoundsException |IOException e){
            return Response.serverError().build();
        }
    }
}
