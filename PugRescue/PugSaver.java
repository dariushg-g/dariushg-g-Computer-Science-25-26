import java.util.ArrayList;

public class PugSaver {
	// Moves every dog whose breed is "Pug" in the list to the back of the list
	public static void rescuePugs(ArrayList<Dog> list) {
		ArrayList<Dog> list1 = new ArrayList<Dog>();
		ArrayList<Dog> listnew = new ArrayList<Dog>();

		for (Dog dog : list) {
			if (dog.getBreed().equals("Pug")) {
				list1.add(dog);
			} else {
				listnew.add(dog);
			}
		}

		for (Dog dog : list1) {
			listnew.addLast(dog);
		}

		list = listnew;

		// for (int i = 0; i < list.size(); i++) {
		// 	list.set(i, listnew.get(i));
		// }

		// for (int i = list.size() - 1; i > 0; i--)
		// if (list.get(i).getBreed().equals("Pug"))
		// list.addLast(list.remove(i));
	}
}

