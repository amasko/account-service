package com.splat.client;

import com.splat.common.AccountService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ClientsInputManager {
    private int rCount, wCount, from, to;
    private AccountService service;
    private static Random random = new Random();
    private ExecutorService writePool, readPool;
    private final List<Future<?>> futures = new ArrayList<>();

    public ClientsInputManager(int rCount, int wCount, int from, int to, AccountService service) {
        this.rCount = rCount;
        this.wCount = wCount;
        this.from = from;
        this.to = to;
        this.service = service;
        writePool = Executors.newFixedThreadPool(wCount);
        readPool = Executors.newFixedThreadPool(rCount);
    }

    /**
     * starts readers and writers threads
     */
    public void execute() {
        while (wCount > 0) {
            futures.add(writePool.submit(new Write(random, from, to, service)));
            wCount--;
        }
        while (rCount > 0) {
            futures.add(readPool.submit(new Read(random, from, to, service)));
            rCount--;
        }
    }

    public void stop() {
        for (Future<?> f : futures) {
            f.cancel(true);
        }
        writePool.shutdownNow();
        readPool.shutdownNow();
        if (!writePool.isShutdown() || !readPool.isShutdown()) {
            System.out.println("pools steal running, is not shutdown");
        }
        try {
            writePool.awaitTermination(1, TimeUnit.SECONDS);
            readPool.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
