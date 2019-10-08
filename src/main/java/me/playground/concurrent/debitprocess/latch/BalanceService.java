package me.playground.concurrent.debitprocess.latch;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BalanceService implements Callable<Boolean> {
  private final CountDownLatch latch;

  public BalanceService(CountDownLatch latch) {
    this.latch = latch;
  }

  @Override
  public Boolean call() {
    Thread.currentThread().setName("Balance Service");
    System.out.printf(
        "[%s] - Latch remaining %d\n", Thread.currentThread().getName(), latch.getCount());
    System.out.printf(
        "[%s] - Starting balance verification...\n", Thread.currentThread().getName());
    long target = (long) (Math.random() * 100);
    long balanceRemaining = 50;
    boolean result = balanceRemaining >= target;
    try {
      TimeUnit.SECONDS.sleep(1);
      System.out.printf("[%s] - done balance verification...\n", Thread.currentThread().getName());
      latch.countDown();
      if (result) {
        System.out.printf(
            "[%s] - Balance %d is enough for %d\n",
            Thread.currentThread().getName(), balanceRemaining, target);
        latch.countDown();
      } else {
        System.out.printf(
            "[%s] - Balance %d is not enough for %d\n",
            Thread.currentThread().getName(), balanceRemaining, target);
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return result;
  }
}
