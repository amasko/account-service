package com.splat.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientApp {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        new InputHandler().handleInput();
    }
}
