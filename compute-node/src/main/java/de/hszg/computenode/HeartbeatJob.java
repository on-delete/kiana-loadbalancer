package de.hszg.computenode;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by daniel on 13.05.15.
 */
public class HeartbeatJob implements Job {

    @Override
    public void execute(final JobExecutionContext ctx) throws JobExecutionException {

        try {
            StringEntity input = new StringEntity("{\"ipAddress\":" + String.valueOf(123456789) + ",\"load\":" + 23.4 + ",\"numberJobs\":" + 3 + "}");
            input.setContentType("application/json");

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut("http://104.197.107.205:8080/HeartbeatService/updateHeartbeat");
            httpPut.setEntity(input);
            CloseableHttpResponse response = null;
            try {
                response = httpclient.execute(httpPut);
                //System.out.println(response.getStatusLine());
                HttpEntity entity2 = response.getEntity();
                response.close();
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
