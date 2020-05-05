package com.concurrency.synch;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedBlocks {
    private int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private int[] b = {0, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    private List<Integer> intList1 = new ArrayList<>();
    private List<Integer> intList2 = new ArrayList<>();

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public static void main(String[] args) {
        SynchronizedBlocks sb = new SynchronizedBlocks();
        sb.copy();

    }

    private void copy() {
        long start = System.currentTimeMillis();
        Thread thread1 = new Thread(new RunnerA());
        Thread thread2 = new Thread(new RunnerB());
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + " ms");
    }

    private void copyArrayA() {
        copyArrayALock(lock1, a, intList1);
    }

    private void copyArrayALock(Object lock1, int[] a, List<Integer> intList1) {
        synchronized (lock1) {
            for (int i1 : a) {
                intList1.add(i1);
                System.out.println(intList1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private synchronized void copyArrayB() {
        copyArrayALock(lock2, b, intList2);
    }

    private class RunnerA implements Runnable {

        @Override
        public void run() {
            copyArrayA();

        }
    }

    private class RunnerB implements Runnable {

        @Override
        public void run() {
            copyArrayB();

        }
    }
}
