package de.hszg.service;

import de.hszg.model.JobResponse;
import de.hszg.service.persistence.JobResponseServiceImpl;
import de.hszg.service.util.Schedule;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Andre on 15.06.2015.
 */
@Path("/JobResponseTimeTracker")
public class JobResponseTimeTrackerService {

    private final JobResponseServiceImpl jobResponseService;

    @Inject
    public JobResponseTimeTrackerService(JobResponseServiceImpl jobResponseService){
        this.jobResponseService = jobResponseService;
    }

    @POST
    @Path("/saveResponseTime")
    @Consumes("application/json")
    public Response saveResponseTime(/*JobResponse jobResponse*/){
        //String gceName = Schedule.getGCEName(jobResponse.getIpAddress());

        String gceName = "test";
        JobResponse jobResponse = new JobResponse();
        jobResponse.setEndTime("dfs");
        jobResponse.setStartTime("sdgd");
        jobResponse.setIpAddress("fdsgs");
        jobResponse.setJob("gdgdf");

        jobResponseService.save(jobResponse, gceName);

        return Response.ok().build();
    }

    @GET
    @Path("responseTimeTracker")
    public Response responseTimeTracker(){

        //jobResponseService.getAll();

        return Response.ok().build();
    }
}
