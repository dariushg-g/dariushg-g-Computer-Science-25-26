public class MyArrayListTester {
    public static void main(String[] args) {
        MyArrayList<String> arr = new MyArrayList<>(0);
        arr.add(0, "hello");
        System.out.println(arr.get(0));
    }
}
