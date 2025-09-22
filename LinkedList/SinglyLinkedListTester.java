
public class SinglyLinkedListTester {
    public static void main(String[] args) {
        SinglyLinkedList<String> linked_list = new SinglyLinkedList<>();
        linked_list.add("hello");
        linked_list.add("world");

        System.out.println(linked_list.get(0));
    }
}
