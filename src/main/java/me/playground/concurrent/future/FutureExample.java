package me.playground.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FutureExample {

  public static void main(String[] args) {
    createFuture();
  }

  static void createFuture() {
    CompletableFuture<String> cf = CompletableFuture.completedFuture("I am Future");
    assert cf.isDone();
    try {
      System.out.printf("%s - %s\n", Thread.currentThread().getName(), cf.get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
    assert cf.getNow(null).equals("i am Future");
  }
}
