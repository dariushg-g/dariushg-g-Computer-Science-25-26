public class MyArrayListTester {
    public static void main(String[] args) {
        MyArrayList<String> arr = new MyArrayList<>(0);
        arr.add("hello");
        arr.add("hello1");
        arr.add("hello2");

        System.out.println(arr.remove(0));
        System.out.println(arr.get(0));
        System.out.println(arr.toString());
    }
}
