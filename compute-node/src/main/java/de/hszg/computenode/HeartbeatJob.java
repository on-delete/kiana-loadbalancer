package de.hszg.computenode;

import com.sun.management.OperatingSystemMXBean;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * Created by daniel on 13.05.15.
 */
public class HeartbeatJob implements Job {

    private static String LOADBALANCER_IP = "104.197.107.205:8080";

    @Override
    public void execute(final JobExecutionContext ctx) throws JobExecutionException {

        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        try {
            StringEntity input = new StringEntity("{\"ipAddress\":\"" + InetAddress.getLocalHost().getHostAddress() + "\",\"load\":" + osBean.getSystemCpuLoad() + ",\"numberJobs\":" + 3 + "}");
            input.setContentType("application/json");

            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut("http://"+LOADBALANCER_IP+"/loadbalancer/HeartbeatService/updateHeartbeat");
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
