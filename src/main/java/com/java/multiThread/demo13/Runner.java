package com.java.multiThread.demo13;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by mgupta on 8/18/15.
 */
// Demo deadlock
public class Runner {

    private Account a = new Account();
    private Account b = new Account();

    private Lock lockA = new ReentrantLock();
    private Lock lockB = new ReentrantLock();

    public void acquireLocks(Lock x, Lock y) throws InterruptedException {

        while (true) {
            boolean isLockYAcquired = false;
            boolean isLockXAcquired = false;

            try {
                isLockXAcquired = x.tryLock();
                isLockYAcquired = y.tryLock();
            } finally {
                if (isLockXAcquired && isLockYAcquired) {
                    // if both the locks are acquired then break the loop and the thread may proceed
                    return;
                } else {
                    // if one of the lock is acquired then thread should release the acquired lock
                    if (isLockXAcquired) {
                        x.unlock();
                    } else if (isLockYAcquired) {
                        y.unlock();
                    }
                    // Wait ample time to allow other thread to acquire the lock
                    Thread.sleep(5);
                }
            }
        }

    }

    public void forFirstThread() throws InterruptedException {

        Random random = new Random();

        for (int i = 0; i < 10000; i++) {

            /*lockA.lock();
            lockB.lock();*/

            acquireLocks(lockA, lockB);

            try {
                Account.transfer(a, b, random.nextInt(100));
            } finally {
                lockA.unlock();
                lockB.unlock();
            }
        }

    }

    public void forSecondThread() throws InterruptedException {

        Random random = new Random();

        for (int i = 0; i < 10000; i++) {

            // Deadlock
            // lockB.lock();
            // lockA.lock();

            // Notice both methods have acquired the lock in the same order, if the order was different then
            // program can end up in deadlock where each method is waiting for other to release the lock
            /*lockA.lock();
            lockB.lock();*/

            // though the locks are tried to be acquired in different order, acquireLock ensures
            // a thread has either both locks or none of them to prevent deadlock
            acquireLocks(lockB, lockA);

            try {
                Account.transfer(b, a, random.nextInt(100));
            } finally {
                // Release the locks after task
                lockA.unlock();
                lockB.unlock();
            }
        }

    }

    public void finished() {
        System.out.println("Account A balance : " + a.getBalance());
        System.out.println("Account B balance : " + b.getBalance());
        System.out.println("Total balance : " + (b.getBalance() + a.getBalance()));
    }

}
