package me.playground.currencyinpractice.chap05;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Indexer implements Runnable {

  private final BlockingQueue<File> queue;
  private final ConcurrentHashMap<String, File> index;

  public Indexer(BlockingQueue<File> queue) {
    this.queue = queue;
    index = new ConcurrentHashMap<>();
  }

  private void indexFile(File file) {
    System.out.printf(
        "%s - index file %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
    index.put(file.getName(), file);
  }

  @Override
  public void run() {
    while (true) {
      try {
        indexFile(queue.take());
      } catch (InterruptedException e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
      }
    }
  }
}
