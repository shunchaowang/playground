package me.playground.concurrent.runningtimer.latch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {

  private final CountDownLatch ready, start, finish;
  private final int running;

  public Task(CountDownLatch ready, CountDownLatch start, CountDownLatch finish, int running) {
    this.ready = ready;
    this.start = start;
    this.finish = finish;
    this.running = running;
  }

  @Override
  public void run() {
    Thread.currentThread().setName("Task " + running);
    System.out.printf("%s - ready, waiting to start\n", Thread.currentThread().getName());
    ready.countDown(); // ready and notify timer
    try {
      start.await(); // wait to start
      System.out.printf("%s - running for %d\n", Thread.currentThread().getName(), running);
      TimeUnit.SECONDS.sleep(running); // running for
      System.out.printf("%s - finishing\n", Thread.currentThread().getName());
      finish.countDown(); // notify timer
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
