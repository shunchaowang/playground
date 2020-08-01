package me.beautyofalgorithm.chap9queue;

public class LinkedQueue {
  private LinkedNode head = null, tail = null;
  private int capacity;
  private int count = 0;

  public LinkedQueue(int capacity) {
    this.capacity = capacity;
  }

  /**
   * check if capacity has reached, return false if full
   * 
   * @param value
   * @return
   */
  public boolean enqueue(String value) {
    if (count == capacity)
      return false; // full
    LinkedNode node = new LinkedNode(value);
    // set head and tail if enqueuing the 1st element
    if (count == 0) {
      head = tail = node;
    } else {
      tail.next = node;
      tail = tail.next;
    }
    count++;
    return true;
  }

  public String dequeue() {
    if (count == 0)
      return null;
    String result = head.value;
    head = head.next;
    count--;
    return result;
  }

  private class LinkedNode {
    private String value;
    private LinkedNode next;

    public LinkedNode(String value) {
      this.value = value;
      next = null;
    }
  }

}