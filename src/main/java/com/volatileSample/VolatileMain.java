package com.volatileSample;

import static com.concurrency.ColorScheme.GREEN;
import static com.concurrency.ColorScheme.RED;

public class VolatileMain {
    private static volatile int counter;

    public static void main(String[] args) {
        new SimpleWritter().start();
        new SimpleReader().start();

    }

    private static class SimpleWritter extends Thread {
        @Override
        public void run() {
            int localCounter = counter;
            for (int i = 0; i < 10; i++) {
                System.out.println(GREEN + "Writter increments counter " + (localCounter));
                counter = ++localCounter;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class SimpleReader extends Thread {
        @Override
        public void run() {
            int localCounter = counter;
            while (localCounter < 10) {
                if (localCounter != counter) {
                    System.out.println(RED + "Reader reads counter " + counter);
                    localCounter = counter;
                }
            }
        }
    }


}
