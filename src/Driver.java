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
		Node greedyParent = greedySolution(fig1047);
		System.out.println("Greedy Solution Tree:");
		System.out.println("Comparisons: " + greedyParent.cost);
		System.out.println("Cost: " + greedyCost(greedyParent)/100);
		greedyParent.printCostStack(1);

		//DP Solution
		System.out.println("Dynamic Solution Tree:");
		dynamicSolution(fig1047);
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
}//end class