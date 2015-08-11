package com.java.multiThread.demo3;

// Implementing Multi-Threading
// Method 2B

public class App {

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                for (int i=0; i<10; i++){
                    System.out.println("hello " + i);
                    try{
                        Thread.sleep(100);
                    }catch (InterruptedException ex){
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
    }
}
