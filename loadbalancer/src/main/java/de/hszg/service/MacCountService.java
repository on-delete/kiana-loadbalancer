package de.hszg.service;

import de.hszg.model.MultipleAPRequest;
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
        try{
            String ipAddress = sharedMemory.getGCEWithLeastLoad();

            /*Not fully implemented*/
            //Schedule.startJobComputing(multipleAPRequest, ipAddress);

            return Response.ok().entity("test: " + ipAddress).build();
        }
        catch (IndexOutOfBoundsException /*|IOException*/ e){
            /*TODO
            In dem Fall wurde keine GCE aufgezeichnet, starten einer GCE?
             */
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
