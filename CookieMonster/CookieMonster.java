import java.io.*;
import java.util.*;

// You are allowed (and expected!) to use either Java's ArrayDeque or LinkedList class to make
// stacks and queues


public class CookieMonster {

	private int[][] cookieGrid;
	private int numRows;
	private int numCols;

	// Constructs a CookieMonster from a file with format:
	// numRows numCols
	// <<rest of the grid, with spaces in between the numbers>>
	public CookieMonster(String fileName) {
		int row = 0;
		int col = 0;
		try {
			Scanner input = new Scanner(new File(fileName));

			numRows = input.nextInt();
			numCols = input.nextInt();
			cookieGrid = new int[numRows][numCols];

			for (row = 0; row < numRows; row++)
				for (col = 0; col < numCols; col++)
					cookieGrid[row][col] = input.nextInt();

			input.close();
		} catch (Exception e) {
			System.out.print("Error creating maze: " + e.toString());
			System.out.println("Error occurred at row: " + row + ", col: " + col);
		}

	}

	public CookieMonster(int[][] cookieGrid) {
		this.cookieGrid = cookieGrid;
		this.numRows = cookieGrid.length;
		this.numCols = cookieGrid[0].length;
	}

	// You may find it VERY helpful to write this helper method. Or not!
	private boolean validPoint(int row, int col) {
		return !(row < 0 || col < 0 || row >= this.numRows || col >= this.numCols
				|| this.cookieGrid[row][col] < 0);
	}

	/*
	 * RECURSIVELY calculates the route which grants the most cookies. Returns the maximum number of
	 * cookies attainable.
	 */
	public int recursiveCookies() {
		return recursiveCookies(0, 0);
	}

	// Returns the maximum number of cookies edible starting from (and including)
	// cookieGrid[row][col]
	public int recursiveCookies(int row, int col) {
		var scout = new OrphanScout(row, col, this.cookieGrid[row][col]);
		return recursive_cookies(scout);
	}

	private int recursive_cookies(OrphanScout orphan) {
		int row = orphan.getEndingRow(), col = orphan.getEndingCol();

		if (validPoint(row, col + 1) && validPoint(row + 1, col)) {
			return Math.max(
					recursive_cookies(new OrphanScout(row + 1, col,
							orphan.getCookiesDiscovered() + this.cookieGrid[row + 1][col])),
					recursive_cookies(new OrphanScout(row, col + 1,
							orphan.getCookiesDiscovered() + this.cookieGrid[row][col + 1])));

		}
		if (validPoint(row, col + 1)) {
			return Math.max(orphan.getCookiesDiscovered(), recursive_cookies(new OrphanScout(row,
					col + 1, orphan.getCookiesDiscovered() + this.cookieGrid[row][col + 1])));
		}
		if (validPoint(row + 1, col)) {
			return Math.max(orphan.getCookiesDiscovered(),
					recursive_cookies(new OrphanScout(row + 1, col,
							orphan.getCookiesDiscovered() + this.cookieGrid[row + 1][col])));
		}
		return orphan.getCookiesDiscovered();
	}


	/*
	 * Calculate which route grants the most cookies using a QUEUE. Returns the maximum number of
	 * cookies attainable.
	 */
	/* From any given position, always add the path right before adding the path down */
	public int queueCookies() {
		var queue = new ArrayDeque<OrphanScout>();
		var start = new OrphanScout(0, 0, cookieGrid[0][0]);
		queue.addLast(start);
		var max = start.getCookiesDiscovered();

		while (!queue.isEmpty()) {
			var popped = queue.removeFirst();
			int total = popped.getCookiesDiscovered();


			max = Math.max(max, total);

			int row = popped.getEndingRow(), col = popped.getEndingCol();

			if (validPoint(row, col + 1))
				queue.addLast(new OrphanScout(row, col + 1, total + cookieGrid[row][col + 1]));
			if (validPoint(row + 1, col))
				queue.addLast(new OrphanScout(row + 1, col, total + cookieGrid[row + 1][col]));
		}

		return max;
	}

	/*
	 * Calculate which route grants the most cookies using a stack. Returns the maximum number of
	 * cookies attainable.
	 */
	/* From any given position, always add the path right before adding the path down */
	public int stackCookies() {
		var stack = new ArrayDeque<OrphanScout>();
		var start = new OrphanScout(0, 0, this.cookieGrid[0][0]);
		stack.push(start);
		var max = 0;

		while (!stack.isEmpty()) {
			var popped = stack.pop();
			int total = popped.getCookiesDiscovered();
			max = Math.max(max, total);

			int row = popped.getEndingRow(), col = popped.getEndingCol();

			if (validPoint(row, col + 1))
				stack.push(new OrphanScout(row, col + 1, total + cookieGrid[row][col + 1]));
			if (validPoint(row + 1, col))
				stack.push(new OrphanScout(row + 1, col, total + cookieGrid[row + 1][col]));

		}

		return max;
	}

}
