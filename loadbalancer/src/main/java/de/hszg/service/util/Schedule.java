package de.hszg.service.util;

import de.hszg.model.MultipleAPRequest;
import de.hszg.model.scheduling.JobScheduleModel;
import de.hszg.service.heartbeat.HeartbeatModel;
import de.hszg.service.heartbeat.SharedMemory;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Andre on 08.05.2015.
 */
public class Schedule {

    public static void startJobComputing(MultipleAPRequest multipleAPRequest, String ipAddress) throws IOException{
        try {
            SerializableEntity input = new SerializableEntity(multipleAPRequest, false);
            input.setContentType("application/json");

            CloseableHttpClient httpclient = HttpClients.createDefault();
            /*TODO Adresse des Services vervollständigen!*/
            HttpPost httpPost = new HttpPost("http://"+ ipAddress +"");
            httpPost.setEntity(input);
            CloseableHttpResponse response = null;

            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            response.close();
            httpclient.close();
        }
        catch (Exception e){
            throw new IOException();
        }
    }

    public static void startJobComputing(JobScheduleModel jobScheduleModel, String ipAddress) throws IOException{
        try {
            SerializableEntity input = new SerializableEntity(jobScheduleModel, false);
            input.setContentType("application/json");

            CloseableHttpClient httpclient = HttpClients.createDefault();
            /*TODO Adresse des Services vervollständigen!*/
            HttpPost httpPost = new HttpPost("http://"+ ipAddress +"");
            httpPost.setEntity(input);
            CloseableHttpResponse response = null;

            response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            response.close();
            httpclient.close();
        }
        catch (Exception e){
            throw new IOException();
        }
    }

    public static String extractIpAddress(String url){
        String[] splittedUrl = url.split("://");

        String servletPath = splittedUrl[1];
        String[] splittedServletPath = servletPath.split("/");

        return splittedServletPath[0];
    }
}
