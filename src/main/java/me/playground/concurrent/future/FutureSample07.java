package me.playground.concurrent.future;

import java.util.concurrent.CompletableFuture;

public class FutureSample07 {

    public static void main(String[] args) {
        {
            CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Cool");
            CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "am Slick !!!");
            CompletableFuture<Void> cf3 = cf1.thenAcceptBoth(cf2, (s1, s2) ->
                    System.out.printf("[1] [%s] %s and %s\n", Thread.currentThread().getName(), s1, s2));

            try {
                cf3.get();
            }
            catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }

        {
            CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "I am Cool");
            CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> "am Slick !!!");
            CompletableFuture<Void> cf3 = cf1.thenAcceptBothAsync(cf2, (s1, s2) ->
                    System.out.printf("[2] [%s] %s and %s\n", Thread.currentThread().getName(), s1, s2));

            try {
                cf3.get();
            }
            catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }
}
