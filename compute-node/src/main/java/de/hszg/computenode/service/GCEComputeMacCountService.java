package de.hszg.computenode.service;

import java.io.IOException;
import java.net.InetAddress;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Vector;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.Bigquery.Jobs.Insert;
import com.google.api.services.bigquery.model.GetQueryResultsResponse;
import com.google.api.services.bigquery.model.Job;
import com.google.api.services.bigquery.model.JobConfiguration;
import com.google.api.services.bigquery.model.JobConfigurationQuery;
import com.google.api.services.bigquery.model.JobReference;
import com.google.api.services.bigquery.model.TableRow;

import de.hszg.computenode.bigquery.BigQueryAuthenticator;
import de.hszg.computenode.service.util.JobResponse;
import de.hszg.computenode.service.util.MacHelper;
import de.hszg.model.scheduling.JobScheduleModel;

/**
 * Created by Tobias on 19.05.2015.
 */
@Path("/GCEComputeMacCountService")
public class GCEComputeMacCountService {
	
	private static Logger log = LogManager.getRootLogger();
	
	@POST
	@Path("/computeMacCount")
	@Consumes("application/json")
	public Response computeMacCount(JobScheduleModel jobScheduleModel){
		
		Vector<String> macs = MacHelper.getInstance().getMacs(jobScheduleModel.getJob().getMacBucket());
		log.info(jobScheduleModel.getJob().getCustomerProject());
		String query = createQuery(macs, jobScheduleModel.getJob().getCustomerProject());
		BigQueryAuthenticator bigQueryAuthenticator = new BigQueryAuthenticator();
		Bigquery bigquery = null;
		String macCount = "";
		String ip = "";
		try {
			bigquery = bigQueryAuthenticator.getBigquery();
			macCount = executeQuery(bigquery, query);
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JobResponse jobResponse = new JobResponse();
		jobResponse.setComputeJobId(jobScheduleModel.getJob().getComputeJobId());
		jobResponse.setJobId(jobScheduleModel.getJob().getJobId());
		jobResponse.setMacCount(Integer.parseInt(macCount));
		jobResponse.setIp(ip);
		
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
	
	private String createQuery(Vector<String> macs, String customerProject){
		log.info(customerProject);
		String query = "SELECT COUNT(*) AS count FROM (SELECT COUNT(ClientMacAddr) AS macCount"+
						" FROM [" + BigQueryAuthenticator.PROJECT_ID + ":" + customerProject + ".observationperstore]"+
						" WHERE ";
		int macsSize = macs.size();
		for(int i = 0; i < macs.size(); i++){
			query = query.concat("(RIGHT(ClientMacAddr, " + macs.get(i).length() +") == '" + macs.get(i)+"')");
			macsSize--;
			if(macsSize > 0){
				query = query.concat(" OR ");
			}
		}
		query = query.concat(" GROUP BY ClientMacAddr);");
		return query;
	}

	public String executeQuery(Bigquery bigquery, String query) throws IOException {
		Job job = new Job();
		JobConfiguration config = new JobConfiguration();
		JobConfigurationQuery queryConfig = new JobConfigurationQuery();
		config.setQuery(queryConfig);

		job.setConfiguration(config);
		queryConfig.setQuery(query);
	
		Insert insert = bigquery.jobs().insert(BigQueryAuthenticator.PROJECT_ID, job);
		insert.setProjectId(BigQueryAuthenticator.PROJECT_ID);
		Job jobDone = waitForJob(bigquery, BigQueryAuthenticator.PROJECT_ID, insert.execute().getJobReference());
	  
		GetQueryResultsResponse queryResultsResponse = bigquery.jobs().getQueryResults(BigQueryAuthenticator.PROJECT_ID, jobDone.getJobReference().getJobId()).execute();
	  
		List<TableRow> rows = queryResultsResponse.getRows();
		
		String macCount = (String) rows.get(0).getF().get(0).getV();
		return macCount;
	}
	
	private static Job waitForJob(Bigquery bigquery, String projectId, JobReference jobRef)
	      throws IOException {
		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// ignore
			}
			Job pollJob = bigquery.jobs().get(projectId, jobRef.getJobId()).execute();
			if (!isJobRunning(pollJob)) {
				return pollJob;
			}
		}
	}
	
	private static boolean isJobRunning(Job job) {
		return job.getStatus().getState().equals("RUNNING") ||
				job.getStatus().getState().equals("PENDING");
	}
}
