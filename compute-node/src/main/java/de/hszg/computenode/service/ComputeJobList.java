package de.hszg.computenode.service;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.hszg.model.scheduling.JobList;

/**
 * Created by Tobias on 26.05.2015.
 */
public class ComputeJobList {

	private static ComputeJobList computeJobList = null;
	
	private HashMap<String, JobList> scheduleJobs;
	private HashMap<String, JobResponseList> jobResponses;
	
	private ReentrantReadWriteLock lock;
	private Lock writeLock;
	private Lock readLock;
	
	private ComputeJobList(){
		scheduleJobs = new HashMap<String, JobList>();
		jobResponses = new HashMap<String, JobResponseList>();
		lock = new ReentrantReadWriteLock();
		writeLock = lock.writeLock();
		readLock = lock.readLock();
	}
	
	public static ComputeJobList getInstance(){
		if(computeJobList == null){
			computeJobList = new ComputeJobList();
		}
		return computeJobList;
	}
	
	public void addJob(JobList jobList, String computeJobId){
		writeLock.lock();
		try{
			scheduleJobs.put(computeJobId, jobList);
			jobResponses.put(computeJobId, new JobResponseList());
		}finally{
			writeLock.unlock();
		}
	}
	
	public void removeJob(String computeJobId){
		writeLock.lock();
		try{
			scheduleJobs.remove(computeJobId);
			jobResponses.remove(computeJobId);
		}finally{
			writeLock.unlock();
		}
	}
	
	public JobResponseList getJobResponseList(String computeJobId){
		readLock.lock();
		try{
			return jobResponses.get(computeJobId);
		}finally{
			readLock.unlock();
		}
	}
	
	public void addJobResponse(JobResponse jobResponse){
		writeLock.lock();
		try{
			String computeJobId = jobResponse.getComputeJobId();
			jobResponses.get(computeJobId).getJobResponseList().add(jobResponse);
		}finally{
			writeLock.unlock();
		}
	}
	
	public boolean jobResponseAlreadyRecieved(JobResponse jobResponse){
		readLock.lock();
		try{
			String computeJobId = jobResponse.getComputeJobId();
			int jobId = jobResponse.getJobId();
			boolean jobResponseAlreadyRecieved = false;
			for(JobResponse tempJobResponse : jobResponses.get(computeJobId).getJobResponseList()){
				if(tempJobResponse.getJobId() == jobId){
					jobResponseAlreadyRecieved = true;
					break;
				}
			}
			return jobResponseAlreadyRecieved;
		}finally{
			readLock.unlock();
		}
	}
	
	public boolean jobExists(String computeJobId){
		readLock.lock();
		try{
			if(scheduleJobs.containsKey(computeJobId)){
				if(jobResponses.containsKey(computeJobId)){
					return true;
				}
			}
			return false;
		}finally{
			readLock.unlock();
		}
	}
	
	public boolean allJobResponsesRecieved(String computeJobId){
		readLock.lock();
		try{
			if(scheduleJobs.get(computeJobId).getJobList().size() == jobResponses.get(computeJobId).getJobResponseList().size()){
				return true;
			}else{
				return false;
			}
		}finally{
			readLock.unlock();
		}
	}
}
