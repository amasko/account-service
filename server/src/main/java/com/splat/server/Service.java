package com.splat.server;

import com.splat.common.AccountService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Service extends UnicastRemoteObject implements AccountService {

    protected Service() throws RemoteException {
    }

    @Override
    public Long getAmount(Integer id) throws RemoteException {
        Statistics.INSTANCE.increment();
        return ServiceManager.INSTANCE.getAmount(id);
    }

    @Override
    public void addAmount(Integer id, Long value) throws RemoteException {
        Statistics.INSTANCE.increment();
        ServiceManager.INSTANCE.addAmount(id, value);
    }

    @Override
    public long[] getStats() throws RemoteException {
        return  Statistics.INSTANCE.stats();
    }

    @Override
    public void resetStatistics() throws RemoteException {
        Statistics.INSTANCE.initStatistics();
    }
}
