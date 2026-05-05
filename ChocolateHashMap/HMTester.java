public class HMTester {
    void main() {
        var hm = new ChocolateHashMap();
        hm.put("hello", new ChocolateBatch("hello", 10, "bye", 20));
        hm.put("no", new ChocolateBatch("hello", 10, "bye", 20));

        System.out.println(hm.remove("no"));

        System.out.println(hm.toString());
    }
}
