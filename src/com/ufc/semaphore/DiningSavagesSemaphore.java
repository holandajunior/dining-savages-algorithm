package com.ufc.semaphore;

import com.ufc.InfoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class DiningSavagesSemaphore {

    private static final int NUMBER_SAVAGES = InfoUtils.NUMBER_SAVAGES;

    private Semaphore mutex;
    private int stewedInPot;

    public DiningSavagesSemaphore( int stewedInPot ) {

        this.stewedInPot = stewedInPot;
        mutex = new Semaphore( 1 );
    }

    public int getFood() {

        stewedInPot -= 1;
        return stewedInPot;
    }

    public Semaphore getMutex() {
        return mutex;
    }



    public static void main(String[] args) {

        DiningSavagesSemaphore coordinator = new DiningSavagesSemaphore( 5 );

        // Create all savage threads
        ExecutorService savagePool = Executors.newFixedThreadPool( DiningSavagesSemaphore.NUMBER_SAVAGES );
        for( int savage = 0; savage < NUMBER_SAVAGES; savage++ ) {

            savagePool.submit( new Savage( coordinator.getMutex() ) );

        }

        //Create cook thread
        ExecutorService cookPool = Executors.newSingleThreadExecutor();
        cookPool.submit( new Cook() );

    }
}
