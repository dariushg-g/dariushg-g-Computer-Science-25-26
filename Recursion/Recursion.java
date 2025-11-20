public class Recursion {

	// Prints the value of every node in the singly linked list with the given head,
	// but in reverse
	public static void printListInReverse(ListNode head) {
		if (head == null)
			return;
		printListInReverse(head.getNext());
		System.out.println(head.getValue().toString());
	}

	// For the given 2D array of Strings, replaces the String at index[r][c]
	// with "infected" unless the String is "vaccinated";
	// also, any Strings they are orthogonally adjacent to
	// that are not "vaccinated" will also be infected, and any adjacent to
	// them as well etc.
	// Infecting someone who is already infected has no effect
	// Trying to infect outside the confines of the grid also has no effect
	// Precondition: grid has no null entries
	public static void infect(String[][] grid, int r, int c) {
		if (grid[r][c].equals("vaccinated") || grid[r][c].equals("infected"))
			return;
		grid[r][c] = "infected";
		infect(grid, Math.max(r - 1, 0), c);
		infect(grid, Math.min(r + 1, grid.length - 1), c);
		infect(grid, r, Math.min(c + 1, grid[0].length - 1));
		infect(grid, r, Math.max(c - 1, 0));
	}

	// How many subsets are there of the numbers 1...n
	// that don't contain any consecutive integers?
	// e.g. for n = 4, the subsets are {}, {1}, {2}, {3}, {4},
	// {1, 3}, {1, 4}, {2, 4}
	// The other subsets of 1,2,3,4 that DO contain consecutive integers are
	// {1,2}, {2,3}, {3,4}, {1,2,3}, {1,2,4}, {1,3,4}, {1,2,3,4}
	// Precondition: n > 0
	public static long countNonConsecutiveSubsets(int n) {
		if (n <= 2)
			return n + 1;
		return countNonConsecutiveSubsets(n - 1) + countNonConsecutiveSubsets(n - 2);
	}

	// A kid at the bottom of the stairs can jump up 1, 2, or 3 stairs at a time.
	// How many different ways can they jump up n stairs?
	// Jumping 1-1-2 is considered different than jumping 1-2-1
	// Precondition: n > 0
	public static long countWaysToJumpUpStairs(int n) {
		if (n <= 2)
			return n;
		if (n == 3)
			return 4;
		return countWaysToJumpUpStairs(n - 1) + countWaysToJumpUpStairs(n - 2)
				+ countWaysToJumpUpStairs(n - 3);
	}

	// Everything above this line does NOT require a recursive helper method
	// ----------------------------------
	// Everything below this line requires a recursive helper method
	// Any recursive helper method you write MUST have a comment describing:
	// 1) what the helper method does/returns
	// 2) your description must include role of each parameter in the helper method

	// Prints all the subsets of str on separate lines
	// You may assume that str has no repeated characters
	// For example, subsets("abc") would print out "", "a", "b", "c", "ab", "ac",
	// "bc", "abc"
	// Order is your choice
	public static void printSubsets(String str) {
		printSubsetsHelper("", 0, str);
		for (var c : str.toCharArray()) {
			System.out.println(c);
		}
		System.out.println("");
	}

	// prints subsets of str, printing c with index i of str
	private static void printSubsetsHelper(String c, int i, String str) {
		if (i == str.length())
			return;
		if (c.length() > 0)
			System.out.println(c + str.charAt(i));
		printSubsetsHelper(c + str.charAt(i), i + 1, str);
		printSubsetsHelper(c, i + 1, str);
	}

	// a b c
	// a b c
	// b a c
	// c b a
	// b a c
	//

	// List contains a single String to start.
	// Prints all the permutations of str on separate lines
	// You may assume that str has no repeated characters
	// For example, permute("abc") could print out "abc", "acb", "bac", "bca",
	// "cab", "cba"
	// Order is your choice
	public static void printPermutations(String str) {
		printPermutationsHelper(str, 0);
	}

	// prints all permutations of s keeping indices 0-c constant
	private static void printPermutationsHelper(String s, int c) {
		if (c == s.length()) {
			System.out.println(s);
			return;
		}
		for (int i = c; i < s.length(); i++) {
			var tempStr = s.toCharArray();
			var temp = tempStr[c];
			tempStr[c] = tempStr[i];
			tempStr[i] = temp;
			printPermutationsHelper(new String(tempStr), c + 1);
		}
	}

	// Performs a mergeSort on the given array of ints
	// Precondition: you may assume there are NO duplicates!!!
	public static void mergeSort(int[] ints) {
		mergeSortHelper(0, ints.length - 1, ints);
	}

	private static void mergeSortHelper(int a, int b, int ints[]) {
		if (a < b) {
			int m = (a + b) / 2;
			System.out.println(b);
			mergeSortHelper(a, m, ints);
			mergeSortHelper(m + 1, b, ints);
			merge(a, b, m, ints);
		}
	}

	// merges the two sorted subarrays from a<->m and m+1<->b back into ints where m is (a + b) / 2
	private static void merge(int a, int b, int m, int ints[]) {
		int l1 = m - a + 1, l2 = b - m;
		int[] L1 = new int[l1], L2 = new int[l2];
		for (int i = 0; i < L1.length; i++)
			L1[i] = ints[i + a];
		for (int i = 0; i < L2.length; i++)
			L2[i] = ints[i + m + 1];
		int i = 0, j = 0, k = a;
		while (i < L1.length && j < L2.length)
			if (L1[i] < L2[j])
				ints[k++] = L1[i++];
			else
				ints[k++] = L2[j++];
		while (i < L1.length)
			ints[k++] = L1[i++];
		while (j < L2.length)
			ints[k++] = L2[j++];
	}


	// Performs a quickSort on the given array of ints
	// Use the middle element (index n/2) as the pivot
	// Precondition: you may assume there are NO duplicates!!!
	public static void quickSort(int[] ints) {
		quickSortHelper(0, ints.length - 1, ints);
	}

	// quick sorts from index a to b in ints by iterating two pointers from each side a and b - 1
	// and then swapping indices in ints until all values are matched in order in relation to
	// ints[(a + b) / 2]
	private static void quickSortHelper(int a, int b, int[] ints) {
		if (b <= a)
			return;
		var piv = ints[(a + b) / 2];
		int i = a, j = b;
		while (i <= j) {
			while (ints[i] < piv)
				i++;
			while (ints[j] > piv)
				j--;
			if (i <= j) {
				swap(i, j, ints);
				i++;
				j--;
			}
		}
		if (j > a)
			quickSortHelper(a, j, ints);
		if (i < b)
			quickSortHelper(i, b, ints);
	}

	// swaps index a to index b in array ints
	private static void swap(int a, int b, int[] ints) {
		var temp = ints[a];
		ints[a] = ints[b];
		ints[b] = temp;
	}


	// Prints a sequence of moves (one on each line)
	// to complete a Towers of Hanoi problem:
	// disks start on tower 0 and must end on tower 2.
	// The towers are number 0, 1, 2, and each move should be of
	// the form "1 -> 2", meaning "take the top disk of tower 1 and
	// put it on tower 2" etc.

	enum Peg {
		ZERO, ONE, TWO;

		public String toString() {
			switch (this) {
				case ZERO:
					return "0";
				case ONE:
					return "1";
				case TWO:
					return "2";
			}
			return "";
		}
	}

	public static void solveHanoi(int startingDisks) {
		solveHanoiHelper(startingDisks, Peg.ZERO, Peg.TWO, Peg.ONE);
	}

	// prints the statements that would move curr disks from p0 to p1
	private static void solveHanoiHelper(int curr, Peg p0, Peg p1, Peg p2) {
		if (curr == 0)
			return;
		solveHanoiHelper(curr - 1, p0, p2, p1);
		System.out.println(p0 + " -> " + p1);
		solveHanoiHelper(curr - 1, p2, p1, p0);
	}

	// You are partaking in a scavenger hunt!
	// You've gotten a secret map to find many of the more difficult
	// items, but they are only available at VERY specific times at
	// specific places. You have an array, times[], that lists at which
	// MINUTE an item is available. Times is sorted in ascending order.
	// Items in the ScavHunt are worth varying numbers of points.
	// You also have an array, points[], same length as times[],
	// that lists how many points each of the corresponding items is worth.
	// Problem is: to get from one location to the other takes 5 minutes,
	// so if there is an item, for example, available at time 23 and another
	// at time 27, it's just not possible for you to make it to both: you'll
	// have to choose!
	// (but you COULD make it from a place at time 23 to another at time 28)
	// Write a method that returns the maximum POINTS you can get.
	// For example, if times = [3, 7, 9]
	// and points = [10, 15, 10]
	// Then the best possible result is getting the item at time 3 and the one at
	// time 9
	// for a total of 20 points, so it would return 20.
	public static int scavHunt(int[] times, int[] points) {
		return scavHuntHelper(0, times, points);
	}

	// returns the larger value between points[curr] + scavhunt for next time at least 5 mins away
	// and scavhunt of the next index
	private static int scavHuntHelper(int curr, int[] times, int[] points) {
		if (curr >= times.length)
			return 0;
		return Math.max(
				points[curr] + scavHuntHelper(get_index_of_5_away(curr, times), times, points),
				scavHuntHelper(curr + 1, times, points));
	}

	// returns the nearest index from times that is not within 5 of the time of the given index curr
	private static int get_index_of_5_away(int curr, int[] times) {
		var i = curr;
		while (i < times.length && times[i] - times[curr] < 5)
			i++;
		return i;
	}
}
