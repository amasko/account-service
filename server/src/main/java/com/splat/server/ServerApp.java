package com.splat.server;

import com.splat.common.AccountService;
import com.splat.server.db.Account;
import com.splat.server.db.AccountManagerImpl;

import javax.jnlp.*;
import java.lang.instrument.Instrumentation;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

/**
 * Created by Alex on 04.09.2014.
 */
public class ServerApp {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, UnknownHostException {

//        List<Account> list = ai.getAllAccounts();
//        System.out.println(list.size() + " " + list.get(200));

        //String.valueOf(java.net.Inet4Address.getLocalHost())
//-----------------------------------------------
        ServiceManager.INSTANCE.initCache();
        System.out.println(ServiceManager.INSTANCE.getCache().size()
        );
//
//        System.out.println( ServiceManager.INSTANCE.getCache().get(25) );
//        ServiceManager.INSTANCE.getCache().get(25);
//        System.out.println( ServiceManager.INSTANCE.getCache().get(25) );
        ServiceManager.INSTANCE.addAmount(3, 5L);
        System.out.println(ServiceManager.INSTANCE.getCache().get(3).get());

        ServiceManager.INSTANCE.addAmount(1200, 50L);
        AccountManagerImpl ai = new AccountManagerImpl();
        ai.loadCacheToDB(ServiceManager.INSTANCE.getCache());
        System.out.println(ServiceManager.INSTANCE.getCache().get(3).get());
        System.out.println(ServiceManager.INSTANCE.getCache().get(1200).get());

//        System.out.println( ServiceManager.INSTANCE.getCache().get(25) );
//        String name = "Service";
//        AccountService service = new Service();
//        Registry registry = LocateRegistry.createRegistry(1099);
//        registry.bind(name, service);

    }
}
