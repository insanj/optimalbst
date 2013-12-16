//Created by Julian Weiss for CSC172
//Part of the Dynamic Programming project
public class AssociatedProb{
	public int prob;
	public String key;

	public AssociatedProb(String gk, int gp){
		prob = gp;
		key = gk;
	}

	@Override
	public String toString(){
		return key;
	}
}//end class