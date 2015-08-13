package com.java.multiThread.demo12;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by mgupta
 */
public class Runner {

    int count;

    // Alternative way to synchronize code without using synchronize keyword
    public Lock lock = new ReentrantLock();

    public Condition condition = lock.newCondition();

    public void increment() {
        for (int i = 0; i < 1000; i++) {
            count++;
        }
        System.out.println("Incremented count to value of : " + count);
    }

    public void forThreadOne() throws InterruptedException {

        lock.lock();

        System.out.println("Waiting....");
        condition.await(); // Equivalent of wait()

        System.out.println("Woken up ..!!");

        try {
            increment();
        } finally { // always add unlock() in finally so even if exception happens the object is still unlocked
            lock.unlock(); // must unlock to relinquish control to other thread
        }

    }

    public void forThreadTwo() throws InterruptedException {


        lock.lock();
        Thread.sleep(1000);

        System.out.println("Press the return key!");
        new Scanner(System.in).nextLine();
        System.out.println("Got return key!");

        // Doesn't relinquish control immediately, relinquish when lock.unlock() is called
        condition.signal(); // Equivalent to notify()

        try {
            increment();
        } finally {
            lock.unlock();
        }

    }

    public void finish() {
        System.out.println("Final count value : " + count);

    }
}
