package com.ufc;

import com.ufc.monitor.DiningSavagesMonitor;
import com.ufc.semaphore.DiningSavagesSemaphore;

public class DiningSavagesMain {

    /**
     *
     * @param args [0] method: semaphore or monitor ; [1] -> Number of savage threads; [2] -> Pot capacity
     */
    public static void main( String[] args ) {

        String method = args[0];
        int numberSavages = Integer.valueOf( args[1] );
        int potCapacity = Integer.valueOf( args[2] );

        if( method.equals("monitor") )
            new DiningSavagesMonitor( potCapacity ).start( numberSavages );
        else
            new DiningSavagesSemaphore( potCapacity ).start( numberSavages );

    }
}
