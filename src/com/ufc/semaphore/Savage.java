package com.ufc.semaphore;

import java.util.concurrent.Semaphore;

public class Savage implements Runnable {

    private static final String ENTITY = "Savage";

    private DiningSavagesSemaphore coordinator;
    private int myNumber;

    private Semaphore mutex;
    private Semaphore emptyPot;
    private Semaphore fullPot;

    public Savage( int myNumber,
                   DiningSavagesSemaphore coordinator,
                   Semaphore mutex,
                   Semaphore emptyPot,
                   Semaphore fullPot ) {

        this.coordinator = coordinator;
        this.myNumber = myNumber;

        this.mutex = mutex;
        this.emptyPot = emptyPot;
        this.fullPot = fullPot;
    }

    @Override
    public void run() {

        try {

            while (true) {

                Thread.sleep(2 * 1000);
                mutex.acquireUninterruptibly();
                if (coordinator.getStewedInPot() == 0) {

                    emptyPot.release();
                    fullPot.acquireUninterruptibly();
                }

                System.out.println(ENTITY + " " + myNumber + " is eating food " + coordinator.getStewedInPot() + "...");
                coordinator.eat();

                mutex.release();
            }

        } catch( InterruptedException e ) {
            e.printStackTrace();
        }
    }
}
