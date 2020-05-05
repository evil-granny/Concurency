package com.thread;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinFramework extends RecursiveTask<Double> {

    private int start;

    private int end;

    private Double[] weights;

    public ForkJoinFramework(Double[] weights, int start, int end) {
        this.start = start;
        this.end = end;
        this.weights = weights;
    }

    public static void main(String[] args) {
        Double[] weights = new Double[10];
        ForkJoinTask<?> task = new ForkJoinFramework(weights, 0, weights.length);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        // Print results
        System.out.println();
        System.out.print("Weights: ");
        Arrays.asList(weights).stream().
            forEach(d -> System.out.print(d.intValue() + " "));
    }

    protected Double compute() {
        if (end - start <= 3) {
            double sum = 0;
            for (int i = start; i < end; i++) {
                weights[i] = (double) new Random().nextInt(100);
                System.out.println("Weight: " + i);
                sum += weights[i];
            }
        } else {
            int middleWeight = start + ((end - start) / 2);
            System.out.println("[start=" + start + ",middle=" + middleWeight + ",end=" + end + "]");
            RecursiveTask<Double> doubleRecursiveTask = new ForkJoinFramework(weights, start, middleWeight);
            doubleRecursiveTask.fork();
            return new ForkJoinFramework(weights, middleWeight, end).compute() + doubleRecursiveTask.join();
            /*invokeAll(new ForkJoinFramework(weights, start, middleWeight),
                new ForkJoinFramework(weights, middleWeight, end));*/
        }
        return 0.0;
    }
}
