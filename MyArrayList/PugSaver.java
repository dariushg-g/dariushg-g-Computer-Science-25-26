import java.util.ArrayList;
import java.util.Objects;

public class PugSaver {

	// Moves every dog whose breed is "Pug" in the list to the back of the list
	// All non-pugs must remain in the same relative order they were in originally
	// and all pugs must also remain in the same relative order they were in originally
	public static void rescuePugs(MyArrayList<Dog> list) {
		MyArrayList<Dog> list1 = new MyArrayList<Dog>();
		MyArrayList<Dog> listnew = new MyArrayList<Dog>();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getBreed().equals("Pug")) {
				list1.add(list.get(i));
			} else {
				listnew.add(list.get(i));
			}
		}

		for (int i = 0; i < list1.size(); i++) {
			listnew.add(list1.get(i));
		}

		list = listnew;

		// for (int i = 0; i < list.size(); i++) {
		// list.set(i, listnew.get(i));
		// }

		// for (int i = list.size() - 1; i > 0; i--)
		// if (list.get(i).getBreed().equals("Pug"))
		// list.addLast(list.remove(i));
	}
}
