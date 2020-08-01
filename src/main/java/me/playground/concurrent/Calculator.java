package me.playground.concurrent;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

    Thread[] threads = new Thread[10];
    Thread.State[] states = new Thread.State[10];

    for (int i = 0; i < 10; i++) {
      threads[i] = new Thread(new Calculator());
      threads[i].setName("My Thread " + i);
      if ((i % 2) == 0) {
        threads[i].setPriority(Thread.MAX_PRIORITY);
      } else {
        threads[i].setPriority(Thread.MIN_PRIORITY);
      }
    }

    // write the result to a file
    try (FileWriter fileWriter = new FileWriter("./build/log.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter)) {
      for (int i = 0; i < 10; i++) {
        printWriter.println("Main: Status of Thread " + i + ":" + threads[i].getState());
        states[i] = threads[i].getState();
      }

      for (int i = 0; i < 10; i++) {
        threads[i].start();
      }

      boolean finish = false;
      while (!finish) {
        for (int i = 0; i < 10; i++) {
          if (threads[i].getState() != states[i]) {
            writeThreadInfo(printWriter, threads[i], states[i]);
            states[i] = threads[i].getState();
          }
        }
        finish = true;
        for (int i = 0; i < 10; i++) {
          finish = finish && (threads[i].getState() == Thread.State.TERMINATED);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void writeThreadInfo(PrintWriter pw, Thread thread, Thread.State state) {
    pw.printf("Main : Id %d - %s\n", thread.getId(), thread.getName());
    pw.printf("Main : Priority: %d\n", thread.getPriority());
    pw.printf("Main : Old State: %s\n", state);
    pw.printf("Main : New State: %s\n", thread.getState());
    pw.printf("Main : ************************************\n");
  }
}
