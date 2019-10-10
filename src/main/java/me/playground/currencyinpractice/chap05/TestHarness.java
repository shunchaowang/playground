package me.playground.currencyinpractice.chap05;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TestHarness {

  private static void task() throws InterruptedException {
    int timing = new Random().nextInt(10);
    System.out.printf(
        "%s - will run %d seconds to finish the task\n", Thread.currentThread().getName(), timing);
    TimeUnit.SECONDS.sleep(timing);
    System.out.printf(
        "%s - has run %d seconds finishing the task\n", Thread.currentThread().getName(), timing);
  }

  public static void main(String[] args) {

    final int numberOfWorks = 10;
    final CountDownLatch startGate = new CountDownLatch(1);
    final CountDownLatch endGate = new CountDownLatch(numberOfWorks);

    for (int i = 0; i < numberOfWorks; i++) {
      Thread t =
          new Thread(
              new Runnable() {
                @Override
                public void run() {
                  try {
                    startGate.await();
                    task();
                    endGate.countDown();
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }
              },
              "Thread " + i);
      t.start();
    }

    long start = System.nanoTime();
    startGate.countDown();
    try {
      endGate.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    long end = System.nanoTime();
    System.out.printf(
        "%s - %d tasks run concurrently to finish %d\n",
        Thread.currentThread().getName(),
        numberOfWorks,
        TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS));
  }
}
