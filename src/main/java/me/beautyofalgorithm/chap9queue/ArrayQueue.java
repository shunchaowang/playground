package me.beautyofalgorithm.chap9queue;

public class ArrayQueue {

  private String items[];
  private int n;
  private int head;
  private int tail;

  public ArrayQueue(int capacity) {
    items = new String[capacity];
    n = capacity;
  }

  public void enquue(String item) {
    // return if queue is full
    // todo: think about when the queue is a fake full, like head = tail
  }
}