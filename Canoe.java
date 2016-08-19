import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Canoe {
	public Canoe (){
		
	}
	
	public void runTest(){
		//Rental Costs
		Integer[][] r = new Integer[][] {{0,2,3,7}
								,{0,0,2,4}
								,{0,0,0,2}
								,{0,0,0,0}};
		int start = 0;
		int end = r.length -1 ;
		Stack<Integer> sequence = new Stack<Integer>();
							
		//bruteForce(r, start, end);
		System.out.println("DC:" + divideAndConqure(r,sequence, end));
//		System.out.println("Dynamic Programming: " + dynamic(r, sequence));
	System.out.println(sequence);
	}

	//Dynamic programming implementation of the canoe trading problem
	public static int dynamic(Integer[][] r, Collection<Integer> sequence){
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
				if (temp <= c[i]){
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
	
	public static int divideAndConqure (Integer[][] r, Stack<Integer> s){
		return divideAndConqure(r, s, r.length-1);
	}
	
	//Recursive method for divide and conqure implementation
	private static int divideAndConqure (Integer[][] r,Stack<Integer> s, int end){
		//Base case
		if (end == 0){
			s.push(0);
			return 0;
		}
		
		int min = Integer.MAX_VALUE;
		int curValue = 0;
		for (int i = 0; i < end; i++) {
			Stack<Integer> minS = new Stack<Integer>();
			curValue = r[i][end] + divideAndConqure(r, minS,i);
			if (curValue <= min){
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
	
	@SuppressWarnings("unchecked")
	public static int bruteForce(Integer[][] r,Collection<Integer> s){
		LinkedList<Integer> seq = new LinkedList<Integer>();
		LinkedList<Integer> minSeq = new LinkedList<Integer>();
		
		int poleStart = 0;
		int poleEnd = r.length -1;
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
			
			//Handle if start Position is already at the end
			if (start == poleEnd){	
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
					//Set offset position to start position
					int j = start;
					
					//Run offset
					while (j < poleEnd){
						//Previous value of j
						int prev = j;
						
						//Add offset
						j += offset;
						
						//Handle jumping over ending pole
						if (j >= poleEnd){
							offsetTotal += r[prev][poleEnd];
							offsetSeq.add(poleEnd);
							break;
						}
						offsetTotal += r[prev][j];
						offsetSeq.add(j);
					}//End offset while loop
					//Set minimum
					if (offsetTotal < min){
						min = offsetTotal;
						minSeq = (LinkedList<Integer>) offsetSeq.clone();
					}
				}//End offset for loop
			}//end starting position loop
		}
		s.addAll(minSeq);
		return min;
	}
	
	
	@SuppressWarnings("unchecked")
	public static int bruteForce2(Integer[][] r,Collection<Integer> s){
		LinkedList<Integer> seq = new LinkedList<Integer>();
		LinkedList<Integer> minSeq = new LinkedList<Integer>();
		
		int n = r.length;
		//Loop through all starting positions
			//Then offset the number of jumps after the starting position
			//Evaluate cost at the end of each offset loop
		
		
		//Handle start and end at same postion 
		if (n <= 1){
			return 0;
		}
		
		//Cost from first pole to starting position
		int startPosCost = r[0][n-1];
		
		//Minimum cost
		int min = Integer.MAX_VALUE;
		
		//r[
		//Loop through starting position
		for (int start = n-1; start > 0; start--){
			//Get starting position total
			startPosCost = r[0][start];
//			seq.clear();
//			seq.add();
//			seq.add(start);
			
			//r[j][i]+c[j];
			//Handle if start Position is already at the end
			if (start == n-1){	
				//Set new minimum cost if first pole to start position is less than current min
				if (startPosCost < min){
					min = startPosCost;
					minSeq = (LinkedList<Integer>) seq.clone();
				}
			} else {
				//Loop through offsets
				for(int offset = 1; (start + offset) <= n-1; offset++){
					//Set offset total to that of the first pole to stating position
					int offsetTotal = startPosCost;
					LinkedList<Integer> offsetSeq = (LinkedList<Integer>) seq.clone();
					//Set offset position to start position
					int j = start;
					
					//Run offset
					while (j < n-1){
						//Previous value of j
						int prev = j;
						
						//Add offset
						j += offset;
						
						//Handle jumping over ending pole
						if (j > n-1){
							offsetTotal += r[prev][n-1];
							offsetSeq.add(n-1);
							break;
						}
						offsetTotal += r[prev][j];
						offsetSeq.add(j);
					}//End offset while loop
					//Set minimum
					if (offsetTotal < min){
						min = offsetTotal;
						minSeq = (LinkedList<Integer>) offsetSeq.clone();
					}
				}//End offset for loop
			}//end starting position loop
		}
		s.addAll(minSeq);
		return min;
	}
	
	
	
	
	
	    /**
	     * @param args the command line arguments
	     */
	    public static void power(Integer[][] r) {
	    	int n = r.length;
	        LinkedList<Integer> orgSet = new LinkedList<>();
	        
//	        Integer[][] r = {{0,2,3,7,6},
//	                         {0,0,2,4,3},
//	                         {0,0,0,2,7},
//	                         {0,0,0,0,2},
//	                         {0,0,0,0,0}};

	        for (int i = 1; i < n-1; i++) {
	            orgSet.add(i);
	        }

	        LinkedList<LinkedList<Integer>> theSet = powerSet(orgSet);
	       

//	        Iterator<LinkedList<Integer>> inter = theSet.iterator();
//	        
//	        System.out.println("This is the list of sets");
//	        while (inter.hasNext()) {
//	            LinkedList<Integer> tempSet = inter.next();
//	            Iterator<Integer> tempInt = tempSet.iterator();
//	            System.out.print("{");
//	            while (tempInt.hasNext()) {
//	                System.out.print(tempInt.next());
//	            }
//	            System.out.println("}");
//	        }
	         LinkedList<Integer> sequenceSet = new LinkedList<Integer>();
	         int theMin = minCost(theSet, r, sequenceSet);
	         
	         System.out.println("The Min is: " + theMin);
	         
	         Iterator<Integer> damn = sequenceSet.iterator();
	      
	         while(damn.hasNext()) {
	             System.out.print(damn.next());
	         }
	    }

	    public static LinkedList<LinkedList<Integer>> powerSet(LinkedList<Integer> originalSet) {
	        LinkedList<LinkedList<Integer>> sets = new LinkedList<LinkedList<Integer>>();
	        if (originalSet.isEmpty()) {
	            sets.add(new LinkedList<Integer>());
	            return sets;
	        }
	        List<Integer> list = new ArrayList<Integer>(originalSet);
	        Integer head = list.get(0);
	        LinkedList<Integer> rest = new LinkedList<Integer>(list.subList(1, list.size()));
	        for (LinkedList<Integer> set : powerSet(rest)) {
	            LinkedList<Integer> newSet = new LinkedList<Integer>();
	            newSet.add(head);
	            newSet.addAll(set);
	            sets.add(newSet);
	            sets.add(set);
	        }
	        return sets;
	    }
	    
	    public static int minCost(LinkedList<LinkedList<Integer>> theList, Integer [][] r, LinkedList<Integer> theSet) {
	        Integer min = Integer.MAX_VALUE;
	        int index = 0; 
	        int indexCounter = 0;
	        Iterator<LinkedList<Integer>> inter = theList.iterator();
	        
	        while (inter.hasNext()) {
	            LinkedList<Integer> tempSet = inter.next();
	            Iterator<Integer> tempInt = tempSet.iterator();
	            tempSet.addFirst(new Integer(0));
	            tempSet.addLast(new Integer(r.length - 1));
	            
//	            while (tempInt.hasNext()) {
	                int tempCost = 0;
	                
	                //System.out.println("This is the Size: " + tempSet.size());
	                for (int k = 0; k < tempSet.size()-1; k++) {
	                    //System.out.println("This is the coords " + tempSet.get(k) + " and " + tempSet.get(k+1));
	                    //System.out.println(tempSet.get(k) + "-" + tempSet.get(k+1) + ":" +r[tempSet.get(k)][tempSet.get(k+1)]);
	                	tempCost += r[tempSet.get(k)][tempSet.get(k+1)];
	                    //System.out.println("TempCost " + tempCost);
	                }
	                if (tempCost < min) {
	                    min = tempCost;
	                    index = indexCounter;
	                }
	                indexCounter++;
	                //System.out.println("\n This is the end of the loop \n");
//	            }
	            
	           
	        }
	        for (Integer i : theList.get(index)) {
	            theSet.add(i);
	        }
	        return min;
	    }
}
