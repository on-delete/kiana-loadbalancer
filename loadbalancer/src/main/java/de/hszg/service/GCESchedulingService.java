package de.hszg.service;

import de.hszg.model.scheduling.Job;
import de.hszg.model.scheduling.JobList;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.xml.ws.Response;

/**
 * Created by Andre on 03.05.2015.
 *
 * This class is for the rest services so that a GCE can POST one or multiple jobs that are have to be computed.
 * The jobs are then distributed to the compute nodes that had least load of all nodes.
 */

@Path("/SchedulingService")
public class GCESchedulingService {

    /**
     * Receives one job that has to be scheduled. Could also be used to reschedule the job, if one GCE died.
     * Forward it to compute node that hast least load of all.
     * @param job
     * @return
     */
    @POST
    @Path("/scheduleJob")
    @Consumes("application/json")
    public Response scheduleJob(Job job){
        return null;
    }

    /**
     * Receives a list of jobs that have to be scheduled.
     * Forward it to compute nodes that have least load of all.
     * @param jobList
     * @return
     */
    @POST
    @Path("/scheduleJobList")
    @Consumes("application/json")
    public Response scheduleJob(JobList jobList){
        return null;
    }
}
