package de.hszg.service.persistence;

import de.hszg.model.JobResponse;

/**
 * Created by Andre on 15.06.2015.
 */
public interface JobResponseService {

    public void save(JobResponse jobResponse, String gceName);
}
