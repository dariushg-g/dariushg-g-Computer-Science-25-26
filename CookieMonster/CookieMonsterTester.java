import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CookieMonsterTester {

	/**
	 * In this program Cookie Monster finds the optimal path from
	 * the upper left corner (0,0) to the lower right corner
	 * (SIZE-1,SIZE-1) in a cookie array.  The elements of
	 * the array contain cookies (a non-negative number) or barrels
	 * (-1).  On each step Cookie Monster can only go down or
	 * to the right.  He is not allowed to step on barrels.
	 * The optimal path contains the largest number of cookies.
	 */
	public static void main(String[] args)
	{
		String[] fileNames = {"cookies0.txt", "cookies2.txt"};


		Scanner kboard = new Scanner(System.in);
		System.out.print("Enter the cookies file name or press ENTER for 3 default test cases: ");
		String input = kboard.nextLine();
		if (!input.equals("")) {
			System.out.print("FileName entered: " + input + ".  ");
			fileNames = new String[1];
			fileNames[0] = input.trim();  
		}
		kboard.close();

		for (String fileName : fileNames) {
			CookieMonster monster = new CookieMonster(fileName);

			System.out.println(monster.recursiveCookies());
			
			// long start = System.currentTimeMillis();
			// int optrecursion = monster.recursiveCookies();
			// long end1 = System.currentTimeMillis();
			// System.out.println("Solved using recursion");
			// System.out.println("Optimal path has " + optrecursion + " cookies, using recursion to search.  TIME: " + (end1-start) + " milliseconds");
			// int optqueue = monster.queueCookies();
			// long end2 = System.currentTimeMillis();
			// System.out.println("Solved using queue");
			// System.out.println("Optimal path has " + optqueue + " cookies, using a queue to search.  TIME: " + (end2-end1) + " milliseconds");
			// int optstack = monster.stackCookies();
			// long end3 = System.currentTimeMillis();
			// System.out.println("Solved using stack");
			// System.out.println("Optimal path has " + optstack + " cookies, using a stack to search.  TIME: " + (end3-end2)  + " milliseconds");
		}
	}
}