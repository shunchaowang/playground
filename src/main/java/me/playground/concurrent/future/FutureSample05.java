package me.playground.concurrent.future;

import java.util.concurrent.CompletableFuture;

public class FutureSample05 {

  public static void main(String[] args) {
    {
      CompletableFuture.supplyAsync(() -> "I'm cool")
          .thenCombine(
              CompletableFuture.supplyAsync(() -> "am slick !!!"),
              (s1, s2) -> String.format("%s AND %s", s1, s2))
          .thenAccept(
              msg -> System.out.printf("[1] [%s] %s\n", Thread.currentThread().getName(), msg));
    }

    {
      CompletableFuture.supplyAsync(() -> "I'm cool")
          .thenCombineAsync(
              CompletableFuture.supplyAsync(() -> "am slick !!!"),
              (s1, s2) -> String.format("%s AND %s", s1, s2))
          .thenAcceptAsync(
              msg -> System.out.printf("[2] [%s] %s\n", Thread.currentThread().getName(), msg));
    }
  }
}
