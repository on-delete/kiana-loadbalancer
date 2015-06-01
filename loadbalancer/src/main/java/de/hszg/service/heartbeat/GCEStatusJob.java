package de.hszg.service.heartbeat;

import de.hszg.gce.GoogleComputeEngineFactory;
import de.hszg.gce.util.GCE;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Path;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andre on 01.06.2015.
 */
@Singleton
public class GCEStatusJob  /*implements Job, Serializable*/{

    private Logger log = LoggerFactory.getLogger(GCEStatusJob.class);

    @Inject
    SharedMemory sharedMemory;

    /*@Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        GoogleComputeEngineFactory googleComputeEngineFactory = new GoogleComputeEngineFactory();

        List<HeartbeatModel> heartbeatList = new ArrayList<>(sharedMemory.getAllHeartbeats());
        List<GCE> gceList = googleComputeEngineFactory.listGCEs();
        List<String> ipAddressList = gceList.stream().map(GCE::getIp).collect(Collectors.toList());

        for(HeartbeatModel heartbeat : heartbeatList){
            if(ipAddressList.contains(heartbeat.getIpAddress())){
                long systemTime = System.currentTimeMillis();

                if(systemTime-heartbeat.getSystemTime()>10000){
                    log.info("Compute Engine mit IP "+heartbeat.getIpAddress()+" antwortet nicht mehr. Wird neu gestartet!");
                    String name = gceList.stream().filter((i) -> i.getIp().equals(heartbeat.getIpAddress())).findFirst().get().getName();
                    googleComputeEngineFactory.resetGCE(name);
                }
            }
        }
    }*/

    @Schedule(second="*/10", minute="*",hour="*", persistent=false)
    public void doWork() {
        log.info("Job laeuft!");
        GoogleComputeEngineFactory googleComputeEngineFactory = new GoogleComputeEngineFactory();

        List<HeartbeatModel> heartbeatList = new ArrayList<>(sharedMemory.getAllHeartbeats());
        List<GCE> gceList = googleComputeEngineFactory.listGCEs();
        List<String> ipAddressList = gceList.stream().map(GCE::getIp).collect(Collectors.toList());

        for (HeartbeatModel heartbeat : heartbeatList) {
            if (ipAddressList.contains(heartbeat.getIpAddress())) {
                long systemTime = System.currentTimeMillis();

                if (systemTime - heartbeat.getSystemTime() > 10000) {
                    log.info("Compute Engine mit IP " + heartbeat.getIpAddress() + " antwortet nicht mehr. Wird neu gestartet!");
                    String name = gceList.stream().filter((i) -> i.getIp().equals(heartbeat.getIpAddress())).findFirst().get().getName();
                    googleComputeEngineFactory.resetGCE(name);
                }
            }
        }
    }
}
