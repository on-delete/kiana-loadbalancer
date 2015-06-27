package de.hszg.service.util;

import de.hszg.model.scheduling.JobScheduleModel;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Created by Andre on 27.06.2015.
 */
public class JobScheduleThread implements Runnable {

    private Thread t;
    private JobScheduleModel jobScheduleModel;
    private String ipAddress;

    public JobScheduleThread(JobScheduleModel jobScheduleModel, String ipAddress) {
        this.jobScheduleModel = jobScheduleModel;
        this.ipAddress = ipAddress;
    }

    public void run() {
        try {
            StringEntity input = new StringEntity(jobScheduleModel.toString());
            input.setContentType("application/json");

            CloseableHttpClient httpclient =  HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("http://"+ ipAddress +":8080/GCEComputeMacCountService/computeMacCount");
            httpPost.setEntity(input);
            httpclient.execute(httpPost);
            httpclient.close();
       }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start ()
    {
        if (t == null)
        {
            t = new Thread (this);
            t.start ();
        }
    }
}
