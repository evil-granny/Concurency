package com.thread;

import java.util.concurrent.*;

public class CallableMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService service = null;

        try {
            service = Executors.newSingleThreadExecutor();
            Future<Integer> submit = service.submit(() -> {
                return 1;
            });
            System.out.println(submit.get());
        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
        if (service != null) {
            service.awaitTermination(1, TimeUnit.MINUTES);
            if (service.isTerminated()) {
                System.out.println("All tasks finished!");
            } else {
                System.out.println("At least one task is still running");
            }
        }

    }

}
