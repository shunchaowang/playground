package me.beautyofalgorithm.chap9queue;

public class CircleQueue {

  private String items[];
  private int n;
  private int head = 0;
  private int tail = 0;

  public CircleQueue(int capacity) {
    items = new String[capacity];
    n = capacity;
  }

  public boolean enqueue(String value) {
    // (tail + 1) % n == head means the queue is full
    // also tail should not be + 1, should be tail + 1 mod n
    int index = (tail + 1) % n;
    if (index == head)
      return false; // full
    items[tail] = value;
    tail = index;
    return true;
  }

  public String dequeue() {
    if (head == tail)
      return null;
    String value = items[head];
    head = (head + 1) % n;
    return value;
  }
}