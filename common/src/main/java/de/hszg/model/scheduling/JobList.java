package de.hszg.model.scheduling;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Andre on 03.05.2015.
 *
 * Model class with a list of all jobs that have to be scheduled.
 */
public class JobList implements Serializable{

    private List<Job> jobList;

    public List<Job> getJobList() {
        return jobList;
    }
}
