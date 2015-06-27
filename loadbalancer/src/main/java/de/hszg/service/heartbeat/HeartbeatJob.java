package de.hszg.service.heartbeat;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Andre on 27.06.2015.
 */
public class HeartbeatJob implements Job {

    private static String LOADBALANCER_IP = "104.197.107.205:8080";

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://"+LOADBALANCER_IP+"/loadbalancer/HeartbeatService/checkHeartbeats");
            try {
                httpclient.execute(httpGet);
                httpclient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
