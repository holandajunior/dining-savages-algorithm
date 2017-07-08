package com.ufc.semaphore;


import java.util.concurrent.Semaphore;

public class Cook implements Runnable {

    private static final String ENTITY = "Cook";

    private DiningSavagesSemaphore coordinator;

    private Semaphore mutex;
    private Semaphore emptyPot;
    private Semaphore fullPot;

    public Cook( DiningSavagesSemaphore coordinator,
                 Semaphore mutex,
                 Semaphore emptyPot,
                 Semaphore fullPot ) {

        this.coordinator = coordinator;

        this.mutex = mutex;
        this.emptyPot = emptyPot;
        this.fullPot = fullPot;
    }

    @Override
    public void run() {


        while( true ) {

            emptyPot.acquireUninterruptibly();

            System.out.println( ENTITY + " is refilling pot..." );
            coordinator.refillPot();
            System.out.println( ENTITY + " refilled pot..." );

            fullPot.release();

        }


    }
}
