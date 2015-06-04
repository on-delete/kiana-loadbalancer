package de.hszg.service;

import de.hszg.model.scheduling.Job;
import de.hszg.model.scheduling.JobList;
import de.hszg.model.scheduling.JobScheduleModel;
import de.hszg.service.heartbeat.HeartbeatModel;
import de.hszg.service.heartbeat.SharedMemory;
import de.hszg.service.util.Schedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Andre on 03.05.2015.
 *
 * This class is for the rest services so that a GCE can POST one or multiple jobs that are have to be computed.
 * The jobs are then distributed to the compute nodes that had least load of all nodes.
 */

@Path("/SchedulingService")
public class GCESchedulingService {

    Logger log = LoggerFactory.getLogger(GCESchedulingService.class);

    @Inject
    private SharedMemory sharedMemory;

    /**
     * Receives a list of jobs that have to be scheduled.
     * Forward it to compute nodes that have least load of all.
     * @param jobList
     * @return
     */
    @POST
    @Path("/scheduleJobList")
    @Consumes("application/json")
    public Response scheduleJob(JobList jobList, @Context HttpServletRequest httpRequest){
        boolean gceFound = false;
        try {
            String requestIpAddress = Schedule.extractIpAddress(httpRequest.getRequestURL().toString());

            for(Job job: jobList.getJobList()){
                while(!gceFound) {
                    HeartbeatModel heartbeat = sharedMemory.getGCEWithLeastLoad();
                    if (Schedule.checkGCEStatus(heartbeat)) {
                        JobScheduleModel jobScheduleModel = new JobScheduleModel(job, requestIpAddress);

                        log.info("IP Adresse GCE: " + heartbeat.getIpAddress());

                        //not fully implemented
                        Schedule.startJobComputing(jobScheduleModel, heartbeat.getIpAddress());
                        gceFound = true;
                    }
                    else{
                        sharedMemory.deleteHeartbeat(heartbeat);
                    }
                }
            }
            return Response.ok().build();
        }
        catch (IOException e){
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
