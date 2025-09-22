// Represents a node of a singly-linked list.

public class ListNode<E> {

  private E value;
  private ListNode<E> next;

  public ListNode(E v) {
    value = v;
    next = null;
  }

  public ListNode(E v, ListNode<E> nx) {
    value = v;
    next = nx;
  }

  public E getValue() {
    return value;
  }

  public ListNode<E> getNext() {
    return next;
  }

  public void setValue(E v) {
    value = v;
  }

  public void setNext(ListNode<E> nx) {
    next = nx;
  }
}


