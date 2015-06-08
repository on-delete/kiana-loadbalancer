package de.hszg.service.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hszg.gce.GoogleComputeEngineFactory;
import de.hszg.gce.util.GCE;
import de.hszg.model.MultipleAPRequest;
import de.hszg.model.heartbeat.Heartbeat;
import de.hszg.model.scheduling.JobScheduleModel;
import de.hszg.service.heartbeat.HeartbeatModel;
import de.hszg.service.heartbeat.SharedMemory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andre on 08.05.2015.
 */
public class Schedule {

    private static Logger log = LogManager.getRootLogger();

    public static String startAggregateJob(MultipleAPRequest multipleAPRequest, String ipAddress) throws IOException{
        try {
            StringEntity input = new StringEntity(multipleAPRequest.toString());
            input.setContentType("application/json");

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://"+ ipAddress +":8080/GCESchedulingAggregationService/scheduleJob");
            httpPost.setEntity(input);
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if(entity!=null) {
                String responseString = EntityUtils.toString(entity);
                return responseString;
            }
            else
            httpclient.close();
        }
        catch (Exception e){
            throw e;
        }

        return null;
    }

    public static void startJobComputing(JobScheduleModel jobScheduleModel, String ipAddress) throws IOException{
        try {
            StringEntity input = new StringEntity(jobScheduleModel.toString());
            input.setContentType("application/json");

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://"+ ipAddress +":8080/GCEComputeMacCountService/computeMacCount");
            httpPost.setEntity(input);
            httpclient.execute(httpPost);
            httpclient.close();
        }
        catch (Exception e){
            throw new IOException();
        }
    }

    public static boolean checkGCEStatus(HeartbeatModel heartbeatModel){
        GoogleComputeEngineFactory googleComputeEngineFactory = new GoogleComputeEngineFactory();

        List<GCE> gceList = googleComputeEngineFactory.listGCEs();
        List<String> ipAddressList = gceList.stream().map(GCE::getIp).collect(Collectors.toList());

        if(ipAddressList.contains(heartbeatModel.getIpAddress())){
            long systemTime = System.currentTimeMillis();

            if(systemTime-heartbeatModel.getSystemTime()>2000){
                log.info("Compute Engine mit IP "+heartbeatModel.getIpAddress()+" antwortet nicht mehr. Wird neu gestartet!");
                String name = gceList.stream().filter((i) -> i.getIp().equals(heartbeatModel.getIpAddress())).findFirst().get().getName();
                googleComputeEngineFactory.resetGCE(name);
                return false;
            }

            return true;
        }
        else{
            return false;
        }
    }

    public static String extractIpAddress(String url){
        String[] splittedUrl = url.split("://");

        String servletPath = splittedUrl[1];
        String[] splittedServletPath = servletPath.split("/");

        return splittedServletPath[0];
    }

    private JsonNode readStringAsJson(String jsonString) {
        log.info(jsonString);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;

        try {
            rootNode = mapper.readTree(jsonString);
        } catch (IOException e) {
        }

        return rootNode;
    }

    /**
     * Converts a input stream to a string.
     *
     * @param inputStreamReader a input stream
     * @return string from input stream
     * @throws IOException
     */
    private static String readInputStreamAsString(InputStream inputStreamReader)
            throws IOException {
        BufferedInputStream bis = new BufferedInputStream(inputStreamReader);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while (result != -1) {
            byte b = (byte) result;
            buf.write(b);
            result = bis.read();
        }

        return buf.toString();
    }
}
