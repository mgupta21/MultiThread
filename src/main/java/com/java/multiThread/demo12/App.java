package com.java.multiThread.demo12;

/**
 * Created by mgupta
 */
public class App {

    public static void main(String[] args) throws InterruptedException {

        final Runner runner = new Runner();

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    runner.forThreadOne();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    runner.forThreadTwo();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        runner.finish();

    }
}
