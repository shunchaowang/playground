package me.playground.concurrent.cyclicbarrier;

import java.util.Random;

public class MatrixMock {

    private final int[][] data;

    /**
     * Construct a matrix.
     *
     * @param row    number of rows
     * @param col    number of columns
     * @param number to search from the matrix
     */
    public MatrixMock(int row, int col, int number) {
        int counter = 0;
        data = new int[row][col];
        Random random = new Random();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                data[i][j] = random.nextInt(100);
                if (data[i][j] == number) {
                    counter++;
                }
            }
        }
        System.out.printf("Mock: there are %d occurrences of %d in generated data.\n", counter, number);
    }

    public int[] getRow(int row) {
        if ((row >= 0) && (row < data.length)) {
            return data[row];
        }
        return null;
    }
}
