package com.java.multiThread.demo14;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by mgupta on 8/20/15.
 */
// Callable and future: These classes allow return value and exceptions from threads
public class App {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newCachedThreadPool();

        long startTime = System.currentTimeMillis();

        /*executorService.submit(new Runnable() {
            public void run() {
                Random random = new Random();
                int duration = random.nextInt(5000);

                System.out.println("Starting...");

                try {
                    Thread.sleep(duration);
                    System.out.println("Slept : " + duration + " secs");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Finished...");

            }
        });*/

        Future<Integer> future = executorService.submit(new Callable<Integer>() {

            // Callable runs call method
            public Integer call() throws Exception {

                System.out.println("Starting..");

                Random random = new Random();
                int duration = random.nextInt(5000);

                Thread.sleep(duration);

                if (duration > 3000) {
                    throw new IllegalStateException("Slept too long..");
                }


                System.out.println("Finished..");

                return duration;
            }
        });

        executorService.shutdown();

        Thread.sleep(2000);

        // verify if task is completed
        if (future.isDone()) {
            System.out.println("Thread finished execution in : " + (System.currentTimeMillis() - startTime) + " milliSecs");
        } else {
            System.out.println("Thread run in progress ...");
        }

        try {
            // Exception thrown in thread is invoked on future.get()
            System.out.println("Thread slept for : " + future.get());

        } catch (IllegalStateException e) {
            System.out.println("Exception : " + e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
