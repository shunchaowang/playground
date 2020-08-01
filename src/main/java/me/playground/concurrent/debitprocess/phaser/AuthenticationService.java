package me.playground.concurrent.debitprocess.phaser;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class AuthenticationService implements Runnable {
  private final Phaser phaser;

  public AuthenticationService(Phaser phaser) {
    this.phaser = phaser;
  }

  @Override
  public void run() {
    Thread.currentThread().setName("Authentication Service");
    phaser.register();
    System.out.printf("[%s] - starting authenticating...\n", Thread.currentThread().getName());
    System.out.printf("[%s] - Current phase %d, registered thread %d\n", Thread.currentThread().getName(), phaser.getPhase(),
            phaser.getRegisteredParties());
    try {
      TimeUnit.SECONDS.sleep(2);
      System.out.printf("[%s] - done authentication.\n", Thread.currentThread().getName());
      phaser.arriveAndDeregister();
      System.out.printf("[%s] - Current phase %d, registered thread %d\n", Thread.currentThread().getName(), phaser.getPhase(),
              phaser.getRegisteredParties());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
