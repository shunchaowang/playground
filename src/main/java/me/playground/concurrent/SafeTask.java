package me.playground.concurrent;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SafeTask implements Runnable {

  private ThreadLocal<Date> startDate = new ThreadLocal<Date>() {
    /**
     * Returns the current thread's "initial value" for this
     * thread-local variable.  This method will be invoked the first
     * time a thread accesses the variable with the {@link #get}
     * method, unless the thread previously invoked the {@link #set}
     * method, in which case the {@code initialValue} method will not
     * be invoked for the thread.  Normally, this method is invoked at
     * most once per thread, but it may be invoked again in case of
     * subsequent invocations of {@link #remove} followed by {@link #get}.
     *
     * <p>This implementation simply returns {@code null}; if the
     * programmer desires thread-local variables to have an initial
     * value other than {@code null}, {@code ThreadLocal} must be
     * subclassed, and this method overridden.  Typically, an
     * anonymous inner class will be used.
     *
     * @return the initial value for this thread-local
     */
    @Override
    protected Date initialValue() {
      return new Date();
    }
  };
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
    System.out.printf("Starting thread: %s : %s\n", Thread.currentThread().getId(), startDate.get());
    try {
      TimeUnit.SECONDS.sleep((int) Math.rint(Math.random() * 10));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.printf("Thread finished: %s : %s\n", Thread.currentThread().getId(), startDate.get());
  }
}
