package me.playground.concurrent.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;

public class Grouper implements Runnable {

    private final Results results;

    public Grouper(Results results) {
        this.results = results;
    }

    @Override
    public void run() {
        int finalResult = 0;
        System.out.println("Grouper: processing results...");
        int[] data = results.getData();
        for (int number : data) {
            finalResult += number;
        }
        System.out.printf("Grouper: %s - total result: %d\n", Thread.currentThread().getName(), finalResult);
    }

    public static void main(String[] args) {

        final int rows = 10000;
        final int col = 1000;
        final int search = 5;
        final int participants = 5;
        final int linesPerParticipant = rows / participants;

        MatrixMock mock = new MatrixMock(rows, col, search);
        Results results = new Results(rows);
        Grouper grouper = new Grouper(results);

        CyclicBarrier barrier = new CyclicBarrier(participants, grouper);
        Searcher[] searchers = new Searcher[participants];

        for (int i = 0; i < participants; i++) {
            searchers[i] = new Searcher(i * linesPerParticipant,
                    i * linesPerParticipant + linesPerParticipant, mock, search, results, barrier);
            Thread thread = new Thread(searchers[i]);
            thread.start();
        }

        System.out.printf("Main: %s - the main thread has finished.\n", Thread.currentThread().getName());
    }
}
