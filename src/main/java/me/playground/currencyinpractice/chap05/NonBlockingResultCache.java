package me.playground.currencyinpractice.chap05;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class NonBlockingResultCache<A, V> implements Computable<A, V> {
  private final Map<A, V> cache = new HashMap<>();
  private final Computable<A, V> computable;

  public NonBlockingResultCache(Computable<A, V> computable) {
    this.computable = computable;
  }

  @Override
  public synchronized V compute(A arg) throws InterruptedException {
    if (cache.containsKey(arg)) {
      System.out.printf(
          "%s - returns %d for %s from cache\n",
          Thread.currentThread().getName(), cache.get(arg), arg);
      return cache.get(arg);
    } else {
      V result = computable.compute(arg);
      cache.put(arg, result);
      System.out.printf(
          "%s - returns %d for %s from computing\n", Thread.currentThread().getName(), result, arg);
      return result;
    }
  }

  public static void main(String[] args) {
    String[] arrays = {"rpac", "arc", "basketball", "lifting", "weight"};
    Computable<String, BigInteger> computable = new ExpensiveFunction();
    NonBlockingResultCache<String, BigInteger> cache = new NonBlockingResultCache<>(computable);

    for (int i = 0; i < 10; i++) {
      int index = new Random().nextInt(4);
      Thread t =
          new Thread() {
            public void run() {
              try {
                cache.compute(arrays[index]);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
          };
      t.start();
    }
  }
}

interface Computable<A, V> {
  V compute(A arg) throws InterruptedException;
}

class ExpensiveFunction implements Computable<String, BigInteger> {

  @Override
  public BigInteger compute(String arg) throws InterruptedException {
    // after deep thinking
    int result = arg.length();
    TimeUnit.SECONDS.sleep(result);
    System.out.printf(
        "%s - spends %d to compute %s with a result of %d\n",
        Thread.currentThread().getName(), result, arg, result);
    return BigInteger.valueOf(result);
  }
}
