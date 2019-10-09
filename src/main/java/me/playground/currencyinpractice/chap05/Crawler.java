package me.playground.currencyinpractice.chap05;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class Crawler implements Callable<Object> {

  private final BlockingQueue<File> queue;
  private final File root;

  public Crawler(BlockingQueue<File> queue, File root) {
    this.queue = queue;
    this.root = root;
  }

  private void crawlFile(File file) {
    File[] files = file.listFiles();
    if (files != null) {
      for (File f : files) {
        // crawl if directory
        if (f.isDirectory()) {
          System.out.printf(
              "%s - %s is a directory, traverse it\n",
              Thread.currentThread().getName(), f.getAbsolutePath());
          crawlFile(f);
        } else { // put in the queue
          try {
            System.out.printf(
                "%s - %s is a file, enqueue it\n",
                Thread.currentThread().getName(), f.getAbsolutePath());
            queue.put(f);
          } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
          }
        }
      }
    }
  }

  @Override
  public Object call() throws Exception {
    crawlFile(root);
    return null;
  }
}
