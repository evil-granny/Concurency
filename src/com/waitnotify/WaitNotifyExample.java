package com.waitnotify;

import static com.concurrency.ColorScheme.BLUE;
import static com.concurrency.ColorScheme.RED;

public class WaitNotifyExample {

    public static void main(String[] args) {
        Message message = new Message();
        new Thread(new Producer(message)).start();
        new Thread(new Consumer(message)).start();
    }

    private static class Producer implements Runnable {
        private final Message message;

        Producer(Message message) {
            this.message = message;
        }

        String[] text = {
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
            for (String t : text) {
                synchronized (message) {
                    System.out.println(BLUE + "Producing message" + t);
                    message.setMessage(t);
                    message.notify();
                    if (!"DONE".equals(t))
                        message.wait();
                }
                Thread.sleep(400);
            }
        }
    }

    private static class Consumer implements Runnable {
        private final Message message;
        Consumer(Message message) {
            this.message = message;
        }

        @Override
        public void run() {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        private void consume() throws InterruptedException {
            while (true) {
                Thread.sleep(400);
                synchronized (message) {
                    System.out.println(RED + "Consuming message: " + message.getMessage());
                    if (!"DONE".equals(message.getMessage())) {
                        message.notify();
                        message.wait();
                    } else
                        return;
                }
            }
        }
    }
}
