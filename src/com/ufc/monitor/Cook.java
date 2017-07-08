package com.ufc.monitor;

public class Cook implements Runnable {

    private static final String ENTITY = "Cook";

    private DiningSavagesMonitor monitor;

    public Cook( DiningSavagesMonitor monitor ) {
        this.monitor = monitor;
    }

    @Override
    public void run() {

        while( true ) {
            monitor.refillPot();
        }

    }
}
