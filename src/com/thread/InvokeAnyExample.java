package com.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class InvokeAnyExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        Callable<String> task1 = () -> {
            Thread.sleep(2000);
            return "Result of Task1";
        };

        Callable<String> task2 = () -> {
            Thread.sleep(1000);
            return "Result of Task2";
        };

        Callable<String> task3 = () -> {
            Thread.sleep(5000);
            return "Result of Task3";
        };


        List<Future<String>> result = null;
        try {
            result = executorService.invokeAll(Arrays.asList(task1, task2, task3));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(Future<String> future: result) {
            System.out.println(future.get());
        }
//        System.out.println(result);

        executorService.shutdown();
    }
}
