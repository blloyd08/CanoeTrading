import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

public class Canoe {
	public Canoe (){
		
	}
	
	public void runTest(){
		//Rental Costs
		int[][] r = new int[][] {{0,2,3,7}
								,{0,0,2,4}
								,{0,0,0,2}
								,{0,0,0,0}};
		int start = 0;
		int end = r.length -1 ;
		Stack<Integer> sequence = new Stack<Integer>();
								
		//bruteForce(r, start, end);
		//System.out.println("DC:" + divideAndConqure(sequence,r,end));
		System.out.println("Dynamic Programming: " + dynamic(r, sequence));
		System.out.println(sequence);
	}
	

	//Dynamic programming implementation of the canoe trading problem
	public int dynamic(int[][] r, Collection<Integer> sequence){
		//Number of posts
		int n = r.length;
		//Ensure collection is empty
		sequence.clear();
		
		//Minimum cost
		int[] c = new int[n];
		//Sequences - each column is the minimum sequence for that post
		boolean[][] s = new boolean[n][n];
		
		//Initialize values
		c[0] = 0;
		s[0][0] = true;
		
		//Loop through each post and set it as the end starting at post 1
		for (int i = 1; i < n; i++){
			//Initialize minimum cost to be first post to end post
			c[i] = r[0][i];
			int minS = 0;
			
			//Loop through all starting positions from 1 to end post
			for (int j = 1; j < i; j++){
				int temp = r[j][i]+c[j];
				//Check minimum
				if (temp < c[i]){
					c[i] = temp;
					minS = j;
				}
			}//End j loop
			
			//Copy minimum sequence to current sequence
			for(int pole = 0; pole <= minS; pole++)
				s[pole][i] = s[pole][minS];
				
			//Add this pole to the sequence;
			s[i][i] = true;
		}//end column loop (i)
		
		//Return the minimum sequence
		for (int i = 0; i < n; i++){
			if(s[i][n-1])
				sequence.add(i);
		}
		
		//Return the minimum cost to pole 0 to n-1
		return c[n-1];
	}
	
	//Recursive method for divide and conqure implementation
	public int divideAndConqure (Stack<Integer> s, int[][] r, int end){
		//Base case
		if (end == 0){
			s.push(0);
			return 0;
		}
		
		int min = Integer.MAX_VALUE;
		int curValue = 0;
		for (int i = 0; i < end; i++) {
			Stack<Integer> minS = new Stack<Integer>();
			curValue = r[i][end] + divideAndConqure(minS,r,i);
			if (curValue < min){
				//Set minimum value
				min = curValue;
				//Set minimum sequence
				s.clear();
				s.addAll(minS);
			}
		}//End 0 to end loop
		s.push(end);
		return min;
		
	}
	
	public int bruteForce(int[][] r, int poleStart, int poleEnd){
		LinkedList<Integer> seq = new LinkedList<Integer>();
		LinkedList<Integer> minSeq = new LinkedList<Integer>();
		/*1-2-3-4 	= 0-1-2-3
		 * 1-2-4	= 0-1-3
		 * 1-3-4	= 0-2-3
		 * 1-4		= 0-3
		 */
		
		//Loop through all starting positions
			//Then offset the number of jumps after the starting position
			//Evaluate cost at the end of each offset loop
		
		
		//Handle return to same position
		if (poleStart >= poleEnd){
			return r[poleStart][poleEnd];
		}
		
		//Cost from first pole to starting position
		int startPosCost = 0;
		//Minimum cost
		int min = Integer.MAX_VALUE;
		
		//Loop through starting position
		for (int start = poleStart + 1; start <= poleEnd; start++){
			//Get starting position total
			startPosCost = r[poleStart][start];
			seq.clear();
			seq.add(poleStart);
			seq.add(start);
			System.out.println("Start: " + start);
			
			//Handle if start Position is already at the end
			if (start == poleEnd){
				System.out.println(poleStart + "-" + poleEnd);
				System.err.println(startPosCost);
				
				//Set new minimum cost if first pole to start position is less than current min
				if (startPosCost < min){
					min = startPosCost;
					minSeq = (LinkedList<Integer>) seq.clone();
				}
			} else {
				//Loop through offsets
				for(int offset = 1; (start + offset) <= poleEnd; offset++){
					//Set offset total to that of the first pole to stating position
					int offsetTotal = startPosCost;
					LinkedList<Integer> offsetSeq = (LinkedList<Integer>) seq.clone();
					System.out.println("Offset: " + offset);
					//Set offset position to start position
					int j = start;
					System.out.println(poleStart + "-" + start);
					
					//Run offset
					while (j < poleEnd){
						//Previous value of j
						int prev = j;
						
						//Add offset
						j += offset;
						
						//Handle jumping over ending pole
						if (j > poleEnd){
							System.out.println(prev + "-" + poleEnd);
							offsetTotal += r[prev][poleEnd];
							offsetSeq.add(poleEnd);
							break;
						}
						System.out.println(prev + "-" + j);
						offsetTotal += r[prev][j];
						offsetSeq.add(j);
					}//End offset while loop
					System.err.println(offsetTotal);
					System.err.println(offsetSeq);
					
					//Set minimum
					if (offsetTotal < min){
						min = offsetTotal;
						minSeq = (LinkedList<Integer>) offsetSeq.clone();
					}
					
					System.out.println("end offset");
				}//End offset for loop
			}//end starting position loop
			
			System.out.println("end starting position");
		}
		System.err.println("Minimum:" + min + "-" + minSeq);
		return 0;
	}
}
