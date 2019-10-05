package me.playground.concurrent;

public class Calculator implements Runnable {

  /**
   * When an object implementing interface <code>Runnable</code> is used to create a thread,
   * starting the thread causes the object's <code>run</code> method to be called in that separately
   * executing thread.
   *
   * <p>The general contract of the method <code>run</code> is that it may take any action
   * whatsoever.
   *
   * @see Thread#run()
   */
  @Override
  public void run() {
    long current = 1L;
    long max = 20000L;
    long numPrimes = 0L;

    System.out.printf("Thread '%s' starts.\n", Thread.currentThread().getName());
    while (current <= max) {
      if (isPrime(current)) {
        numPrimes++;
      }
      current++;
    }

    System.out.printf(
        "Thread '%s' ends. Number of primes %d.\n", Thread.currentThread().getName(), numPrimes);
  }

  private boolean isPrime(long number) {
    if (number <= 2) return true;
    for (long i = 2; i < number; i++) {
      if (number % i == 0) return false;
    }
    return true;
  }

  public static void main(String[] args) {

    System.out.printf("Minimum priority %s\n", Thread.MIN_PRIORITY);
    System.out.printf("Normal priority %s\n", Thread.NORM_PRIORITY);
    System.out.printf("Maximum priority %s\n", Thread.MAX_PRIORITY);
    Thread thread = new Thread(new Calculator());
  }
}
