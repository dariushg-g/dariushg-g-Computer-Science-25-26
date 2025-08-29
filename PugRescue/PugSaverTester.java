import java.util.ArrayList;

public class PugSaverTester {


    public static void main(String[] args) {
        Dog dog = new Dog("Bob", "Retriever");
        Dog dog1 = new Dog("Bob", "Pug");
        Dog dog2 = new Dog("Bob", "Pug");

        Dog dog3 = new Dog("Bob", "Poodle");
        Dog dog4 = new Dog("Bob", "Shepherd");

        ArrayList<Dog> list = new ArrayList<>();
        list.add(dog);
        list.add(dog1);
        list.add(dog2);
        list.add(dog3);
        list.add(dog4);

        PugSaver.rescuePugs(list);

        System.out.println(list);
    }

}
