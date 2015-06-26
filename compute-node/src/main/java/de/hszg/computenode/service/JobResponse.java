package de.hszg.computenode.service;

import java.io.Serializable;

/**
 * Created by Tobias on 19.05.2015.
 * 
 * Mocel class for the response of a computed job.
 */
public class JobResponse implements Serializable {
	
	private String computeJobId;
	private int jobId;
	private int macCount;
	private long endTime;
	private String ip;
	
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
	public int getMacCount() {
		return macCount;
	}
	public void setMacCount(int macCount) {
		this.macCount = macCount;
	}
	
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Override
    public String toString() {
        return "{\"computeJobId\":\""+computeJobId+"\",\"jobId\":\""+jobId+"\",\"macCount\":"+macCount+"\"ip\":\""+ip+"}";
    }
	
}
