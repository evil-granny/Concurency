package com.producerconsumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.concurrency.ColorScheme.GREEN;
import static com.concurrency.ColorScheme.RED;

public class ProducerConsumer {

    private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

    public static void main(String[] args) {
           new Thread(new Producer()).start();
           new Thread(new Consumer()).start();

    }

    private static class Producer implements Runnable {
        String[] message = {
                "Tratata tytytyt.",
                "dffddfdd",
                "strong vodka",
                "dark beer",
                "New Year",
                "happy year",
                "snowing winter",
                "DONE"
        };

        @Override
        public void run() {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void produce() throws InterruptedException {
            Random r = new Random();
            for (String s : message) {
                queue.put(s);
                System.out.println(GREEN + "Producing " + s + ". Queue size is " + queue.size());
                Thread.sleep(r.nextInt(2000));
            }
        }
    }

    private static class Consumer implements Runnable {

        @Override
        public void run() {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void consume() throws InterruptedException {
            Random r = new Random();
            while (true) {
                String message = queue.take();
                System.out.println(RED + "Consuming " + message + " Queue size is " + queue.size());
                if (!"DONE".equals(message)) {
                    Thread.sleep(r.nextInt(3000));
                } else
                    return;
            }
        }
    }
}
