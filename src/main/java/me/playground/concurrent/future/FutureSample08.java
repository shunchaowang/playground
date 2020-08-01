package me.playground.concurrent.future;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FutureSample08 {

  private static final Random random = new Random();

  public static void randomDelay() {
    try {
      Thread.sleep(random.nextInt(500));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
      CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
          randomDelay();
          return "I am awesome";
      });

      CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
          randomDelay();
          return "I am cool";
      });

      CompletableFuture<Void> cf3 = cf1.acceptEitherAsync(cf2, msg -> {
          System.out.printf("[1] [%s] %s and am NIMBLE !!!\n", Thread.currentThread().getName(), msg);
      });

      try {
          cf3.get();
      } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
      }
  }
}
