package de.hszg.service;

import de.hszg.model.JobResponse;
import de.hszg.model.JobResponseList;
import de.hszg.service.persistence.JobResponseEntity;
import de.hszg.service.persistence.JobResponseService;
import de.hszg.service.persistence.JobResponseServiceImpl;
import de.hszg.service.util.Schedule;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Andre on 15.06.2015.
 */
@Path("/JobResponseTimeTracker")
public class JobResponseTimeTrackerService {

    private final JobResponseService jobResponseService;

    @Inject
    public JobResponseTimeTrackerService(JobResponseServiceImpl jobResponseService){
        this.jobResponseService = jobResponseService;
    }

    @POST
    @Path("/saveResponseTime")
    @Consumes("application/json")
    public Response saveResponseTime(JobResponse jobResponse){
        String gceName = Schedule.getGCEName(jobResponse.getIpAddress());

        jobResponseService.save(jobResponse, gceName);

        return Response.ok().build();
    }

    @GET
    @Path("responseTimeTracker")
    public Response responseTimeTracker(){
        List<JobResponseEntity> results = jobResponseService.getAll();

        if(results==null){
            return Response.serverError().build();
        }
        else{
            JobResponseList jobResponseList = new JobResponseList();

            for(JobResponseEntity entity : results){
                JobResponse jobResponse = new JobResponse();
                jobResponse.setJob(entity.getJob());
                jobResponse.setEndTime(entity.getEndTime());
                jobResponse.setStartTime(entity.getStartTime());
                jobResponse.setIpAddress(entity.getIpAddress());
                jobResponse.setGcename(entity.getgcename());

                jobResponseList.getResponseList().add(jobResponse);
            }

            return Response.ok().entity(jobResponseList.toString()).build();
        }
    }
}
