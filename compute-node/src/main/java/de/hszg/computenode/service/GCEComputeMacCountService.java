package de.hszg.computenode.service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import de.hszg.model.scheduling.JobScheduleModel;

/**
 * Created by Tobias on 19.05.2015.
 */
@Path("/GCEComputeMacCountService")
public class GCEComputeMacCountService {

	@POST
	@Path("/computeMacCount")
	@Consumes("application/json")
	public Response computeMacCount(JobScheduleModel jobScheduleModel){

		JobResponse jobResponse = new JobResponse();
		jobResponse.setComputeJobId(jobScheduleModel.getJob().getComputeJobId());
		jobResponse.setJobId(jobScheduleModel.getJob().getJobId());
		jobResponse.setMacCount(5);
		
		try {
			StringEntity input = new StringEntity(jobResponse.toString());
			input.setContentType("application/json");

			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost("http://" + jobScheduleModel.getRequestIpAddress() + ":8080/GCESchedulingAggregationService/aggregateJob");
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
		
		return Response.ok().build();
	}
}
