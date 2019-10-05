package me.playground.concurrent.cyclicbarrier;

/**
 * Store the number of occurrences of the searched number in each row of the matrix.
 */
public class Results {

    private final int[] data;

    public Results(int size) {
        data = new int[size];
    }

    /**
     * Set the value in the position of the array
     *
     * @param position the position means which row this represents
     * @param value    the occurrences of the searched number in row[position] of matrix
     */
    public void setData(int position, int value) {
        data[position] = value;
    }

    public int[] getData() {
        return data;
    }
}
