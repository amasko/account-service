package com.splat.server.db;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public interface AccountManager {

    public List<Account> getAllAccounts();

    public void loadCacheToDB(Map<Integer, AtomicReference<Account>> map);
}
