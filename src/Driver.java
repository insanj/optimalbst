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
		System.out.println("Cost: " + parent.cost);
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
    }//end method

	public static Node constructBST(Node parent, Stack<AssociatedProb> tree){
		Node root = new Node(parent);
		while(!tree.isEmpty()){
    		Node currParent = root;
    		Node apNode = new Node(tree.pop());

    		while(apNode.parent == null){
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

    	return root;
	}//end method

    /*public static int cost(int leftIndex, int rightIndex, Node[] probs, int root){
    	int sum = cost(leftIndex, rightIndex - 1, probs, root) + cost(leftIndex + 1, rightIndex, probs, root);
    	for(int i = left; i <= right; i++)
    		sum += probs[i].getProb();
    	return sum;
    }*/

    /*public static int dynCost(int left, int right, Node[] currProbs, Node[][] dynProbs){
    	if(left > right)
    		return 0;
    	if(dynProbs[left][right] != null)
    		return dynProbs[left][right].getProb();

    	int min = Integer.MAX_VALUE;
    	Node minRoot = new Node();
    	for(int i = left; i < right; i++){
    		//recursive get cost of l->r subtrees
    		int cost = dynCost(left, i-1, currProbs, dynProbs) + dynCost(right, i+1, currProbs, dynProbs);
    		for(int j = left; j <= right; j++)
    			cost += currProbs[j].getProb();
    		if(cost < min){
    			minRoot = new Node(currProbs[i]);
    			minRoot.index = i;
    			min = cost;
    		}
    	}//end for

    	((AssociatedProb)minRoot.data).prob = min;
    	dynProbs[left][right] = new Node(minRoot);

    	return min;
    }//end method

    public static void printDynTree(int left, int right, Node[] currProbs, Node[][] dynProbs, int level){
    	Node root = dynProbs[left][right];
    	int index = root.index;

    	System.out.print(root.getProb());

    	//Left Subtree
    	if(index > 0){
    		int leftA = currProbs[left].index;
    		int leftB = currProbs[index].index;
    		printDynTree(leftA, leftB, currProbs, dynProbs, ++level);
    	}

    	//Right Subtree
    	if(index < currProbs.length){
    		int rightA = currProbs[index].index;
    		int rightB = currProbs[right].index;
    		printDynTree(rightA, rightB, currProbs, dynProbs, ++level);
    	}
    }//end method*/
}//end class