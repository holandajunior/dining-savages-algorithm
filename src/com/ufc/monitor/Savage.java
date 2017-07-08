package com.ufc.monitor;

public class Savage implements Runnable {

    private DiningSavagesMonitor monitor;

    private int myNumber;

    public Savage( int myNumber, DiningSavagesMonitor monitor ) {
        this.monitor = monitor;
        this.myNumber = myNumber;
    }

    @Override
    public void run() {

        try {

            while (true) {
                Thread.sleep(2 * 1000);
                monitor.eat( myNumber );
            }

        } catch( InterruptedException e ) {
            e.printStackTrace();
        }
    }
}
