package com.thread;

import java.util.Arrays;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadMain {

    private static int counter = 0;

    private static AtomicInteger sheepCount1 = new AtomicInteger(0); // w1

    private static int sheepCount2 = 0;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Thread thread = new Thread(() -> System.out.println("Thread end"));

        synchronized (thread) {
            thread.start();
            thread.wait();//waiting on another thread
        }

        System.out.println("Main end");

        //==============================================================================================================//
        ExecutorService executorService = null;
        try {
            executorService = Executors.newSingleThreadExecutor();

            Future<?> submit = executorService.submit(() -> {
                for (int j = 0; j < 500; j++) {
                    ThreadMain.counter++;
                }
            });

            submit.get(10, TimeUnit.SECONDS);
            System.out.println("Reached!");

            executorService.execute(() -> System.out.println("Print something"));
            executorService.execute(() -> {
                for (int i = 0; i < 3; i++) {
                    System.out.println("Printing i: " + i);
                }
            });
            executorService.execute(() -> System.out.println("After for()"));
            System.out.println("end");
        } catch (TimeoutException e) {
            System.out.println("Noy reached in time");
        } finally {
            if (executorService != null) {
                executorService.shutdown();
            }
        }




      /*  Object o1 = new Object();
        Object o2 = new Object();
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<?> f1 = service.submit(() -> {
            synchronized (o1) {
                synchronized (o2) {
                    System.out.println("Tortoise");
                } // t1
            }
        });
        Future<?> f2 = service.submit(() -> {
            synchronized (o2) {
                synchronized (o1) {
                    System.out.println("Hare");
                } // t2
            }
        });
        f1.get();
        f2.get();*/

        Integer i1 = Arrays.asList(1, 2, 3, 4, 5).stream()
            .findAny()
            .get();

        synchronized (i1) { // y1
            Integer i2 = Arrays.asList(6, 7, 8, 9, 10)
                .parallelStream()
                .sorted()  // y2
                .findAny().get(); // y3
            System.out.println(i1 + " " + i2);
        }

        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor(); // w2
            for (int i = 0; i < 100; i++)
                service.execute(() -> {
                    sheepCount1.getAndIncrement();
                    sheepCount2++;
                }); // w3
            Thread.sleep(100);
            System.out.println(sheepCount1 + " " + sheepCount2); ///100 and 100
        } finally {
            if (service != null)
                service.shutdown();
        }

    }

}

