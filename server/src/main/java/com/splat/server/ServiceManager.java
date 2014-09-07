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

    public void initCache() {
        for (Account acc : accountManager.getAllAccounts()) {
            cache.put(acc.getId(), new AtomicReference<>(acc));
        }
        LOG.info("Cache initialized");
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
