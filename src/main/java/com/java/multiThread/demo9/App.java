package com.java.multiThread.demo9;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by mgupta
 */
public class App {

    // This queue is thread safe and can be called by multiple threads without synchronization
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue(10);

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {

            public void run() {
                try {
                    producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {

            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        // Add wait until thread finish, before exiting
        t1.join();
        t2.join();

    }

    private static void producer() throws InterruptedException {

        Random random = new Random();

        while (true) {
            // Queue size is 10, when the queue is full then put waits until an item is consumed before adding to queue
            queue.put(random.nextInt(100));
        }
    }

    private static void consumer() throws InterruptedException {

        Random random = new Random();

        while (true) {
            Thread.sleep(100);

            // Randomly choose whether to take value from queue, one in every 10 times on an average
            if (random.nextInt(10) == 0) {

                // If there is no value in queue take will wait, and take value when added to queue
                Integer val = queue.take();
                System.out.println("Taken value: " + val + "; Queue size: " + queue.size());
            }
        }
    }
}
