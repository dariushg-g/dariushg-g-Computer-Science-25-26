import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PugRescueTester {


	public static void main(String[] args)
	{
		try {

			ArrayList<String> breedList = new ArrayList<String>();
			
			fillWithBreeds(breedList);

			ArrayList<Dog> testSubjects = new ArrayList<Dog>();
			final boolean EFFICIENCY = false;
			//Set max to 10000000 for an efficiency check, or 100 for an accuracy check
			int max = 0;
			if (EFFICIENCY)
				max = 1000000;
			else
				max = 10000000;

			for (int i = 0; i < max; i++)
			{
				if (i % 5 == 0 || i % 17 == 0)
					testSubjects.add(new Dog("Dog"+i));
				else
					testSubjects.add(new Dog("Dog"+i, getRandomBreed(breedList)));
			}

			int banned = 0;
			if (EFFICIENCY)
				banned = 7529412;
			else
				banned = 76;

			long startTime = System.currentTimeMillis();
			PugSaver.rescuePugs(testSubjects);
			long totalTime = System.currentTimeMillis() - startTime;
			System.out.println("Total seconds to rescue pugs: " + totalTime/1000.0);

			for (int i = 0; i < banned; i++)
			{
				if (testSubjects.get(i).getBreed().equals("Pug")) {
					System.out.println("Incorrect!");
					return;
				}
			}
			for (int i = banned; i < testSubjects.size(); i++) {
				if (!testSubjects.get(i).getBreed().equals("Pug")) {
					System.out.println("Incorrect!");
					return;
				}
			}
			System.out.println("Probably correct!");
			
		} catch(FileNotFoundException e) {
			System.out.println("You must place the breeds.txt file in the proper place.");

		}
	}

	public static void fillWithBreeds(ArrayList<String> breeds) throws FileNotFoundException {
		Scanner breedReader = new Scanner(new File("breeds.txt"));
		while(breedReader.hasNext()) {
			breeds.add(breedReader.nextLine());
		}
		breedReader.close();
	}

	public static String getRandomBreed(ArrayList<String> breeds) {
		int index = (int) Math.random()*breeds.size();
		return breeds.get(index);
	}
}