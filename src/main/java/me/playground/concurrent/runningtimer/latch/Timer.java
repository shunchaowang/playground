package me.playground.concurrent.runningtimer.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Timer implements Runnable {

  // ready has n count, start 1, finish has n
  private final CountDownLatch ready, start, finish;

  public Timer(CountDownLatch ready, CountDownLatch start, CountDownLatch finish) {
    this.ready = ready;
    this.start = start;
    this.finish = finish;
  }

  @Override
  public void run() {
    Thread.currentThread().setName("Timer");
    System.out.printf(
        "%s - starting, waiting for all tasks ready\n", Thread.currentThread().getName());
    try {
      ready.await(); // wait all tasks ready
      System.out.printf(
          "%s - firing the gun to start the race\n", Thread.currentThread().getName());
      long startTime = System.nanoTime();
      start.countDown(); // make all tasks run
      finish.await(); // wait all tasks finish
      long finishTime = System.nanoTime();
      System.out.printf(
          "%s - all tasks finishing in %d\n",
          Thread.currentThread().getName(),
          TimeUnit.SECONDS.convert(finishTime - startTime, TimeUnit.NANOSECONDS));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
