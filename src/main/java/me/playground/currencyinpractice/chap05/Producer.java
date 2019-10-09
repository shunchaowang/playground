package me.playground.currencyinpractice.chap05;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Producer implements Runnable {

  private final BlockingQueue<Integer> queue;
  private final String name;

  public Producer(BlockingQueue<Integer> queue, String name) {
    this.queue = queue;
    this.name = name;
  }

  @Override
  public void run() {
    Thread.currentThread().setName(name);
    Random random = new Random();
    for (int i = 0; i < 5; i++) {
      int j = random.nextInt(20);
      try {
        TimeUnit.SECONDS.sleep(5);
        queue.put(j);
        System.out.printf(
            "[%s] - has put %d to queue, which has %d items and %d remaining\n",
            Thread.currentThread().getName(), j, queue.size(), queue.remainingCapacity());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
