
public class SinglyLinkedListTester {
    public static void main(String[] args) {
        SinglyLinkedList<String> mylist = new SinglyLinkedList<String>();
        SinglyLinkedList<String> list = new SinglyLinkedList<String>();


        mylist.add(0, "B");
        mylist.add(1, "C");
        mylist.add(2, "D");
        mylist.add(3, "F");
        mylist.add(4, "G");
        mylist.add(3, null);

        list.add("B");
        list.add("C");
        list.add("D");
        list.add("F");
        list.add("G");
        list.add(3, null);

        System.out.println(list.size());
        System.out.println(mylist.size());


    }
}
