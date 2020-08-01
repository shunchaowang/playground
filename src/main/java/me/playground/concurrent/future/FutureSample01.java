package me.playground.concurrent.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FutureSample01 {

  public static void main(String[] args) {
    {
      CompletableFuture<Void> cf =
          CompletableFuture.runAsync(
              () -> {
                System.out.printf("[%s] I am cool\n", Thread.currentThread().getName());
              });

      try {
        cf.get();
      } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
      }
    }

      {
          CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> {
              System.out.printf("[%s] Am Awesome\n", Thread.currentThread().getName());
              return null;
          });
          try {
              cf.get();
          } catch (InterruptedException | ExecutionException e) {
              e.printStackTrace();
          }
      }

      {
          ExecutorService executor = Executors.newSingleThreadExecutor();

          CompletableFuture cf = CompletableFuture.supplyAsync(() -> {
              System.out.printf("[%s] And am Smart\n", Thread.currentThread().getName());
              return null;
          }, executor);

          executor.shutdown();

          try {
              cf.get();
          }
          catch (Exception ex) {
              ex.printStackTrace(System.err);
          }
      }
  }
}
