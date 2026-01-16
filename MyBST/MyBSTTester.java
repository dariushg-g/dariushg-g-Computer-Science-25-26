public class MyBSTTester {
    void main() {
        MyBST<Integer> bst = new MyBST<>();
        bst.add(5);
        bst.add(3);
        bst.add(7);
        bst.add(1);
        bst.add(4);
        bst.add(6);
        bst.add(9);

        bst.remove(9);

        System.out.println(bst.toString());
    }
}
