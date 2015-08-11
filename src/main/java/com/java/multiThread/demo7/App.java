package com.java.multiThread.demo7;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) throws InterruptedException {

        // Thread pool is like having # (2 in this case) of workers in a factory.
        // Allot/submit tasks to executor that workers will work on
        // When one worker finishes a task it works on the next/new task
        // Thread pool recycles threads to avoid overhead needed to create new thread
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i<5; i++){
            executorService.submit(new Processor(i));
        }

        // Doesn't shutdown immediately and waits for all tasks to complete. Stops accepting new tasks immediately.
        executorService.shutdown();

        System.out.println("All tasks are submitted.");

        // Immediate termination after 1 hour if tasks have not completed by then
        executorService.awaitTermination(1, TimeUnit.HOURS);

        System.out.println("All tasks completed");


    }
}

class Processor implements Runnable {

    private int id;

    public Processor(int id){
        this.id=id;
    }

    public void run() {

        System.out.println("Starting task with ID : " + id);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Completed task with ID : " + id);

    }
}
