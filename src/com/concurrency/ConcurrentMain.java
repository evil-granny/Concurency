package com.concurrency;

import static com.concurrency.ColorScheme.GREEN;
import static com.concurrency.ColorScheme.YELLOW;

public class ConcurrentMain {
    public static void main(String[] args) {
        SimpleThread th1 = new SimpleThread();
        th1.start();

        System.out.println("Hello from main");

        SimpleThread th2 = new SimpleThread();
        th2.start();

        Thread th3 = new Thread(new SimpleRunner());
        th3.start();

        new Thread(()-> System.out.println("Hello from lambda Runnable")).start();


    }
}

class SimpleThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                System.out.println(YELLOW+"WARN - " + currentThread().getName() + " was interrupted");
                return;
            }
            System.out.println(GREEN+"INFO - " + currentThread().getName() + " - "+i);
        }
       /* if(Thread.interrupted()){
            return;
        }*/
    }
}

class SimpleRunner implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(YELLOW+"WARN - " + Thread.currentThread().getName() + " was interrupted");
                e.printStackTrace();
            }
            System.out.println(GREEN+"INFO - Runnable" + Thread.currentThread().getName() + " - "+i);
        }
    }
}
