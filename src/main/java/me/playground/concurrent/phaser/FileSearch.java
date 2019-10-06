package me.playground.concurrent.phaser;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class FileSearch implements Runnable {

  private final String initPath;
  private final String fileExtension;
  private List<String> results;
  private Phaser phaser;

  public FileSearch(String initPath, String fileExtension, Phaser phaser) {
    this.initPath = initPath;
    this.fileExtension = fileExtension;
    this.phaser = phaser;
    results = new ArrayList<>();
  }

  private void directoryProcess(File file) {
    File list[] = file.listFiles();
    if (list != null) {
      for (File f : list) {
        if (f.isDirectory()) {
          directoryProcess(f);
        } else {
          fileProcess(f);
        }
      }
    }
  }

  private void fileProcess(File file) {
    if (file.getName().endsWith(fileExtension)) {
      results.add(file.getAbsolutePath());
    }
  }

  private void filterResults() {
    List<String> newResults = new ArrayList<>();
    long actualDate = new Date().getTime();
    for (String s : results) {
      File file = new File(s);
      long fileDate = file.lastModified();
      if (actualDate - fileDate < TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)) {
        newResults.add(s);
      }
    }
    results = newResults;
  }

  private boolean checkResults() {
    if (results.isEmpty()) {
      System.out.printf(
          "%s: Phase %d: 0 results.\n", Thread.currentThread().getName(), phaser.getPhase());
      System.out.printf(
          "%s: Phase %d: End.\n", Thread.currentThread().getName(), phaser.getPhase());
      phaser.arriveAndDeregister();
      return false;
    } else {
      System.out.printf(
          "%s: Phase %d: %d results.\n",
          Thread.currentThread().getName(), phaser.getPhase(), results.size());
      phaser.arriveAndAwaitAdvance();
      return true;
    }
  }

  private void showInfo() {
    for (int i = 0; i < results.size(); i++) {
      File file = new File(results.get(i));
      System.out.printf("%s: %s\n", Thread.currentThread().getName(), file.getAbsolutePath());
    }
    phaser.arriveAndAwaitAdvance();
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
    phaser.arriveAndAwaitAdvance();
    System.out.printf("%s: Starting.\n", Thread.currentThread().getName());

    File file = new File(initPath);
    if (file.isDirectory()) {
      directoryProcess(file);
    }

    if (!checkResults()) {
      return;
    }
    filterResults();
    if (!checkResults()) return;
    showInfo();
    phaser.arriveAndDeregister();
    System.out.printf("%s: Work completed.\n", Thread.currentThread().getName());
  }

  public static void main(String[] args) {
      Phaser phaser = new Phaser(2);
      FileSearch buildSearch = new FileSearch("./build", "java", phaser);
      FileSearch srcSearch = new FileSearch("./src", "java", phaser);

      Thread buildSearchThread = new Thread(buildSearch, "build");
      buildSearchThread.start();
      Thread srcSearchThread = new Thread(srcSearch, "src");
      srcSearchThread.start();

    try {
      buildSearchThread.join();
      srcSearchThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("Terminated: " + phaser.isTerminated());
  }
}
