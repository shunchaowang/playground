package me.playground.concurrent.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Searcher implements Runnable {

    private final int firstRow;
    private final int lastRow;
    private final MatrixMock mock;
    private final int number;
    private final Results results;

    private final CyclicBarrier barrier;

    public Searcher(int firstRow, int lastRow, MatrixMock mock, int number, Results results, CyclicBarrier barrier) {
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.mock = mock;
        this.number = number;
        this.results = results;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        int counter;

        for (int i = firstRow; i < lastRow; i++) {
            int[] row = mock.getRow(i);
            counter = 0;
            for (int j = 0; j < row.length; j++) {
                if (row[j] == number) {
                    counter++;
                }
            }
            results.setData(i, counter);
        }
        System.out.printf("%s: processed from %d to %d lines.\n", Thread.currentThread().getName(), firstRow, lastRow);

        // await the barrier
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
