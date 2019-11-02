package me.beautyofalgorithm;

public class LinkedList {

  class LinkedNode {
    int value;
    LinkedNode next;

    public LinkedNode(int value) {
      this.value = value;
      next = null;
    }

    public boolean hasNext() {
      return this.next != null;
    }
  }

  private LinkedNode head = null;

  public void add(LinkedNode node) {
    // need to check if node exists
    if (head == null) {
      head = node;
      return;
    }
    LinkedNode current = head;
    while (current.hasNext()) {
      current = current.next;
    }
    current.next = node;
  }

  public void remove(LinkedNode node) {
    // need to check if node exists
    // remove the head, then make head.next is new head, it can ignore if head.next is null.
    // need to remember the current and previous node, if the current node is to be removed,
    // make previous.next points to current.next, it can ignore if current.next is null.
  }
}
