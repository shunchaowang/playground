package me.playground.concurrent.runningtimer;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import me.playground.concurrent.runningtimer.latch.Task;
import me.playground.concurrent.runningtimer.latch.Timer;

public class Main {

  private static final int N_TASKS = 10;

  public static void main(String[] args) {
    latchTimer();
  }

  private static void latchTimer() {

    // create a fixed thread pool
    ExecutorService executor = Executors.newCachedThreadPool();
    CountDownLatch ready = new CountDownLatch(N_TASKS),
        start = new CountDownLatch(1),
        finish = new CountDownLatch(N_TASKS);
    executor.submit(new Timer(ready, start, finish));
    Random random = new Random();
    for (int i = 0; i < N_TASKS; i++) {
      executor.submit(new Task(ready, start, finish, random.nextInt(10)));
    }
    executor.shutdown();
  }
}
