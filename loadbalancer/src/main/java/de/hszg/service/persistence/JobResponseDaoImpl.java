package de.hszg.service.persistence;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Created by Andre on 15.06.2015.
 */
@Singleton
public class JobResponseDaoImpl implements JobResponseDao{
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persist-unit");

    public void save(JobResponseEntity entity) {
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
}
