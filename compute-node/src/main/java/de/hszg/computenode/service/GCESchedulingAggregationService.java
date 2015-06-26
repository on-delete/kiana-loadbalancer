package de.hszg.computenode.service;

import java.io.IOException;
import java.util.UUID;
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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.core.util.SystemClock;

import de.hszg.computenode.service.util.ComputeJobList;
import de.hszg.computenode.service.util.JobResponse;
import de.hszg.model.MultipleAPRequest;
import de.hszg.model.scheduling.Job;
import de.hszg.model.scheduling.JobList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Tobias on 19.05.2015.
 */
@Path("/GCESchedulingAggregationService")
public class GCESchedulingAggregationService {

	private static String LOADBALANCER_IP = "104.197.107.205:8080";
	
	@POST
    @Path("/scheduleJob")
    @Consumes("application/json")
	@Produces("application/json")
	public Response scheduleJob(MultipleAPRequest multipleAPRequest){
		
		Vector<String> hashBuckets = createHashBuckets(multipleAPRequest.getGceCount());
		//TODO Test für die Kommunikation zwischen Loadbalancer und Compute Node durch eigentliche Implementation ersetzen
		String customerProject = multipleAPRequest.getCustomerProject();
		String computeJobId = UUID.randomUUID().toString();
		JobList jobList = createJobList(hashBuckets, customerProject, computeJobId);
		ComputeJobList.getInstance().addJob(jobList, computeJobId);
        try {
			StringEntity input = new StringEntity(jobList.toString());
			input.setContentType("application/json");

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://" + LOADBALANCER_IP + "/loadbalancer/SchedulingService/scheduleJobList");
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
		
        while(!ComputeJobList.getInstance().allJobResponsesRecieved(computeJobId)){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        int macCount = 0;
        for(JobResponse jobResponse : ComputeJobList.getInstance().getJobResponseList(computeJobId).getJobResponseList()){
        	macCount += jobResponse.getMacCount();
        }
        
        ComputeJobList.getInstance().removeJob(computeJobId);

		return Response.ok(macCount).build();
	}
	
	@POST
	@Path("/aggregateJob")
	@Consumes("application/json")
	public Response aggregateJob(JobResponse jobResponse){
		if(ComputeJobList.getInstance().jobExists(jobResponse.getComputeJobId())){
			if(!ComputeJobList.getInstance().jobResponseAlreadyRecieved(jobResponse)){
				jobResponse.setEndTime(System.currentTimeMillis());
				ComputeJobList.getInstance().addJobResponse(jobResponse);
				
				de.hszg.model.JobResponse response = new de.hszg.model.JobResponse();
				
				String computeJobId = jobResponse.getComputeJobId();
				int jobId = jobResponse.getJobId();
				
				response.setJob(computeJobId + "-" + jobId);
				response.setIpAddress(jobResponse.getIp());
				for(Job job : ComputeJobList.getInstance().getJobList(computeJobId).getJobList()){
					if(job.getJobId() == jobId){
						response.setStartTime(Long.toString(job.getStartTime()));
						break;
					}
				}
				for(JobResponse responseJob: ComputeJobList.getInstance().getJobResponseList(computeJobId).getJobResponseList()){
					if(responseJob.getJobId() == jobId){
						response.setEndTime(Long.toString(responseJob.getEndTime()));
						break;
					}
				}
				
				try {
					StringEntity input = new StringEntity(response.toString());
					input.setContentType("application/json");

					CloseableHttpClient httpclient = HttpClients.createDefault();
					HttpPost httpPost = new HttpPost("http://" + LOADBALANCER_IP + "/loadbalancer/JobResponseTimeTracker/saveResponseTime");
					httpPost.setEntity(input);
					CloseableHttpResponse httpResponse = null;

					httpResponse = httpclient.execute(httpPost);
					HttpEntity entity = httpResponse.getEntity();
					httpResponse.close();
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
			}
		}
		return Response.ok().build();
	}
	
	private Vector<String> createHashBuckets(int nodeCount){
		int jobCount = 0;
		int bitCount = 0;
		while(jobCount < nodeCount){
			bitCount += 1;
			jobCount = (int) Math.pow(2,bitCount);
		}
		Vector<String> hashBuckets = new Vector<String>();
		for(int i = 0; i < jobCount; i++){
			String bucket = Integer.toBinaryString(i);
			while(bucket.length() < bitCount){
				bucket = "0" + bucket;
			}
			hashBuckets.add(bucket);
		}
		return hashBuckets;
	}
	
	private JobList createJobList(Vector<String> hashBuckets, String customerProject, String computeJobId){
		JobList jobList = new JobList();
		//String computeJobId = UUID.randomUUID().toString();
		int hashBucketSize = hashBuckets.size();
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < hashBucketSize; i++){
			Job job = new Job();
			job.setJobId(i);
			job.setComputeJobId(computeJobId);
			job.setCustomerProject(customerProject);
			job.setMacBucket(hashBuckets.get(i));
			job.setStartTime(startTime);
			jobList.getJobList().add(job);
		}
		return jobList;
	}
}
