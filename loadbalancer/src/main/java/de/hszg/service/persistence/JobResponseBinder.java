package de.hszg.service.persistence;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * Created by Andre on 15.06.2015.
 */
public class JobResponseBinder extends AbstractBinder{

    @Override
    protected void configure(){
        bind(JobResponseServiceImpl.class).to(JobResponseService.class).in(Singleton.class);

        bind(JobResponseDaoImpl.class).to(JobResponseDao.class).in(Singleton.class);
    }
}
