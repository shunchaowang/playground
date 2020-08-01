package me.playground.concurrent.debitprocess.phaser;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Phaser;

public class Main {

    public static void main(String[] args) {
        Phaser phaser = new Phaser();
        AuthenticationService as = new AuthenticationService(phaser);
        BalanceService bs = new BalanceService(phaser);
        System.out.printf("[%s] - Current phase %d, registered thread %d\n", Thread.currentThread().getName(), phaser.getPhase(),
                phaser.getRegisteredParties());
        phaser.register();
        System.out.printf("[%s] - Current phase %d, registered thread %d\n", Thread.currentThread().getName(), phaser.getPhase(),
                phaser.getRegisteredParties());
        ForkJoinPool.commonPool().submit(as);
        ForkJoinPool.commonPool().submit(bs);
        System.out.printf("[%s] - Current phase %d, registered thread %d\n", Thread.currentThread().getName(), phaser.getPhase(),
                phaser.getRegisteredParties());

        System.out.printf("[%s] - Current phase %d, registered thread %d\n", Thread.currentThread().getName(), phaser.getPhase(),
                phaser.getRegisteredParties());
        phaser.arriveAndAwaitAdvance();
        System.out.printf("[%s] - Current phase %d, registered thread %d\n", Thread.currentThread().getName(), phaser.getPhase(),
                phaser.getRegisteredParties());
        System.out.printf("[%s] - processing transaction...\n", Thread.currentThread().getName());
        ForkJoinPool.commonPool().shutdown();
    }

}
