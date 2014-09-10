package com.splat.server;

import com.splat.common.AccountService;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class ServerApp {
    public static void main(String[] args) throws RemoteException,
            AlreadyBoundException, UnknownHostException, NotBoundException {
        //loading content from db to cache
        ServiceManager.INSTANCE.initCache();
        Statistics.INSTANCE.initStatistics();
        System.out.println(ServiceManager.INSTANCE.getCache().size());
        //Establishing server connection
        String name = "Service";
        AccountService service = new Service();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind(name, service);
        //shut server down
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("e")) {
            System.out.println("For shutting server down enter letter `e`");
            input = scanner.next();
        }
        ServiceManager.INSTANCE.stopDbsync();
        registry.unbind(name);
        UnicastRemoteObject.unexportObject(service, true);
        System.exit(0);
    }
}
