package de.hszg.model.scheduling;

import java.util.List;

/**
 * Created by Andre on 03.05.2015.
 *
 * Mocel class for the jobs with a list of all mac addresses that have to be computed.
 */
public class Job {

    private String macBucket;

    private String computeJobId;

    private int jobId;

    public String getMacBucket() {
        return macBucket;
    }

    public void setMacBucket(String macBucket) { this.macBucket = macBucket; }

    public String getComputeJobId() {
        return computeJobId;
    }

    public void setComputeJobId(String computeJobId) {
        this.computeJobId = computeJobId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "{\"macBucket\":\""+macBucket+"\",\"computeJobId\":\""+computeJobId+"\",\"jobId\":"+jobId+"}";
    }
}