package me.cha11sorting;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class QuickSorting {

  public void quickSort(int[] a, int n) {

    if (n <= 1)
      return;
    quickSort(a, 0, n - 1);
  }

  private void quickSort(int[] a, int p, int r) {
    if (p >= r)
      return;
    int position = partition(a, p, r);
    quickSort(a, p, position - 1);
    quickSort(a, position + 1, r);
  }

  private int partition(int[] a, int p, int r) {

    int pivot = a[r];
    int i = p;
    for (int j = i; j <= r - 1; j++) {
      if (a[j] < pivot) {
        // this means one less than pivot has been found, we should make one more space
        // available on the left of pivot;
        // to prevent element movement when insertion, we just swap i and j
        // swap a[i] and a[j]
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
        // one more space!
        i++;
      }
    }

    // swap a[i] and a[r]
    int t = a[i];
    a[i] = a[r];
    a[r] = t;
    return i;
  }

  public static void main(String[] args) {
    QuickSorting quickSort = new QuickSorting();

    // generate a random array of size
    int size = 1000;
    int[] a = new int[size];
    Random random = new Random();
    for (int i = 0; i < size; ++i) {
      a[i] = random.nextInt(size * 5);
    }

    String generatedArray = Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(" "));
    out.printf("Original array is %s\n", generatedArray);

    long start = System.currentTimeMillis();
    quickSort.quickSort(a, 0, size - 1);
    long end = System.currentTimeMillis();

    long duration = TimeUnit.MICROSECONDS.toMillis(end - start);
    out.printf("Sorting by QuickSort took %d milliseconds\n.", duration);

    String sortedArray = Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(" "));
    out.printf("Sorted array is %s\n", sortedArray);
  }
}