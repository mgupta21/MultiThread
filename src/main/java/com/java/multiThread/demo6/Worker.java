package com.java.multiThread.demo6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {

    private static Random random = new Random();

    private static List<Integer> list1 = new ArrayList<Integer>();
    private static List<Integer> list2 = new ArrayList<Integer>();

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting....");

        long start = System.currentTimeMillis();

        Thread t1 = new Thread(new Runnable() {
        public void run() {

                try {
                    process();
                } catch (InterruptedException e) {}
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {

                try {
                    process();
                } catch (InterruptedException e) {}
            }
        });

        // Synchronized stageOne and stageTwo methods ensures each list has 2000 items but since one intrinsic lock of a object worker
        // is shared among threads, so  only one thread has access to object while other is waiting. this makes system to slow as run
        // time is doubled. To solve this we can have code level locks rather than object level locks i.e. make 2 locks
        // one for each method stageOne and stageTwo so that while one thread is accessing say stageOne the other access the stageTwo.
        // This will make the system run time to half. (A thread can have only one lock object at a given time)
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        long end = System.currentTimeMillis();

        System.out.println("Time Taken : " + (end-start));
        System.out.println("List1 : " + list1.size() + " List2 : " + list2.size());

    }

    public static /*synchronized*/ void stageOne() throws InterruptedException {

        synchronized (lock1){
            Thread.sleep(1);
            list1.add(random.nextInt(100));
        }

    }

    public static /*synchronized*/ void stageTwo() throws InterruptedException {

        synchronized (list2){
            Thread.sleep(1);
            list2.add(random.nextInt(100));
        }

    }

    public static void process() throws InterruptedException {
        for (int i=0; i<1000; i++){
            stageOne();
            stageTwo();
        }
    }

}
