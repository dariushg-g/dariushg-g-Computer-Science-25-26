
public class MyArrayListTester {
    public static void main(String[] args) {
        MyArrayList<String> arr = new MyArrayList<>();

        for (int i = 0; i < 10000000; i++) {
            arr.add("hello");
        }

        String s = arr.toString();
        System.out.println(s.charAt(0));
    }
}


