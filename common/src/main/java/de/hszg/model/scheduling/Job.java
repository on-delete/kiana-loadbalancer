package de.hszg.model.scheduling;

import java.util.Date;
import java.util.List;

/**
 * Created by Andre on 03.05.2015.
 *
 * Mocel class for the jobs with a list of all mac addresses that have to be computed.
 */
public class Job {
	
	private String customerProject = "";

    private String macBucket;

    private String computeJobId;

    private int jobId;
    
    private long startTime;

    public String getCustomerProject() {
		return customerProject;
	}

	public void setCustomerProject(String customerProject) {
		this.customerProject = customerProject;
	}

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

    public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
    
    @Override
    public String toString() {
        return "{\"macBucket\":\""+macBucket+"\",\"computeJobId\":\""+computeJobId+"\",\"jobId\":"+jobId+"}";
    }
}