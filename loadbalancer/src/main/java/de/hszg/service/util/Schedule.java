package de.hszg.service.util;

import de.hszg.model.MultipleAPRequest;
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

    private SharedMemory sharedMemory;

    public Schedule(SharedMemory sharedMemory){
        this.sharedMemory = sharedMemory;
    }

    public String getGCEWithLeastLoad() throws IndexOutOfBoundsException{
        if(sharedMemory.getAllHeartbeats().size()>0){
            List<HeartbeatModel> allHeartbeats = new ArrayList<>(sharedMemory.getAllHeartbeats());
            Collections.sort(allHeartbeats);
            return allHeartbeats.get(0).getIpAddress();
        }
        else{
            throw new IndexOutOfBoundsException();
        }
    }

    public void startJobComputing(MultipleAPRequest multipleAPRequest, String ipAddress) throws IOException{
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
}
