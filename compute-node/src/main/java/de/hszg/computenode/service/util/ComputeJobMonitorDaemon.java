package de.hszg.computenode.service.util;

import java.io.IOException;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.hszg.model.scheduling.Job;
import de.hszg.model.scheduling.JobList;

/**
 * Created by Tobias on 12.06.2015.
 */
public class ComputeJobMonitorDaemon implements Runnable {
	
	private static String LOADBALANCER_IP = "104.197.107.205:8080";
	
	private static Logger log = LogManager.getRootLogger();

	@Override
	public void run() {
		while(true){
			Set<String> computeJobs = ComputeJobList.getInstance().getComputeJobs();
			for(String computeJob : computeJobs){
				JobList jobList = ComputeJobList.getInstance().getJobList(computeJob);
				JobResponseList responseList = ComputeJobList.getInstance().getJobResponseList(computeJob);
				JobList openJobList = new JobList();
				if((jobList.getJobList().size()/3) <= responseList.getJobResponseList().size()){
					long averageTime = 0;
					for(JobResponse response : responseList.getJobResponseList()){
						for(Job job : jobList.getJobList()){
							if(job.getJobId() == response.getJobId()){
								averageTime += (response.getEndTime()-job.getStartTime());
								break;
							}
						}
					}
					averageTime /= responseList.getJobResponseList().size();
					long actualTime = System.currentTimeMillis();
					for(Job job : jobList.getJobList()){
						boolean open = true;
						for(JobResponse response : responseList.getJobResponseList()){
							if(response.getJobId() == job.getJobId()){
								open = false;
								break;
							}
						}
						if(open){
							if((actualTime - job.getStartTime()) >= 2 * averageTime){
								openJobList.getJobList().add(job);
							}
						}
					}
				}
				if(openJobList.getJobList().size() > 0){
					log.info("Restarted Jobs: " + jobList.toString());
					try {
						StringEntity input = new StringEntity(openJobList.toString());
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
						log.error(e.getMessage());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						log.error(e.getMessage());
					}
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
		}
	}

}
