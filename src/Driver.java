//Created by Julian Weiss for CSC172
//Part of the Dynamic Programming project
import java.util.*;
public class Driver{
	public static Scanner user = new Scanner(System.in);
	public static void main(String args[]){
		printDiagram("Dynamic Programming Project", null);
		System.out.println("Part One, Fig 10.47\nPress enter to continue...");
		user.nextLine();

		/* Part One */
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
		System.out.println(" Comparisons: " + greedyParent.cost);
		System.out.println(" Cost: " + greedyCost(greedyParent)/100);
		greedyParent.printCostStack(1);

		//DP Solution
		System.out.println("\nDynamic Solution Tree:");
		dynamicSolution(fig1047, true);

		/* Part Two and Three */
		System.out.println("\n********************************************************");
		System.out.println("Part Two/Three, 100 Randomized Distributions\nTrees will not be printed (see code to change)\nFive sets of 100 will be generated with Costs/etc,\nin order to determine average costs for each algorithm \n(see README for depiction of this data)\nPress enter to continue...");
		user.nextLine();

		HashMap<Integer, double[]> sizeToCosts = new HashMap<Integer, double[]>();
		for(int a = 1; a <= 5; a++){
			for(int i = 1; i <= 100; i++){
				double[] costs = new double[3];
				ArrayList<AssociatedProb> random = randomDist(1 + (int)(Math.random() * 99));
				printDiagram("Randomized " + a + "." + i, null);

				System.out.println("Distribution Size: " + random.size());
				greedyParent = greedySolution(random);
				System.out.println("Greedy Solution:");
				System.out.println(" Comparisons: " + greedyParent.cost);
				costs[0] = greedyCost(greedyParent)/100;
				System.out.println(" Cost: " + costs[0]);
				//greedyParent.printCostStack(1);
				
				System.out.println("Dynamic Solution:");
				costs[1] = dynamicSolution(random, false);	//true to print tree
				costs[2] = 1;

				double[] got = sizeToCosts.get(random.size());
				if(got != null){
					costs[0] += got[0];
					costs[1] += got[1];
					costs[2] += got[2];
				}

				sizeToCosts.put(random.size(), costs);
			}//end for
		}//end for

		System.out.println("\n*******************************************************************************");
		System.out.println("For random-sized sets (above), the average costs for Greedy and Dynamic are...");
		user.nextLine();

		System.out.println("Size : Greedy : Dynamic");
		for(Integer i : sizeToCosts.keySet()){
			double[] costs = sizeToCosts.get(i);
			System.out.println(i + " : " + costs[0]/costs[2] + " : " + costs[1]/costs[2]);
		}

		System.out.println("\n*********************************************************");
		System.out.println("Part Three (continued), 4 Given Distributions\nUniform (1/N), Sharply Biased (hump at 1),\nSlightly Biased (hump at N/4), Symmetrical (max at N/2)\nPress enter to continue...");
		user.nextLine();

		ArrayList<AssociatedProb> uniform = new ArrayList<AssociatedProb>();
		uniform.add(new AssociatedProb("20", 20)); // 1/5 = 0.2 = 20 in int-prob form
		uniform.add(new AssociatedProb("20", 20));
		uniform.add(new AssociatedProb("20", 20));
		uniform.add(new AssociatedProb("20", 20));
		//uniform.add(new AssociatedProb("20", 20));

		printDiagram("Uniform", uniform);
		greedyParent = greedySolution(uniform);
		System.out.println("Greedy Solution Tree:");
		System.out.println(" Comparisons: " + greedyParent.cost);
		System.out.println(" Cost: " + greedyCost(greedyParent)/100);
		greedyParent.printCostStack(1);

		System.out.println("\nDynamic Solution Tree:");
		dynamicSolution(uniform, true);

		user.nextLine();

		ArrayList<AssociatedProb> sharp = new ArrayList<AssociatedProb>();
		sharp.add(new AssociatedProb("60", 60)); // 1
		sharp.add(new AssociatedProb("20", 20));
		sharp.add(new AssociatedProb("15", 15));
		sharp.add(new AssociatedProb("5", 5));
	//	sharp.add(new AssociatedProb("10", 10));

		printDiagram("Sharply Biased", sharp);
		greedyParent = greedySolution(sharp);
		System.out.println("Greedy Solution Tree:");
		System.out.println(" Comparisons: " + greedyParent.cost);
		System.out.println(" Cost: " + greedyCost(greedyParent)/100);
		greedyParent.printCostStack(1);

		System.out.println("\nDynamic Solution Tree:");
		dynamicSolution(sharp, true);

		user.nextLine();

		ArrayList<AssociatedProb> slight = new ArrayList<AssociatedProb>();
		slight.add(new AssociatedProb("45", 45));
		slight.add(new AssociatedProb("25", 25)); // n/4
		slight.add(new AssociatedProb("15", 15));
		slight.add(new AssociatedProb("10", 10));
		slight.add(new AssociatedProb("5", 5));
	//	slight.add(new AssociatedProb("11", 11));
	//	slight.add(new AssociatedProb("10", 10));
	//	slight.add(new AssociatedProb("12", 12));

		printDiagram("Slightly Biased", slight);
		greedyParent = greedySolution(slight);
		System.out.println("Greedy Solution Tree:");
		System.out.println(" Comparisons: " + greedyParent.cost);
		System.out.println(" Cost: " + greedyCost(greedyParent)/100);
		greedyParent.printCostStack(1);

		System.out.println("\nDynamic Solution Tree:");
		dynamicSolution(slight, true);

		user.nextLine();

		ArrayList<AssociatedProb> sym = new ArrayList<AssociatedProb>();
		sym.add(new AssociatedProb("3", 3));
		sym.add(new AssociatedProb("9", 9));
		sym.add(new AssociatedProb("14", 14)); // n/2
		sym.add(new AssociatedProb("26", 26));
		sym.add(new AssociatedProb("21", 21));
		sym.add(new AssociatedProb("15", 15));
		sym.add(new AssociatedProb("9", 9));
		sym.add(new AssociatedProb("3", 3));

		printDiagram("Symmetrical", sym);
		greedyParent = greedySolution(sym);
		System.out.println("Greedy Solution Tree:");
		System.out.println(" Comparisons: " + greedyParent.cost);
		System.out.println(" Cost: " + greedyCost(greedyParent)/100);
		greedyParent.printCostStack(1);

		System.out.println("\nDynamic Solution Tree:");
		dynamicSolution(sym, true);
	}//end main

	/* Dynamic Optimal BST */
	public static double dynamicSolution(ArrayList<AssociatedProb> givenAPs, boolean printTree){
		ArrayList<AssociatedProb> copy = new ArrayList<AssociatedProb>();
		for(int i = 0; i < givenAPs.size(); i++)
			copy.add(givenAPs.get(i));

		DynamicSolution dynSolver = new DynamicSolution(copy);		
		Node parent = dynSolver.solve();
		double cost = ((double)parent.cost)/100;
		System.out.println(" Calls: " + dynSolver.calls);
		System.out.println(" Cost: " + cost);

		if(printTree)
			parent.getRoot().printCostStack(0);

		return cost;
    }//end method

	/* Greedy Optimal BST */
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

	/* Part Two Utility Method */
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

		if(probs != null){
			System.out.println("Size: " + probs.size());
			for(AssociatedProb ap : probs)
				System.out.println(" " + ap.key + " | " + (double)ap.prob/100);

			System.out.println(stars + calc + stars);
		}//end if
	}//end method
}//end class