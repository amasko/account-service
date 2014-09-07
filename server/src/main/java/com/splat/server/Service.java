package com.splat.server;

import com.splat.common.AccountService;
import com.splat.server.db.AccountManagerImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Service extends UnicastRemoteObject implements AccountService {

    protected Service() throws RemoteException {
    }

    @Override
    public Long getAmount(Integer id) throws RemoteException {
        Statistics.INSTANCE.increment();
        return ServiceManager.INSTANCE.getCache().get(id).get().getAmount();
    }

    @Override
    public void addAmount(Integer id, Long value) throws RemoteException {
        Statistics.INSTANCE.increment();
        ServiceManager.INSTANCE.addAmount(id, value);
    }

    @Override
    public long[] getStats() throws RemoteException {
        return new long[]{Statistics.INSTANCE.getQueries(), Statistics.INSTANCE.getRate()};
    }

    @Override
    public void resetStatistics() throws RemoteException {
        new AccountManagerImpl().loadCacheToDB(ServiceManager.INSTANCE.getCache());
    }
}
