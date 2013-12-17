//Created by Julian Weiss for CSC172
//Part of the Dynamic Programming project
public class Node{
	public Node parent, leftChild, rightChild;
	public Object data;
	public int nodesBelow;

	public Node(Object gd){
		data = gd;
		nodesBelow = 0;
	}

	public Node(Node copy){
		parent = copy.parent;
		leftChild = copy.leftChild;
		rightChild = copy.rightChild;
		data = copy.data;
		nodesBelow = copy.nodesBelow;
	}

	public void setLeft(Node left){
		leftChild = left;
		nodesBelow++;
	}

	public void setRight(Node right){
		rightChild = right;
		nodesBelow++;
	}

	public void appendLeft(Node left){
		if(leftChild == null)
			setLeft(left);

		Node running = leftChild;
		while(running.leftChild != null)
			running = running.leftChild;
		running.setLeft(left);
	}

	public void appendRight(Node right){
		if(rightChild == null)
			setRight(right);

		Node running = rightChild;
		while(running.rightChild != null)
			running = running.rightChild;
		running.setRight(right);
	}

	public int getProb(){
		return ((AssociatedProb)data).prob;
	}


	public String getKey(){
		return ((AssociatedProb)data).key;
	}
	

	public void printProbTree(){
		System.out.println("     " + data + "\n" + leftChild + "\t" + rightChild);

		if(leftChild != null)
			leftChild.printProbTree();

		if(rightChild != null)
			rightChild.printProbTree();
	}//end method

	public void printProbStack(){
		AssociatedProb prob = (AssociatedProb)data;
		if(leftChild != null || rightChild != null)
			System.out.println(prob + "(." + prob.prob + "): " + leftChild + " " + rightChild);

		if(leftChild != null)
			leftChild.printProbStack();

		if(rightChild != null)
			rightChild.printProbStack();
	}//end method

	public void printCostStack(int level){
		String empty = " ";
		for(int i = 1; i < level; i++)
			empty += " ";

		System.out.println(empty + data);

		if(leftChild != null)
			leftChild.printCostStack(++level);

		else
			System.out.println(empty + " $");

		if(rightChild != null)
			rightChild.printCostStack(++level);

		else
			System.out.println(empty + " $");
	}//end method

	@Override
	public String toString(){
		return data.toString();
	}
}//end class