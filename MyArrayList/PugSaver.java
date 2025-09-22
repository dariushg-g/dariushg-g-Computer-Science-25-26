import java.util.ArrayList;

public class PugSaver {

	// Moves every dog whose breed is "Pug" in the list to the back of the list
	// All non-pugs must remain in the same relative order they were in originally
	// and all pugs must also remain in the same relative order they were in originally
	public static void rescuePugs(ArrayList<Dog> list) {
		ArrayList<Dog> list1 = new ArrayList<Dog>();
		ArrayList<Dog> listnew = new ArrayList<Dog>();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getBreed().equals("Pug")) {
				list1.add(list.get(i));
			} else {
				listnew.add(list.get(i));
			}
		}
 
		listnew.addAll(list1);

		for (int i = 0; i < listnew.size(); i++) {
			list.set(i, listnew.get(i));
		}

		// for (int i = 0; i < list.size(); i++) {
		// list.set(i, listnew.get(i));
		// }

		// for (int i = list.size() - 1; i > 0; i--)
		// if (list.get(i).getBreed().equals("Pug"))
		// list.addLast(list.remove(i));
	}
}
