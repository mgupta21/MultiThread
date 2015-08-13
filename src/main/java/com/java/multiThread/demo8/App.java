package com.java.multiThread.demo8;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mgupta
 */
public class App {

    public static void main(String[] args) {

        // Countdown latch is a thread safe java class, which can be used by various threads without worrying about the thread synchronization
        // It makes one or more thread wait until latch reaches count of 0
        CountDownLatch latch = new CountDownLatch(10);

        ExecutorService executor = Executors.newFixedThreadPool(10);

        // creating one task per thread
        for (int i = 0; i < 10; i++) {
            executor.submit(new Processor(latch));
        }

        try {
            // It waits until countDownLatch count down to 0
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // This line is not printed until latch.await() has return
        System.out.println("Completed.");

    }

}

// Used to define the task to be performed by the thread
class Processor implements Runnable {

    // No need to use synchronized keyword to alter latch value as CountDownLatch is a thread safe class
    private CountDownLatch latch;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {

        System.out.println("Started..");

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Decreases the value defined in CountDownLatch constructor by one
        latch.countDown();

    }
}
