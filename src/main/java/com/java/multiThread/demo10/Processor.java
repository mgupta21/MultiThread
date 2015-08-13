package com.java.multiThread.demo10;

import java.util.Scanner;

/**
 * Created by mgupta
 */
public class Processor {

    public void produce() throws InterruptedException {

        synchronized (this) {
            System.out.println("Producer thread running....");

            // wait and not consume system resources, can only be called in synchronized block
            // It cause the synchronized block to relinquish lock to other thread
            wait();
            System.out.println("Resumed.");

        }

    }

    public void consume() throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        // Ensure produce() acquire the lock first
        Thread.sleep(2000);

        synchronized (this) {

            System.out.println("Waiting for the return key.");
            scanner.nextLine();
            System.out.println("Return key pressed.");

            // can only be called within synchronized block, cause
            // make waiting thread to wake up, but doesn't relinquish lock
            // notifyAll() notify all the threads
            notify();
            Thread.sleep(5000);

            // now will go to producer's wait() and print line "Resumed"

        }

    }


}
