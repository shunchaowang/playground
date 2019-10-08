package me.playground.concurrent.future;

import java.util.concurrent.CompletableFuture;

public class FutureSample04 {

  public static void main(String[] args) {
    {
      CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I'm Cool");
      CompletableFuture<String> cf2 =
          cf1.thenApply(msg -> String.format("%s and am Super AWESOME !!!", msg));
      cf2.thenAccept(msg -> System.out.printf("[1] %s\n", msg));
    }

    {
      CompletableFuture.supplyAsync(() -> "I'm Cool")
          .thenApply(msg -> String.format("%s and am Super AWESOME !!!", msg))
          .thenAccept(msg -> System.out.printf("[2] %s\n", msg));
    }
  }
}
