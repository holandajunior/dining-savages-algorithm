package com.ufc.monitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DiningSavagesMonitor {

    private Lock lock;
    private Condition cook;
    private Condition savages;

    private final int potCapcity;
    private int stewedInPot;

    public DiningSavagesMonitor( int potCapacity ) {

        this.lock = new ReentrantLock();
        this.savages = this.lock.newCondition();
        this.cook = this.lock.newCondition();

        this.potCapcity = potCapacity;
        this.stewedInPot = potCapacity;

        System.out.println( "Method: using Monitor" );

    }

    public void eat( int savage ) {

        lock.lock();

        while ( stewedInPot == 0 ) {
            cook.signal();
            savages.awaitUninterruptibly();
        }

        System.out.println( "Savage " + savage + " eating food " + stewedInPot + "..." );
        --stewedInPot;

        lock.unlock();

    }

    public void refillPot() {
        lock.lock();

        if ( stewedInPot == 0) {
            stewedInPot = potCapcity;
            savages.signalAll();
            System.out.println( "Cook refilled pot..." );
        }

        System.out.println( "Cook sleeping..." );
        cook.awaitUninterruptibly();

        lock.unlock();
    }

    public void start( int numberSavages ) {

        // Create all savage threads
        ExecutorService savagePool = Executors.newFixedThreadPool( numberSavages );
        for( int savage = 0; savage < numberSavages; savage++ ) {

            savagePool.submit( new Savage( savage, this ) );

        }

        //Create cook thread
        ExecutorService cookPool = Executors.newSingleThreadExecutor();
        cookPool.submit( new Cook( this ) );

    }


}
