package me.cha11sorting;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class BasicSorting {

  public void bubbleSorting(int[] a, int n) {
    if (n <= 1)
      return;

    for (int i = 0; i < n; i++) {
      boolean elementMoved = false;
      for (int j = 0; j < n - 1 - i; ++j) {
        if (a[j] > a[j + 1]) {
          int temp = a[j];
          a[j] = a[j + 1];
          a[j + 1] = temp;
          elementMoved = true;
        }
      }
      if (!elementMoved)
        break;
    }
  }

  public void insertionSorting(int[] a, int n) {
    if (n < 1)
      return;
    for (int i = 1; i < n; i++) {
      int value = a[i];
      int j = i - 1;
      while (j >= 0) {
        if (a[j] > value) {
          a[j + 1] = a[j];
          j--;
        } else {
          break;
        }
      }
      a[j + 1] = value;
    }
  }

  public void SelectionSorting(int[] a, int n) {

  }

  public static void main(String[] args) {
    // generate a random array of size
    int size = 100000;
    int[] a = new int[size];
    Random random = new Random();
    for (int i = 0; i < size; ++i) {
      a[i] = random.nextInt(size * 5);
    }
    int[] b = Arrays.copyOf(a, size);

    String generatedArray = Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(" "));
    out.printf("Original array is %s\n", generatedArray);

    BasicSorting sorting = new BasicSorting();

    // timing the sorting using bubble sorting
    long start = System.currentTimeMillis();
    sorting.bubbleSorting(a, size);
    long end = System.currentTimeMillis();
    long duration = TimeUnit.MICROSECONDS.toMillis(end - start);
    out.printf("Sorting by bubble took %d milliseconds\n.", duration);

    // timing the soring using insertion sorting
    start = System.currentTimeMillis();
    sorting.insertionSorting(b, size);
    end = System.currentTimeMillis();
    duration = TimeUnit.MILLISECONDS.toMillis(end - start);
    out.printf("Sorting by insertion took %d milliseconds\n.", duration);
  }

}