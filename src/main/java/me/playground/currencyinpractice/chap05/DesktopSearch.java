package me.playground.currencyinpractice.chap05;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class DesktopSearch {

  public static void main(String[] args) {
    File file = new File(".");
    BlockingQueue<File> queue = new LinkedBlockingDeque<>(16);
    ExecutorService executor = Executors.newFixedThreadPool(10);
    System.out.printf(
        "%s - start indexing %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
    // start crawler
    File[] files = file.listFiles();
    if (files != null) {
      for (File f : files) {
        executor.submit(new Crawler(queue, f));
      }
    }
    // start indexer of 16
    for (int i = 0; i < 16; i++) {
      executor.submit(new Indexer(queue));
    }
    executor.shutdown();
  }
}
