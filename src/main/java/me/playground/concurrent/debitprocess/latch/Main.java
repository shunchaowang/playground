package me.playground.concurrent.debitprocess.latch;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * We will consider a hypothetical Debit Processing System with 3 services: - an Authentication
 * Service to verify a card number and pin; - an Account Balance Service to verify if there is
 * enough money in the account for the debit transaction; - and a Fraud Service to check for fraud
 * activity.
 */
public class Main {

  public static void main(String[] args) {
    final int count = 3;
    CountDownLatch latch = new CountDownLatch(count);
    AuthenticationService authenticationService = new AuthenticationService(latch);
    FraudService fraudService = new FraudService(latch);
    Callable<Boolean> balanceService = new BalanceService(latch);
    ExecutorService executor = Executors.newFixedThreadPool(10);
    Future<Boolean> balanceVerified = executor.submit(balanceService);
    executor.submit(authenticationService);
    executor.submit(fraudService);

    boolean proceed = false;
    try {
      proceed =
          latch.await(5, TimeUnit.SECONDS); // wait for 5 seconds, if false, decline the traction
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    try {
      if (!balanceVerified.get()) {
        System.out.printf("[%s] - Balance is not enough\n", Thread.currentThread().getName());
        System.exit(1);
      }
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    if (!proceed) {
      System.out.printf(
          "[%s] - has timed out of %d seconds\n", Thread.currentThread().getName(), 5);
    } else {
      System.out.printf(
          "[%s] - starting the transaction at %d\n",
          Thread.currentThread().getName(), new Date().getTime());
      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.printf(
          "[%s] - finishing the transaction at %d\n",
          Thread.currentThread().getName(), new Date().getTime());
    }
    executor.shutdown();
    System.out.printf(
        "[%s] - Latch remaining %d\n", Thread.currentThread().getName(), latch.getCount());
  }
}
