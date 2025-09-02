
public class MyArrayListTester {
    public static void main(String[] args) {
        MyArrayList<String> arr = new MyArrayList<>(0);
        arr.add("hello");
        arr.add("hello1");
        arr.add("hello2");

        if (!arr.remove(1).equals("hello1"))
            throw new Error("removing first index should be \"hello1\"");
        System.out.println(arr.get(1));
        System.out.println(arr.toString());
    }
}




