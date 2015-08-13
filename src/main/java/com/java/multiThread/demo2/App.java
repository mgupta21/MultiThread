package com.java.multiThread.demo2;

// Implementing Multi-Threading
// Method 2 : Create a class that extends the Runnable interface
// and pass the instance of class to the constructor of Thread class

class Runner implements Runnable {

    public void run() {

        for (int i = 0; i < 10; i++) {
            System.out.println("hello " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
}

public class App {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runner());
        Thread t2 = new Thread(new Runner());

        t1.start();
        t2.start();


    }
}
