package de.bensoft.jettyembedded;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * Created by michaelbenoit on 02.12.15.
 */
public class EntityManagerProducer {

    @Produces
    public EntityManager produceEntityManager() {
        return Persistence
                .createEntityManagerFactory("jettyEmbeddedPU")
                .createEntityManager();
    }
}
