import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class PostInput {
	private  int RAND_MAX = 20;
	
	private Random rand = new Random();
	
	public Integer[][] createTable(int theSize) {
		Integer[][] grid = new Integer[theSize][theSize];
		
		for (int col = 0; col < theSize; col++) {
			
			for (int row = 0; row < theSize; row++) {
				
				if (row == col) {
					grid[row][col] = 0;
				}
				else if (col > row) {
					grid[row][col] = null;
				}
				else {
					Integer postCost = rand.nextInt(RAND_MAX) + 1;
					grid[row][col] = postCost;
				}
			}
		}	
		return grid;
	}

	public void printTable (Integer[][] theTable, int theSize) {
		for (int col = 0; col < theSize; col++) {

			for (int row = 0; row < theSize; row++) {
				
				if (theTable[row][col] == null) {
					System.out.print("N");
				}
				else {
					System.out.print(theTable[row][col]);
				}
				System.out.print("\t");
				
			}
			System.out.println();
			System.out.println();
			

		}
	}
	/**
	 * Writes a 2d array table to a Tab delimited .txt file.
	 * 
	 * @param theTable A 2d array of Integer
	 * @param theSize The size of the n x n 2d array.
	 * @param theName The name of the saved text file (without extension).
	 */
	public void writeTable (Integer[][] theTable, int theSize, String theName) {
		try {


			File file = new File(theName + ".txt");

			
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			
			
			for (int col = 0; col < theSize; col++) {

				for (int row = 0; row < theSize; row++) {
					
					if (theTable[row][col] == null) {
						
						bw.write("N");
					}
					else {				
						bw.write(theTable[row][col].toString());
					}			
					bw.write("\t");				
				}			
				bw.write("\n");
			}
			
			
			bw.close();



		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Integer[][] readTable(String theFileName) {
		
		
        String rowLine = null;
        int count = 0;

        try {
           
            FileReader fileReader = 
                new FileReader(theFileName);

            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
            
            // Read in first row line to determine the the size of the 2d array.
            
            rowLine = bufferedReader.readLine();
            Scanner stringScan = new Scanner(rowLine);
              
            while (stringScan.hasNext()) {
            	count++;
            	stringScan.next();
            	
            }
            
            // Create the 2d array based off of count using assumption of n x n size.
            
            Integer[][] postTable = new Integer[count][count];
            
            stringScan = new Scanner(rowLine);
            
            for (int i = 0; i < count; i++) {
            	Integer num = stringScan.nextInt();
            	postTable[i][0] = num;
            	
            	
            }

            
            int row = 1;
            while((rowLine = bufferedReader.readLine()) != null) {
            	
                stringScan = new Scanner(rowLine);
                
                for (int j = 0; j < count; j++) {
                	
                	try {
                	Integer num = stringScan.nextInt();
                	postTable[j][row] = num;
                	
                	
                
                	}
                	catch(InputMismatchException e) {
                	postTable[j][row] = null;	
                	stringScan.next();
                	
                	
                	}
                	
                	
                }
                row++;
                
            }   

           
            stringScan.close();
            bufferedReader.close();     
            
            return postTable;
        }
        catch(FileNotFoundException e) {
            System.out.println(e);                
        }
        catch(IOException ioex) {
        	System.out.println(ioex);
        }
		return null;
		
		
		
	}
	
}
