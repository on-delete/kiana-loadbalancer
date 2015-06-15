package de.hszg.service.persistence;

import de.hszg.model.JobResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.*;
import java.util.List;

/**
 * Created by Andre on 15.06.2015.
 */
@Singleton
public class JobResponseServiceImpl implements JobResponseService{

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persist-unit");

    public void save(JobResponse jobResponse, String gceName) {
        JobResponseEntity entity = new JobResponseEntity();
        entity.setIpAddress(jobResponse.getIpAddress());
        entity.setgcename(gceName);
        entity.setJob(jobResponse.getJob());
        entity.setStartTime(jobResponse.getStartTime());
        entity.setEndTime(jobResponse.getEndTime());

        EntityManager em = emf.createEntityManager();

        EntityTransaction transaction = em.getTransaction();

        try{
            transaction.begin();

            em.merge(entity);

            transaction.commit();
        } catch(Exception e){
            System.out.println("Error Saving JobResponse: " + e.getMessage());

            transaction.rollback();
        }
        finally {
            em.close();
        }
    }

    public List<JobResponseEntity> getAll() {
        EntityManager em = emf.createEntityManager();
        List<JobResponseEntity> result = null;

        try{
            result = em.createNamedQuery("JobResponseEntity.getAllJobResponse", JobResponseEntity.class).getResultList();
        }catch(Exception e){
            System.out.println("Fehler beim holen der Jobresponse: " + e.getMessage());
        }
        finally {
            em.close();
        }

        return result;
    }
}
