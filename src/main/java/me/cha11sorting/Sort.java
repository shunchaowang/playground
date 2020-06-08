package me.cha11sorting;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Sort {

  // bubble sorting
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

  // insertion sorting
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

  // quick sorting
  public void quickSorting(int[] a, int n) {

    if (n <= 1)
      return;
    sortQuick(a, 0, n - 1);
  }

  private void sortQuick(int[] a, int p, int r) {
    if (p >= r)
      return;
    int position = partition(a, p, r);
    sortQuick(a, p, position - 1);
    sortQuick(a, position + 1, r);
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

    // generate a random array of size
    int size = 100000;
    int[] a = new int[size];
    Random random = new Random();
    for (int i = 0; i < size; ++i) {
      a[i] = random.nextInt(size * 5);
    }
    int[] b = Arrays.copyOf(a, size);
    int[] c = Arrays.copyOf(a, size);

    String originalArray = Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(" "));
    // out.printf("Original array is %s\n", originalArray);

    Sort sort = new Sort();

    // test bubble sorting
    long bubbleStart = System.currentTimeMillis();
    sort.bubbleSorting(a, size);
    long bubbleEnd = System.currentTimeMillis();
    long bubbleDuration = TimeUnit.MILLISECONDS.toMillis(bubbleEnd - bubbleStart);
    out.printf("Bubble sorting took %d milliseconds.\n", bubbleDuration);

    // test insertion sorting
    long insertionStart = System.currentTimeMillis();
    sort.insertionSorting(a, size);
    long insertionEnd = System.currentTimeMillis();
    long insertionDuration = TimeUnit.MILLISECONDS.toMillis(insertionEnd - insertionStart);
    out.printf("Insertion sorting took %d milliseconds.\n", insertionDuration);

    // test quick sorting
    long quickStart = System.currentTimeMillis();
    sort.insertionSorting(a, size);
    long quickEnd = System.currentTimeMillis();
    long quickDuration = TimeUnit.MILLISECONDS.toMillis(quickEnd - quickStart);
    out.printf("Quick sorting took %d milliseconds.\n", quickDuration);

  }
}