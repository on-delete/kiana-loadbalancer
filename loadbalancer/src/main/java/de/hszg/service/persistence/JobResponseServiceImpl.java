package de.hszg.service.persistence;

import de.hszg.model.JobResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Andre on 15.06.2015.
 */
@Singleton
public class JobResponseServiceImpl implements JobResponseService{

    @Inject
    private JobResponseDaoImpl jobResponseDao;

    public void save(JobResponse jobResponse, String gceName) {
        JobResponseEntity entity = new JobResponseEntity();
        entity.setIpAddress(jobResponse.getIpAddress());
        entity.setGCEName(gceName);
        entity.setJob(jobResponse.getJob());
        entity.setStartTime(jobResponse.getStartTime());
        entity.setEndTime(jobResponse.getEndTime());

        jobResponseDao.save(entity);
    }

    /*public Object getAll() {
        return all;
    }*/
}
