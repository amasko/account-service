package com.splat.server.db;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Alex on 04.09.2014.
 */
public class AccountManagerImpl {

    private final static Logger LOG = Logger.getLogger(AccountManagerImpl.class);

    public void test() {
        LOG.info("Logging is happening here");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceUnit");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Account account = em.find(Account.class, 25L);
        System.out.println(account.getAmount());
    }

}
