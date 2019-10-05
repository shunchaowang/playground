package me.playground.concurrent;

public class PrintJob implements Runnable {

  private PrintQueue printQueue;

  public PrintJob(PrintQueue printQueue) {
    this.printQueue = printQueue;
  }

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
    System.out.printf("%s: is going to print a job\n", Thread.currentThread().getName());
    printQueue.printJob();
    System.out.printf("%s: The document has been printed\n", Thread.currentThread().getName());
  }

  public static void main(String[] args) {
    PrintQueue printQueue = new PrintQueue();
    Thread[] thread = new Thread[4];
    for (int i = 0; i < thread.length; i++) {
      thread[i] = new Thread(new PrintJob(printQueue), "Thread " + i);
      thread[i].start();
    }
  }
}
