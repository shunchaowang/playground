package me.playground.concurrent.debitprocess;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FraudService implements Runnable {
    private final CountDownLatch latch;

    public FraudService(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.printf(
                "[%s] - Latch remaining %d\n", Thread.currentThread().getName(), latch.getCount());
        System.out.printf("[%s] - Starting fraud detection...\n", Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.printf("[%s] - done fraud detection...\n", Thread.currentThread().getName());
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
