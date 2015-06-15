package de.hszg.service.heartbeat;

import de.hszg.service.persistence.JobResponseDao;
import de.hszg.service.persistence.JobResponseDaoImpl;
import de.hszg.service.persistence.JobResponseService;
import de.hszg.service.persistence.JobResponseServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * Created by Andre on 08.05.2015.
 */
public class SharedMemoryBinder extends AbstractBinder{
    @Override
    protected void configure() {
        bind(new SharedMemory()).to(SharedMemory.class);
        bind(new JobResponseServiceImpl()).to(JobResponseService.class);
        bind(new JobResponseDaoImpl()).to(JobResponseDao.class);
    }
}
