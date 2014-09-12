package com.splat.server.db;
import org.apache.log4j.Logger;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AccountManagerImpl implements AccountManager{
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("PersistenceUnit");
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);
    private final static Logger LOG = Logger.getLogger(AccountManagerImpl.class);
    private ScheduledFuture<?> loaderHandler;

    /**
     * Returns all table content
     * @return list of accounts
     */
    @Override
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

    @Override
    public void loadCacheToDB(Map<Integer, AtomicReference<Account>> map) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        int count = 0;
        for (Integer key : map.keySet()) {
            AtomicReference<Account> ref = map.get(key);
            Account entry = ref.get();
            //load to DB only if data in cache was accessed
            if (entry.isModified()) {
                entry.setModified(false);
                em.persist(em.merge(entry));
                ref.set(entry);
                count++;
            }
        }
        em.getTransaction().commit();
        em.close();
        LOG.info("Updated " + count + " accounts;");
    }

    /**
     * Loads cache content to database with a given period (20 sec)
     * @param map
     */
    public void DBsync(final Map<Integer, AtomicReference<Account>> map) {
        final Runnable loader = new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                LOG.info("start uploading cache to DB ");
                loadCacheToDB(map);
                long finish = System.currentTimeMillis();
                LOG.info("finished uploading cache to DB, loading time: " + (finish - start) + " ms.");
            }
        };
        loaderHandler = scheduler.scheduleAtFixedRate(loader, 20, 20, TimeUnit.SECONDS);
    }

    /**
     * stops scheduled task for cache to bd ploading
     */
    public void stopDBsync() {
        loaderHandler.cancel(true);
    }
}
