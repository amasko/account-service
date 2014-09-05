package com.splat.server.db;
import org.apache.log4j.Logger;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Alex on 04.09.2014.
 */
public class AccountManagerImpl {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceUnit");
    private final static Logger LOG = Logger.getLogger(AccountManagerImpl.class);

    public void test() {
        LOG.info("Logging is happening here");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
//        Account account = em.find(Account.class, 25);
//        System.out.println(account.getAmount());
//        Account acc2 = em.find(Account.class, 2002);
//        if (acc2 == null) {
//            System.out.println("no such entry");
//            acc2 = new Account(2002, 2002L);
//            em.persist(em.merge(acc2));
//
//        }
        Account account1 = new Account(10, 1L);
        em.persist(em.merge(account1));


        em.getTransaction().commit();
        em.close();
    }

    /**
     * Returns all table content
     * @return list of accounts
     */
    public List<Account> getAllAccounts() {
        EntityManager em = emf.createEntityManager();
        CriteriaQuery<Account> criteria = em.getCriteriaBuilder().createQuery(Account.class);
        Root<Account> root = criteria.from(Account.class);
        criteria.select(root);
        List<Account> list = em.createQuery(criteria).getResultList();
        em.close();
        //LOG.info("Cache initialized");
        return list;
    }

    public void loadCacheToDB(Map<Integer, AtomicReference<Account>> map) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for (Integer key : map.keySet()) {
            AtomicReference<Account> ref = map.get(key);
            Account entry = ref.get();
            //load to DB only if data in cache was accessed
            if (entry.isModified()) {
                entry.setModified(false);
                em.persist(em.merge(entry));
                ref.set(entry);
                //map.put(key, ref);
            }
        }
        em.getTransaction().commit();
        em.close();
        LOG.info("loaded!");
    }

}
