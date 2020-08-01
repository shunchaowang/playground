package me.playground.currencyinpractice.chap05;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Consumer implements Runnable {
  private final BlockingQueue<Integer> queue;
  private final String name;

  public Consumer(BlockingQueue<Integer> queue, String name) {
    this.queue = queue;
    this.name = name;
  }

  @Override
  public void run() {
    Thread.currentThread().setName(name);
    for (int i = 0; i < 5; i++) {
      try {
        TimeUnit.SECONDS.sleep(8);
        int j = queue.take();
        System.out.printf(
            "[%s] - has taken %d from Queue, which has %d items and %d remaining\n",
            Thread.currentThread().getName(), j, queue.size(), queue.remainingCapacity());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
