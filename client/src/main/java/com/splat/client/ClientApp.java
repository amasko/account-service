package com.splat.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientApp {
    public static void main(String[] args){
        try {
            new InputHandler().handleInput();
        } catch (RemoteException | NotBoundException e) {
            System.out.println("Problems with remote connection occurred.\n" +
                    "Possibly server is not running or wrong host. ");
        }
    }
}
