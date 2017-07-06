package com.ufc.semaphore;

import java.util.concurrent.Semaphore;

public class Savage implements Runnable {

    private Semaphore mutex;

    public Savage( Semaphore mutex ) {
        this.mutex = mutex;
    }

    @Override
    public void run() {

        while( true ) {

        }

    }
}
