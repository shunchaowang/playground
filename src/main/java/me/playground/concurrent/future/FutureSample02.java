package me.playground.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FutureSample02 {

  public static void main(String[] args) {

    {
      CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am cool");
      CompletableFuture<Void> cf2 =
          cf1.thenAccept(
              msg ->
                  System.out.printf(
                      "[1] [%s] %s and am also awesome\n", Thread.currentThread().getName(), msg));
      try {
        cf2.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    {
      ExecutorService executor = Executors.newSingleThreadExecutor();
      CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am new", executor);
      CompletableFuture<Void> cf2 =
          cf1.thenAccept(
              msg ->
                  System.out.printf(
                      "[2] [%s] %s and am also smart\n", Thread.currentThread().getName(), msg));
      executor.shutdown();
      try {
        cf2.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

    {
      CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Fast");
      CompletableFuture<Void> cf2 =
          cf1.thenAcceptAsync(
              msg ->
                  System.out.printf(
                      "[3] [%s] %s and am also Elegant\n", Thread.currentThread().getName(), msg));

      try {
        cf2.get();
      } catch (Exception ex) {
        ex.printStackTrace(System.err);
      }
    }

    {
      ExecutorService executor = Executors.newFixedThreadPool(2);

      CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Slick", executor);
      CompletableFuture<Void> cf2 =
          cf1.thenAcceptAsync(
              msg ->
                  System.out.printf(
                      "[4] [%s] %s and am also Nimble\n", Thread.currentThread().getName(), msg),
              executor);

      executor.shutdown();

      try {
        cf2.get();
      } catch (Exception ex) {
        ex.printStackTrace(System.err);
      }
    }
  }
}
