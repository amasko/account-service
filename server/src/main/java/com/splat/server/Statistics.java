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

    public long[] stats() {
        long[] statsArray = new long[3];
        long interval = System.currentTimeMillis() - timeStamp;
        long tempQueries = queries.get();
        statsArray[0] = interval;
        statsArray[1] = tempQueries;
        double rate = 1000 * (double) tempQueries / (double) interval;
        statsArray[2] = (long) rate;
        return statsArray;
    }

    public long getQueries() {
        return queries.get();
    }
}
