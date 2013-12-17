//Created by Julian Weiss for CSC172
//Part of the Dynamic Programming project
public class Node{
	public Node parent, leftChild, rightChild;
	public Object data;
	public int cost, index;
	public boolean empty;

	/* Constructors */
	public Node(){
		empty = true;
	}

	public Node(Object gd){
		data = gd;
	}

	public Node(int gi, Object gd){
		index = gi;
		data = gd;
	}

	public Node(Node copy){
		parent = copy.parent;
		leftChild = copy.leftChild;
		rightChild = copy.rightChild;
		data = copy.data;
		index = copy.index;
	}

	/* Functional Methods */
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

	/* Setters */
	public void setParent(Node gp){
		parent = gp;
		empty = false;
	}

	public void setLeft(Node left){
		leftChild = left;
		empty = false;
	}

	public void setRight(Node right){
		rightChild = right;
		empty = false;
	}

	public void setData(Object gd){
		data = gd;
		empty = false;
	}

	public void setProb(int gp){
		if(data != null)
			((AssociatedProb)data).prob = gp;
		empty = false;
	}

	/* Getters */
	public int getProb(){
		return ((AssociatedProb)data).prob;
	}

	public String getKey(){
		return ((AssociatedProb)data).key;
	}
	
	public Node getRoot(){
		if(parent == null)
			return this;
		else
			return getRoot(parent);
	}

	private Node getRoot(Node currParent){
		if(currParent.parent != null)
			return getRoot(currParent.parent);
		else
			return currParent;
	}

	/* Print Methods */
	public void printCostStack(int level){
		String empty = new String();
		for(int i = 0; i < level; i++)
			empty += " ";

		if(data == null)
			System.out.println(empty + "$");

		else{
			System.out.println(empty + data);

			if(leftChild != null)
				leftChild.printCostStack(level + 1);

			else
				System.out.println(empty + " $");

			if(rightChild != null)
				rightChild.printCostStack(level + 1);

			else
				System.out.println(empty + " $");
		}//end else
	}//end method

	@Override
	public String toString(){
		return data.toString();
	}
}//end class