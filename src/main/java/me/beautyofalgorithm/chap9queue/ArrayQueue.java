package me.beautyofalgorithm.chap9queue;

public class ArrayQueue {

  private String items[];
  private int n;
  private int head = 0;
  private int tail = 0;

  public ArrayQueue(int capacity) {
    items = new String[capacity];
    n = capacity;
  }

  /**
   * We need check if the queue is full, when tail == n it means the queue is
   * full; but when head == tail == n, the queue is a fake full, we should
   * consider to move the elements when adding a new element.
   * 
   * @param item
   */
  public boolean enquue(String item) {
    // return if queue is full
    // todo: think about when the queue is a fake full, like head = tail
    if (tail == n) { // maybe full
      if (head == 0)
        return false;// really full
      // head is not 0, we are moving the elements
      // now there are elements from head to tail - 1, we need to move [head, tail -
      // 1] to [0, tail - head - 1]
      for (int i = head; i < tail; i++) {
        items[i - head] = items[i];
      }
      tail -= head;
      head = 0;
    }
    items[tail] = item;
    return true;

  }

  public String dequeue() {
    if (head == tail)
      return null;
    String item = items[head];
    head++;
    return item;
  }
}
