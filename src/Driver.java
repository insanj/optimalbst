//Created by Julian Weiss for CSC172
//Part of the Dynamic Programming project
import java.util.*;
public class Driver{
	public static void main(String args[]){
		ArrayList<AssociatedProb> fig1047 = new ArrayList<AssociatedProb>();
		fig1047.add(new AssociatedProb("a", 22));
		fig1047.add(new AssociatedProb("am", 18));
		fig1047.add(new AssociatedProb("and", 20));
		fig1047.add(new AssociatedProb("egg", 5));
		fig1047.add(new AssociatedProb("if", 25));
		fig1047.add(new AssociatedProb("the", 2));
		fig1047.add(new AssociatedProb("two", 8));

		//Greedy Solution
		printDiagram("Figure 10.47", fig1047);
		Node greedyParent = greedySolution(fig1047);
		System.out.println("Greedy Solution Tree:");
		System.out.println("Comparisons: " + greedyParent.cost);
		System.out.println("Cost: " + greedyCost(greedyParent)/100);
		greedyParent.printCostStack(1);

		//DP Solution
		System.out.println("\nDynamic Solution Tree:");
		dynamicSolution(fig1047);

		for(int i = 0; i < 100; i++){
			ArrayList<AssociatedProb> random = randomDist(1 + (int)(Math.random() * 50));
			printDiagram("Randomized " + i, random);

			greedyParent = greedySolution(random);
			System.out.println("Greedy Solution Tree:");
			System.out.println("Comparisons: " + greedyParent.cost);
			System.out.println("Cost: " + greedyCost(greedyParent)/100);
			greedyParent.printCostStack(1);

			//DP Solution
			System.out.println("\nDynamic Solution Tree:");
			dynamicSolution(random);
		}
	}//end main

	public static void dynamicSolution(ArrayList<AssociatedProb> givenAPs){
		ArrayList<AssociatedProb> copy = new ArrayList<AssociatedProb>();
		for(int i = 0; i < givenAPs.size(); i++)
			copy.add(givenAPs.get(i));

		DynamicSolution dynSolver = new DynamicSolution(copy);		
		Node parent = dynSolver.solve();
		System.out.println("Calls: " + dynSolver.calls);
		System.out.println("Cost: " + ((double)parent.cost)/100);
		parent.getRoot().printCostStack(0);
    }//end method

	//Takes in ArrayList of AssociatedProbs (probabilities and word keys).
	//Runs through (iterative) and creates BST using custom Node.java class, and returns
	//the parent node of the finalized probability tree.
	public static Node greedySolution(ArrayList<AssociatedProb> givenAPs){
		AssociatedProb[] sortedAPs = givenAPs.toArray(new AssociatedProb[givenAPs.size()]);
		Arrays.sort(sortedAPs, new Comparator<AssociatedProb>(){
			@Override
			public int compare(AssociatedProb ap1, AssociatedProb ap2){
				return ap1.prob - ap2.prob;
			}
    	});

    	Stack<AssociatedProb> treeStack = new Stack<AssociatedProb>();
    	for(AssociatedProb ap : sortedAPs)
    		treeStack.push(ap);

    	return constructBST(new Node(treeStack.pop()), treeStack);
    }//end method

	public static Node constructBST(Node parent, Stack<AssociatedProb> tree){
		int comparisons = 0;
		Node root = new Node(parent);
		while(!tree.isEmpty()){
			comparisons++;
    		Node currParent = root;
    		Node apNode = new Node(tree.pop());

    		while(apNode.parent == null){
    			comparisons += 3;
    			if(apNode.getKey().compareTo(currParent.getKey()) > 0){
    				if(currParent.rightChild == null){
    					currParent.rightChild = apNode;
    					apNode.parent = currParent;
    				}//end if

    				else
    					currParent = currParent.rightChild;
    			}//end if

    			else{
    				if(currParent.leftChild == null){
    					currParent.leftChild = apNode;
    					apNode.parent = currParent;
    				}//end if

    				else
    					currParent = currParent.leftChild;
    			}//end if
    		}//end while
    	}//end while

    	root.cost = comparisons;
    	return root;
	}//end method

	public static double greedyCost(Node parent){
		return greedyCost(parent, 1);
	}

	public static double greedyCost(Node node, int level){
		double running = node.getProb() * level;
		if(node.leftChild != null)
			running += greedyCost(node.leftChild, level + 1);
		if(node.rightChild != null)
			running += greedyCost(node.rightChild, level + 1);

		return running;
	}//end method

	public static ArrayList<AssociatedProb> randomDist(int size){
		int[] all = new int[size];
		int sum = 100 - size;
		Random gen = new Random();
		for(int i = 0; i < all.length-1; i++)
			all[i] = gen.nextInt(sum);

		all[size-1] = sum;
		Arrays.sort(all);
		for(int i = size - 1; i > 0; i--)
			all[i] -= all[i-1];

		for(int i = 0; i < size; i++)
			++all[i];//65-122

		ArrayList<AssociatedProb> normalized = new ArrayList<AssociatedProb>();
		for(int i = 0; i < size; i++)
			normalized.add(new AssociatedProb(Character.toString((char)(97 + (int)(Math.random() * (122 - 96)))), all[i]));

		return normalized;
	}

	public static void printDiagram(String string, ArrayList<AssociatedProb> probs){
		String calc = new String();
		for(int i = -1; i <= string.length(); i++)
			calc += "*";
		String stars = "*****";

		System.out.println(stars + calc + stars);
		System.out.println(stars + " " + string + " " + stars);
		System.out.println(stars + calc + stars);

		for(AssociatedProb ap : probs)
			System.out.println(" " + ap.key + " | " + (double)ap.prob/100);

		System.out.println(stars + calc + stars);
	}//end method
}//end class