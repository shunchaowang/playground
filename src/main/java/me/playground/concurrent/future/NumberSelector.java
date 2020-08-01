package me.playground.concurrent.future;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

public class NumberSelector implements Function<List<Long>, Long> {
  /**
   * Applies this function to the given argument.
   *
   * @param longs the function argument
   * @return the function result
   */
  @Override
  public Long apply(List<Long> longs) {
    System.out.printf("%s: Step 3: Start.\n", Thread.currentThread().getName());
    long max = longs.stream().max(Long::compare).get();
    long min = longs.stream().min(Long::compare).get();
    long result = (max + min) / 2;
    System.out.printf("%s: Step 3: Result %d.\n", Thread.currentThread().getName(), result);
    return result;
  }

  public static void main(String[] args) {
    System.out.println("Main start.");
    CompletableFuture<Integer> seedFuture = new CompletableFuture<>();
    Thread seedThread = new Thread(new SeedGenerator(seedFuture));
    seedThread.start();
    System.out.println("Main: Getting the seed.");
    int seed = 0;
    try {
      seed = seedFuture.get();
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }

    System.out.print("Main: Launching the list of numbers generator\n");
    NumberListGenerator numberListGenerator = new NumberListGenerator(seed);
    CompletableFuture<List<Long>> startFuture = CompletableFuture.supplyAsync(numberListGenerator);
    System.out.println("Main: launch step 1.");
    CompletableFuture<Long> step1Future =
        startFuture.thenApplyAsync(
            list -> {
              System.out.printf("%s: Step 1: Start.\n", Thread.currentThread().getName());
              long selected = 0;
              long selectedDistance = Long.MAX_VALUE;
              long distance;
              for (Long number : list) {
                distance = Math.abs(number - 1000);
                if (distance < selectedDistance) {
                  selectedDistance = distance;
                  selected = number;
                }
              }
              System.out.printf(
                  "%s: Step 1: Result - %d\n", Thread.currentThread().getName(), selected);
              return selected;
            });

    System.out.printf("Main: Launching step 2\n");
    CompletableFuture<Long> step2Future =
        startFuture.thenApplyAsync(list -> list.stream().max(Long::compare).get());
    CompletableFuture<Void> write2Future =
        step2Future.thenAccept(
            selected -> {
              System.out.printf(
                  "%s: Step 2: Result - %d\n", Thread.currentThread().getName(), selected);
            });

    System.out.printf("Main: Launching step 3\n");
    NumberSelector numberSelector = new NumberSelector();
    CompletableFuture<Long> step3Future = startFuture.thenApplyAsync(numberSelector);
    System.out.printf("Main: Waiting for the end of the three steps\n");
    CompletableFuture<Void> waitFuture =
        CompletableFuture.allOf(step1Future, write2Future, step3Future);
    CompletableFuture<Void> finalFuture =
        waitFuture.thenAcceptAsync(
            (param) -> {
              System.out.printf("Main: The CompletableFuture example has been completed.");
            });
    finalFuture.join();
  }
}
