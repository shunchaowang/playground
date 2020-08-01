package me.playground.concurrent;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class UnsafeTask implements Runnable {

  private Date startDate;
  /**
   * When an object implementing interface <code>Runnable</code> is used to create a thread,
   * starting the thread causes the object's <code>run</code> method to be called in that separately
   * executing thread.
   *
   * <p>The general contract of the method <code>run</code> is that it may take any action
   * whatsoever.
   *
   * @see Thread#run()
   */
  @Override
  public void run() {
    startDate = new Date();
    System.out.printf("Starting thread: %s : %s\n", Thread.currentThread().getId(), startDate);
    try {
      TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.printf("Thread finished: %s : %s\n", Thread.currentThread().getId(), startDate);
  }
}
