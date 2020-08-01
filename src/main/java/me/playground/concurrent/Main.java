package me.playground.concurrent;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        UnsafeTask task = new UnsafeTask();
        SafeTask safeTask = new SafeTask();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(task);
            Thread threadLocal = new Thread(safeTask);
            thread.start();
            threadLocal.start();
            try{
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
