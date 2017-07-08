package com.ufc.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class DiningSavagesSemaphore {

    private Semaphore mutex;
    private Semaphore emptyPot;
    private Semaphore fullPot;

    private final int potCapacity;
    private int stewedInPot;

    public DiningSavagesSemaphore(int potCapacity ) {

        mutex = new Semaphore( 1 );
        emptyPot = new Semaphore(0);
        fullPot = new Semaphore(0);

        this.potCapacity = potCapacity;
        this.stewedInPot = potCapacity;

        System.out.println( "Method: using Semaphore" );
    }

    public int getStewedInPot() {
        return stewedInPot;
    }

    public void eat() {
        stewedInPot -= 1;
    }

    public boolean refillPot() {

        stewedInPot = potCapacity;
        return true;
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public Semaphore getEmptyPot() {
        return emptyPot;
    }

    public Semaphore getFullPot() {
        return fullPot;
    }

    public void start( int numberSavages ) {


        // Create all savage threads
        ExecutorService savagePool = Executors.newFixedThreadPool( numberSavages );
        for( int savage = 0; savage < numberSavages; savage++ ) {

            savagePool.submit( new Savage( savage,
                                           this,
                                           this.getMutex(),
                                           this.getEmptyPot(),
                                           this.getFullPot() ) );

        }


        //Create cook thread
        ExecutorService cookPool = Executors.newSingleThreadExecutor();
        cookPool.submit( new Cook( this,
                                   this.getMutex(),
                                   this.getEmptyPot(),
                                   this.getFullPot() ) );

    }
}
