package me.playground.currencyinpractice.chap05;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class ProducerConsumer {

  public static void main(String[] args) {

    ExecutorService executor = Executors.newFixedThreadPool(10);
    System.out.printf("[%s] - starting producer and consumer\n", Thread.currentThread().getName());
    BlockingQueue<Integer> queue = new LinkedBlockingDeque<>(16);
    // I want 5 producers and 2 consumers
    Collection<Callable<Object>> callables = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      callables.add(Executors.callable(new Producer(queue, "Producer " + i)));
    }

    for (int i = 0; i < 2; i++) {
      callables.add(Executors.callable(new Consumer(queue, "Consumer " + i)));
    }

    try {
      executor.invokeAll(callables);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    executor.shutdown();
  }
}
