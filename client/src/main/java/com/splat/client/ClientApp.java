package com.splat.client;

import com.splat.common.AccountService;

import javax.jnlp.ServiceManager;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by Alex on 05.09.2014.
 */
public class ClientApp {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        String name = "Service";
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        AccountService service = (AccountService) registry.lookup(name);
        System.out.println(service.getAmount(5));
    }
}
