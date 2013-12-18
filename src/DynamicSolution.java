//Created by Julian Weiss for CSC172
//Part of the Dynamic Programming project
import java.util.*;
public class DynamicSolution{
	public ArrayList<AssociatedProb> dynProbs = new ArrayList<AssociatedProb>();
	public int calls;

	public DynamicSolution(ArrayList<AssociatedProb> gl){
		dynProbs = gl;
	}

	public Node solve(){	
		calls = 0;
		AssociatedProb[] currProbs = dynProbs.toArray(new AssociatedProb[dynProbs.size()]);
		Node[][] dynTable = new Node[currProbs.length][currProbs.length];
		return solve(currProbs, 0, currProbs.length-1, dynTable);
	}//end method

	private Node solve(AssociatedProb[] probs, int left, int right, Node[][] table){
		calls++;

		Node minTree = new Node();
		if(left <= right)
			if(table[left][right] != null)
				return table[left][right];

		int min = 0;
		for(int i = left; i <= right; i++)
			min += probs[i].prob;

		if(right <= left || left >= right){
			if(left != right)
				return new Node();

			else{
				minTree = new Node(probs[right]);
				minTree.cost = min;
			}
		}//end if

		else{
			for(int i = left; i <= right; i++){
				Node leftBranch = solve(probs, left, i-1, table);
				Node rightBranch = solve(probs, i+1, right, table);
				Node completeRoot = new Node(probs[i]);
				completeRoot.leftChild = leftBranch.getRoot();
				completeRoot.rightChild = rightBranch.getRoot();

				Node complete = new Node(completeRoot);
				complete.cost = (leftBranch.cost + rightBranch.cost + min);

				if(minTree.empty || minTree.cost > complete.cost)
					minTree = complete;
			}//end for
		}//end else

		table[left][right] = minTree;
		return minTree;
	}//end method
}//end class