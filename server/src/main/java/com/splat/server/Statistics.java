package com.splat.server;

import java.util.concurrent.atomic.AtomicLong;

public enum Statistics {
    INSTANCE;
    private final AtomicLong queries = new AtomicLong(0);
    private long timeStamp;

    public void initStatistics() {
        queries.set(0);
        timeStamp = System.currentTimeMillis();
    }

    public void increment() {
        queries.incrementAndGet();
    }

    public long getRate() {
        long time = System.currentTimeMillis() - timeStamp;
        long tempQueries = queries.get();
        double rate = (double) time / (double) tempQueries * 1000;
        return (long) rate;
    }

    public long getQueries() {
        return queries.get();
    }
}
