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

  public void add(int value) {
    LinkedNode node = new LinkedNode(value);
    if (head == null) {
      head = node;
      return;
    }
    // need to check if value exists
    // if value exists return without doing anything
    LinkedNode current = head;
    if (current.value == value) {
      return;
    }
    while (current.hasNext()) {
      current = current.next;
    }
    current.next = node;
  }

  public void remove(int value) {
    if (head == null) return;
    // if value doesn't exist return without doing anything
    // remove the head, then make head.next is new head, it can ignore if head.next is null.
    // need to remember the current and previous node, if the current node is to be removed,
    // make previous.next points to current.next, it can ignore if current.next is null.
    LinkedNode current = head;
    // if removing head, we don't need previous node at all
    if (current.value == value) {
      head = current.next; // current.next can be null, it's become an empty list after value removed
      return;
    }
    LinkedNode previous = current;
    current = current.next;
    while (current != null) {
      if (current.value == value) { // found value, remove it
        previous.next = current.next;
        return;
      } else {
        previous = current;
        current = current.next;
      }
    }
  }

  public static void main(String[] args) {

  }
}
