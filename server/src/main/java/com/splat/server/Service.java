package com.splat.server;

import com.splat.common.AccountService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Service extends UnicastRemoteObject implements AccountService {

    protected Service() throws RemoteException {
    }

    @Override
    public Long getAmount(Integer id) throws RemoteException {
        return (long) (id * 2);
    }

    @Override
    public void addAmount(Integer id, Long value) throws RemoteException {
        ServiceManager.INSTANCE.addAmount(id, value);
    }

    @Override
    public void getStats() throws RemoteException {

    }

    @Override
    public void resetStatistics() throws RemoteException {

    }
}
