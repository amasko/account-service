package com.splat.client;
import com.splat.common.AccountService;
import org.apache.log4j.Logger;
import java.rmi.RemoteException;
import java.util.Random;

public class Write implements Runnable {
    private static Logger LOG = Logger.getLogger(Read.class);
    private Random random;
    private int from, to;
    private AccountService service;
    private boolean running = true;

    public Write(Random random, int from, int to, AccountService service) {
        this.random = random;
        this.from = from;
        this.to = to;
        this.service = service;
    }

    public void setRunning(boolean r) {
        running = r;
    }

    @Override
    public void run() {
        while (running) {
            int id = random.nextInt(to - from + 1) + from;
            int amount = random.nextInt(1000);
            boolean bool = random.nextBoolean();
            amount = bool ? amount : -amount;
            try {
                service.addAmount(id, (long) amount);
            } catch (RemoteException e) {
                LOG.error("Remote access failed!");
                running = false;
                //e.printStackTrace();
            }
        }
    }
}
