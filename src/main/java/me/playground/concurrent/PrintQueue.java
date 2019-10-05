package me.playground.concurrent;

import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** Semaphore synchronization mechanism */
public class PrintQueue {

  private final int numOfPrinters = 3;
  private final boolean[] freePrinters;
  // the lock for the printers array
  private final Lock lockPrinters;
  // the semaphore for all shared resources
  private final Semaphore semaphore;

  public PrintQueue() {
    freePrinters = new boolean[numOfPrinters];
    for (int i = 0; i < numOfPrinters; i++) {
      freePrinters[i] = true;
    }
    lockPrinters = new ReentrantLock();
    semaphore = new Semaphore(numOfPrinters);
  }

  public void printJob() {
    try {
      semaphore.acquire();
      int assignedPrinter = getPrinter();
      long duration = (long) (Math.random() * 10);
      System.out.printf(
          "%s - %s: PrintQueue: Printing a job on printer %d during %d seconds\n",
          new Date(), Thread.currentThread().getName(), assignedPrinter, duration);
      TimeUnit.SECONDS.sleep(duration);
      freePrinters[assignedPrinter] = true;
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      semaphore.release();
    }
  }

  private int getPrinter() {
    int result = -1;
    try {
      lockPrinters.lock();
      for (int i = 0; i < numOfPrinters; i++) {
        if (freePrinters[i]) {
          freePrinters[i] = false;
          return i;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lockPrinters.unlock();
    }
    return -1;
  }
}
