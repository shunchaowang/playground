package me.playground.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class SeedGenerator implements Runnable {

    CompletableFuture<Integer> resultCommunicator;

    public SeedGenerator(CompletableFuture<Integer> resultCommunicator) {
        this.resultCommunicator = resultCommunicator;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.printf("%s: Generating seed...\n", getClass().getName());
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int seed = (int) Math.rint(Math.random() * 10);
        System.out.printf("%s: Seed generated: %d\n", getClass().getName(), seed);
        resultCommunicator.complete(seed);
    }
}
