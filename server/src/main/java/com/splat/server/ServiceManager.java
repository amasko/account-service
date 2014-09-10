package com.splat.server;

import com.splat.server.db.Account;
import com.splat.server.db.AccountManagerImpl;
import org.apache.log4j.Logger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public enum ServiceManager {
    INSTANCE;
    private static Logger LOG = Logger.getLogger(ServiceManager.class);
    private final Map<Integer, AtomicReference<Account>> cache = new ConcurrentHashMap<>();
    private AccountManagerImpl accountManager = new AccountManagerImpl();

    public Map<Integer, AtomicReference<Account>> getCache() {
        return cache;
    }

    public Long getAmount(Integer id) {
        if (!cache.containsKey(id)) {
            return 0L;
        }
        return cache.get(id).get().getAmount();
    }

    public void stopDbsync() {
        accountManager.stopDBsync();
    }

    public void initCache() {
        for (Account acc : accountManager.getAllAccounts()) {
            cache.put(acc.getId(), new AtomicReference<>(acc));
        }
        //temporary
//        for (Integer key : cache.keySet()) {
//            cache.put(key, new AtomicReference<>(new Account(key, 0L)));
//        }
        LOG.info("Cache initialized");
        accountManager.DBsync(cache);
        LOG.info("Periodic cache -> DB sync started");
    }

    public void addAmount(Integer id, Long value) {
        if (cache.containsKey(id)) {
            AtomicReference<Account> ref = cache.get(id);
            Account entry = ref.get();
            entry.setAmount(entry.getAmount() + value);
            entry.setModified(true);
//            LOG.info(entry);
            ref.set(entry);
        } else {
            Account entry = new Account(id, value);
            entry.setModified(true);
            cache.put(id, new AtomicReference<>(entry));
        }
    }
}
