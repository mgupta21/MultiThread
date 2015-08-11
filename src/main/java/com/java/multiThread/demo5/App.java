package com.java.multiThread.demo5;

// Thread Synchronization : Method 2 : Using synchronization keyword

public class App {

    // No need to declare a variable volatile, as count variable is run in
    // synchronized block which guarantees  count's current state is visible to
    // all threads.
    private int count = 0;

    public synchronized void increment (){
        count++;
    }

    public static void main(String[] args) {

        App app = new App();
        app.work();

    }

    public void work(){

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                for (int i=0; i<10000; i++){
                    // count++;
                    increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                for (int i=0; i<10000 ; i++){
                    // count++;
                    increment();
                }
            }
        });

        t1.start();
        t2.start();

        // we add join to add some wait, to ensure the threads are finished executing
        // before the System.out.println is printed, else count might be printed
        // as 0 as t1 and t2 is executed in separate thread and before these threads
        // start the System.out.println might be printed.
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Even though the join ensures the some wait before the rest of code
        // is executed but it doesn't ensure that count will be 20000 in end as
        // some information might be lost when both threads are trying to
        // access the count variable at same time. Declaring volatile count variable though
        // might improve a bit but it won't solve the basic underlying problem
        // which is interleaving and not caching of state (as in previous demo).
        // To fix the problem we have to ensure when one thread is modifying
        // a variable no other thread is accessing it or modifying it simultaneously.
        // The issue is solved by having a synchronized method modify the variable. Every
        // java object has intrinsic lock, when we call synchronized method of an Object
        // in this case increment() method of App Object then a thread has to acquire the
        // intrinsic lock before they can access the synchronized method of the object


        System.out.println("Value of count is : " + count);

    }

}
