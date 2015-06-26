package de.hszg.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Andre on 07.05.2015.
 */
public class HeartbeatTest {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(100);

        for(int i = 1; i<=20; i++){
            Runnable worker = new HttpThread(i);
            executor.execute(worker);
        }
        executor.shutdown();
        // Wait until all threads are finish
        while (!executor.isTerminated()) {

        }
    }

    public static void postHeartbeat(int ipAddress){
        try {
            StringEntity input = new StringEntity("{\"ipAddress\":"+String.valueOf(ipAddress)+",\"load\":"+23.4+",\"numberJobs\":"+3+"}");
            input.setContentType("application/json");

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://10.240.81.175:8080/HeartbeatService/postHeartbeat");
            httpPost.setEntity(input);
            CloseableHttpResponse response = null;
            try {
                response = httpclient.execute(httpPost);
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

    public static void updateHeartbeat(int ipAddress){
        try {
            StringEntity input = new StringEntity("{\"ipAddress\":" + String.valueOf(ipAddress) + ",\"load\":" + 23.4 + ",\"numberJobs\":" + 3 + "}");
            input.setContentType("application/json");

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut("http://localhost:8080/HeartbeatService/updateHeartbeat");
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

    public static class HttpThread implements Runnable {
        private final int ipAddress;

        HttpThread(int ipAddress) {
            this.ipAddress = ipAddress;
        }

        public void run() {
            //postHeartbeat(ipAddress);

            int i=0;
            while(i < 1){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                updateHeartbeat(ipAddress);

                //i++;
            }
        }
    }
}
