import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public class tcss343 {

	public static void main(String[] args) throws IOException {
		//Read input
		Integer[][] r = getInput();
		Canoe.power(r);
		System.out.println(" ");
		
		//Create return sequence
//		Stack<Integer> sequenceBF = new Stack<Integer>();
//		Stack<Integer> sequenceDC = new Stack<Integer>();
		Stack<Integer> sequenceDP = new Stack<Integer>();

		//Run Brute Force Programming Test
//		System.out.println("Brute Force");
//		System.out.println("Minimum cost: " + Canoe.bruteForce(r, sequenceBF));
//		System.out.println(sequenceBF + "\n");
		
		 //Run Divide and Conquer Programming Test
//		 System.out.println("Divide and Conquer");
//		 System.out.println("Minimum cost: " +Canoe.divideAndConqure(r, sequenceDC));
//		 System.out.println(sequenceDC + "\n");

		// Run Dynamic Programming Test
		System.out.println("Dynamic");
		System.out.println("Minimum cost: " + Canoe.dynamic(r, sequenceDP));
		System.out.println(sequenceDP);
	}

	public static Integer[][] getInput() {
		String rowLine = null;
		int count = 0;

		// Read in first row line to determine the the size of the 2d array.
		Scanner consoleScanner = new Scanner(System.in);
		rowLine = consoleScanner.nextLine();
		Scanner stringScan = new Scanner(rowLine);

		while (stringScan.hasNext()) {
			count++;
			stringScan.next();

		}

		// Create the 2d array based off of count using assumption of n x n
		// size.

		Integer[][] postTable = new Integer[count][count];

		stringScan = new Scanner(rowLine);

		for (int i = 0; i < count; i++) {
			Integer num = stringScan.nextInt();
			postTable[0][i] = num;
		}
		stringScan.close();
		int row = 1;
		while (consoleScanner.hasNextLine()) {
			rowLine = consoleScanner.nextLine();
			stringScan = new Scanner(rowLine);

			for (int j = 0; j < count; j++) {
				try {
					Integer num = stringScan.nextInt();
					postTable[row][j] = num;
				} catch (InputMismatchException e) {
					postTable[row][j] = null;
					stringScan.next();
				}
			}
			row++;
		}
		stringScan.close();
		consoleScanner.close();
		return postTable;
	}
}
