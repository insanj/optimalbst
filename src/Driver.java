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
		System.out.println("Stack version of greedy tree:");
		greedyParent.printProbStack();

		System.out.println("Costs to access each:");
		greedyParent.printCostStack(1);

		//DP Solution
		
	}//end main

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

    	/*Node parent = new Node(new Node(treeStack.pop()));
    	Node currParent = parent;
    	while(!treeStack.isEmpty()){
    		Node child = new Node(treeStack.pop());
    		if(weightOfBranch(parent, -1, 0) > child.getProb())
	   			currParent.appendLeft(child);

   			else
   				currParent.appendRight(child);
    	}//end while*/

    	return constructBST(new Node(treeStack.pop()), treeStack);
    }//end method

    private static int weightOfBranch(Node parent, int direction, int running){
    	if(direction < 0){
    		if(parent == null)
    			return running;
    		else
    			return weightOfBranch(parent.leftChild, direction, running + parent.getProb());
    	}

    	else{
    		if(parent == null)
    			return running;
    		else
    			return weightOfBranch(parent.rightChild, direction, running + parent.getProb());
    	}
    }

	public static Node constructBST(Node parent, Stack<AssociatedProb> tree){
		Node root = new Node(parent);
		while(!tree.isEmpty()){
    		Node currParent = root;
    		Node apNode = new Node(tree.pop());

    		while(apNode.parent == null){
    			if(apNode.getProb() > currParent.getProb()){
    				if(currParent.rightChild == null){
    					currParent.rightChild = apNode;
    					apNode.parent = currParent;
    				}//end if

    				else
    					currParent = currParent.rightChild;
    			}//end if

    			if(apNode.getProb() < currParent.getProb()){
    				if(currParent.leftChild == null){
    					currParent.leftChild = apNode;
    					apNode.parent = currParent;
    				}//end if

    				else
    					currParent = currParent.leftChild;
    			}//end if
    		}//end while
    	}//end while

    	return root;
	}//end method

}//end class