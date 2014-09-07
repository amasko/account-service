package com.splat.server.db;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Alex on 07.09.2014.
 */
public interface AccountManager {

    public List<Account> getAllAccounts();

    public void loadCacheToDB(Map<Integer, AtomicReference<Account>> map);
}
