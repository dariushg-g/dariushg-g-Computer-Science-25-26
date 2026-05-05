import java.util.HashSet;

public class Looper {
    public static <E> boolean containsLoop(ListNode<E> node) {
        HashSet<ListNode<E>> map = new HashSet<ListNode<E>>();

        while (node != null) {
            if (map.contains(node))
                return true;
            map.add(node);
            node = node.getNext();
        }

        return false;
    }

    public static <E> ListNode<E> findMiddleValue(ListNode<E> head) {
        ListNode<E> prev = head;
        while (head != null) {
            head = head.getNext().getNext();
            prev = head.getNext();
        }
        return prev;
    }

    public static <E> ListNode<E> findKthFromEnd(ListNode<E> head, int k) {
        int diff = 0;
        ListNode<E> prev = head;
        while (head != null)
            if (diff++ < k) {
                head = head.getNext().getNext();
                prev = head.getNext();
            } else {
                head = head.getNext();
                prev = head.getNext();
            }
        return prev;
    }
}

