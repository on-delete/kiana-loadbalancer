
package de.hszg.computenode.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tobias on 01.06.2015.
 *
 * Model class with a list of responses from the scheduled jobs of one request.
 */
public class JobResponseList {
	
	private List<JobResponse> jobResponseList = new ArrayList<JobResponse>();
	
	public List<JobResponse> getJobResponseList(){
		return jobResponseList;
	}
}
