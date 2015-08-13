package com.java.multiThread.demo11;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by mgupta
 */
public class Processor {

    private final int LIMIT = 10;
    // arrayBlockingQueue was synchronized but not LinkedList
    private LinkedList<Integer> list = new LinkedList<Integer>();
    private Object lock = new Object();

    public void produce() throws InterruptedException {

        int val = 0;

        while (true) {
            synchronized (lock) {

                while (list.size() == LIMIT) {
                    lock.wait(); // always add wait on the object that is used to lock
                }
                list.add(val++);
                lock.notify();
            }
        }

    }

    public void consume() throws InterruptedException {

        Random random = new Random();

        while (true) {

            synchronized (lock) {

                while (list.size() == 0) {
                    lock.wait();
                }

                System.out.print("List size : " + list.size());

                int val = list.removeFirst();
                System.out.println("; Consumed value : " + val);
                lock.notify(); // call notify on lock object used to lock
            }

            // Ensure more values produced than consumed
            Thread.sleep(random.nextInt(1000));
        }

    }

}
