package me.playground.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FutureSample06 {

  public static void main(String[] args) {
    {
      CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I'm Cool");
      CompletableFuture<String> cf2 =
          cf1.thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " & am SLICK !!!"));
      CompletableFuture<Void> cf =
          cf2.thenAccept(
              msg -> System.out.printf("[1] [%s] %s\n", Thread.currentThread().getName(), msg));

      try {
        cf.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    {
      CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I'm Cool");
      CompletableFuture<String> cf2 =
          cf1.thenComposeAsync(s -> CompletableFuture.supplyAsync(() -> s + " & am SLICK !!!"));
      CompletableFuture<Void> cf =
          cf2.thenAcceptAsync(
              msg -> System.out.printf("[2] [%s] %s\n", Thread.currentThread().getName(), msg));

      try {
        cf.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }
  }
}
