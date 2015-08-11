package com.java.multiThread.demo4;

import java.util.Scanner;

// Thread Synchronization : Method 1 : Using volatile keyword

// Java main() code is run in a thread. Whenever we call the start() of thread
// then java creates another thread. so 2 threads namely main() and start()
// are executed concurrently
public class App {

    public static void main (String[] args) {

        Processor proc1 = new Processor();
        proc1.start();

        System.out.println("Press return key to stop ....");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        proc1.shutdown();

    }

}

class Processor extends Thread {

    // Volatile keyword prevents a variable state from being cached in a thread when
    // its state is being modified by another thread. i.e start() thread may
    // ignore the current state of a variable isRunning if its not defined volatile.
    // or in other words volatile keyword ensures that current state of variable
    // is visible to all threads.
    private volatile boolean isRunning = true;


    @Override
    public void run() {

        while (isRunning){
            System.out.println("hello");

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

    public void shutdown(){
        isRunning = false;
    }
}
