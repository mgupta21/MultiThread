package com.java.multiThread.demo1;

// Implementing Multi-Threading
// Method 1 : Create a class that extends the Thread and implement overridden run ()

class Runner extends Thread {

    public void run (){
        for (int i=0; i<10; i++){
            System.out.println("hello " + i);
            try{
                Thread.sleep(100);
            }catch (InterruptedException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

}


public class App {

    public static void main( String[] args ) {

        Runner runner1 = new Runner();
        // Run runner using start() method to ensure program is run in its own thread. If we call run() directly
        // then the program will run in the main thread of the application and not in its own special thread.
        runner1.start();

        // runner 1 and 2 will run concurrently and not one after other
        Runner runner2 = new Runner();
        runner2.start();
    }
}
