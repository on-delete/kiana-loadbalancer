package de.hszg.model.scheduling;

import java.io.Serializable;

/**
 * Created by Andre on 17.05.2015.
 */
public class JobScheduleModel implements Serializable {

    private Job job;

    private String requestIpAddress;

    public JobScheduleModel(Job job, String requestIpAddress) {
        this.job = job;
        this.requestIpAddress = requestIpAddress;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public String getRequestIpAddress() {
        return requestIpAddress;
    }

    public void setRequestIpAddress(String requestIpAddress) {
        this.requestIpAddress = requestIpAddress;
    }
}
