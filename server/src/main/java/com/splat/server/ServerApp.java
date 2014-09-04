package com.splat.server;

import com.splat.server.db.AccountManagerImpl;

/**
 * Created by Alex on 04.09.2014.
 */
public class ServerApp {
    public static void main(String[] args) {
        AccountManagerImpl ai = new AccountManagerImpl();
        ai.test();

    }
}
