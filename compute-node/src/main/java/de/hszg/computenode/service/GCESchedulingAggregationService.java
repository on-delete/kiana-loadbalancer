package de.hszg.computenode.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import de.hszg.model.MultipleAPRequest;
import de.hszg.model.scheduling.Job;
import de.hszg.model.scheduling.JobList;

/**
 * Created by Tobias on 19.05.2015.
 */
@Path("/GCESchedulingAggregationService")
public class GCESchedulingAggregationService {

	 private static String LOADBALANCER_IP = "104.197.107.205:8080";
	 private static HashMap<String, JobList> scheduleJobs = new HashMap<String, JobList>();
	 private static HashMap<String, HashMap<Integer, Integer>> jobResponses = new HashMap<String, HashMap<Integer,Integer>>();
	
	@POST
    @Path("/scheduleJob")
    @Consumes("application/json")
	@Produces("application/json")
	public Response scheduleJob(MultipleAPRequest multipleAPRequest){
		
		
		//TODO Test für die Kommunikation zwischen Loadbalancer und Compute Node durch eigentliche Implementation ersetzen
		JobList jobList = new JobList();
		String computeJobId = ("test" + System.currentTimeMillis());
		for(int i = 0; i < 4; i++){
			Job job = new Job();
			job.setJobId(i);
			job.setComputeJobId(computeJobId);
			jobList.getJobList().add(job);
		}
		scheduleJobs.put(computeJobId, jobList);
		jobResponses.put(computeJobId, new HashMap<Integer, Integer>());
        try {
			SerializableEntity input = new SerializableEntity(jobList, false);
			input.setContentType("application/json");

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://" + LOADBALANCER_IP + "/SchedulingService/scheduleJobList");
			httpPost.setEntity(input);
			CloseableHttpResponse response = null;

			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			response.close();
			httpclient.close();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		}
		
        while(true){
        	if(scheduleJobs.get(computeJobId).getJobList().size() > jobResponses.get(computeJobId).size()){
        		try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}else{
        		break;
        	}
        }
        
        scheduleJobs.remove(computeJobId);
        jobResponses.remove(computeJobId);
        
		return Response.ok().build();
	}
	
	@POST
	@Path("/aggregateJob")
	@Consumes("application/json")
	public Response aggregateJob(JobResponse jobResponse){
		if(jobResponses.containsKey(jobResponse.getComputeJobId())){
			if(!(jobResponses.get(jobResponse.getComputeJobId()).containsKey(jobResponse.getJobId()))){
				jobResponses.get(jobResponse.getComputeJobId()).put(jobResponse.getJobId(), jobResponse.getMacCount());
			}
		}
		return Response.ok().build();
	}
}
