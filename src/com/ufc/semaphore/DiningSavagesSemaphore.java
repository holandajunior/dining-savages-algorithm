package com.ufc.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class DiningSavagesMonitor {

    private Semaphore mutex;
    private Semaphore emptyPot;
    private Semaphore fullPot;

    private final int potCapacity;
    private int stewedInPot;

    public DiningSavagesMonitor(int potCapacity ) {

        mutex = new Semaphore( 1 );
        emptyPot = new Semaphore(0);
        fullPot = new Semaphore(0);

        this.potCapacity = potCapacity;
        this.stewedInPot = potCapacity;
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

    /**
     *
     * @param args [0] -> Number of savage threads; [1] -> Pot capacity
     */
    public static void main(String[] args) {

        int numberSavages = Integer.valueOf( args[0] );
        int potCapacity = Integer.valueOf( args[1] );

        DiningSavagesMonitor coordinator = new DiningSavagesMonitor( potCapacity );

        // Create all savage threads
        ExecutorService savagePool = Executors.newFixedThreadPool( numberSavages );
        for( int savage = 0; savage < numberSavages; savage++ ) {

            savagePool.submit( new Savage( savage,
                                           coordinator,
                                           coordinator.getMutex(),
                                           coordinator.getEmptyPot(),
                                           coordinator.getFullPot() ) );

        }


        //Create cook thread
        ExecutorService cookPool = Executors.newSingleThreadExecutor();
        cookPool.submit( new Cook( coordinator,
                                   coordinator.getMutex(),
                                   coordinator.getEmptyPot(),
                                   coordinator.getFullPot() ) );

    }
}
