import java.util.ArrayList;

public class BTNode {
	private ArrayList<Integer> state;
	private BTNode parent;
	private ArrayList<BTNode> children;
	private ArrayList<ArrayList<Integer>> moves;
	public static ArrayList<Integer> goal;
	private static int size;
	
	public BTNode(int size){
		this.size = size;
		this.generateStart();
		children = new ArrayList<BTNode>();
		generateGoal();
	}
	public BTNode(ArrayList<Integer> info,BTNode parent){
		state = new ArrayList<Integer>();
		state.addAll(info);
		this.parent = parent;
		children = new ArrayList<BTNode>();
		size = state.size();
		generateGoal();
	}
	
	public void getPossibleMoves(){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.addAll(state);
		moves = new ArrayList<ArrayList<Integer>>();
		if (temp.get(0).intValue() == 0){
			temp.set(0, new Integer(1));
			while(!compareArrays(temp,goal)){
				for(int i = temp.size()-1;i>0;i--){
					if(temp.get(i).intValue() == 0){
						temp.set(i, new Integer(1));
						if(!compareArrays(temp,state) && !moves.contains(temp) && checkValidMove(state,temp)){
							moves.add((ArrayList<Integer>) temp.clone());
						}
						break;
					}
					temp.set(i, 0);
				}
			}
		}
		else if (temp.get(0).intValue() == 1){
			do{
				for(int i = temp.size()-1;i>=0;i--){
					if(temp.get(i).intValue() == 0){
						temp.set(i, new Integer(1));
						if(!compareArrays(temp,state) && !moves.contains(temp) && temp.get(0).intValue()==0 && checkValidMove(state,temp)){
							moves.add((ArrayList<Integer>) temp.clone());
						}
						break;
					}
					temp.set(i,0);
				}
			}while(!compareArrays(temp,state));
		}
	}
	
	public static boolean compareArrays(ArrayList<Integer> arr1, ArrayList<Integer> arr2){
		if(arr1.size()!=arr2.size()) return false;

		return arr1.equals(arr2);
	}
	
	
	public static int countDifferent(ArrayList<Integer> current, ArrayList<Integer> next){
		int count = 0;
		for(int i = 0; i < current.size();i++){
			if(current.get(i).intValue()!= next.get(i).intValue()){
				count++;
			}
		}
		return count;
	}
	
	public static ArrayList<Integer> getChangedIndices(ArrayList<Integer> current, ArrayList<Integer> next){
		ArrayList<Integer> changed = new ArrayList<>();
		for(int i = 0; i < current.size();i++){
			if(current.get(i).intValue()!= next.get(i).intValue()){
				changed.add(new Integer(i));
			}
		}
		return changed;
	}
	public static boolean checkValidMove(ArrayList<Integer> current, ArrayList<Integer> next){
		if(current.get(0)== 0){
			if(countDifferent(current,next)!= 3){
				return false;
			}
		}
		else if(current.get(0) == 1){
			if(countDifferent(current,next)!= 2){
				return false;
			}
			else{
				
				for(Integer i: getChangedIndices(current,next)){
					if(current.get(i).intValue()== 0 && next.get(i).intValue()== 1 && i!=0){
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public void generateStart(){
		state = new ArrayList<Integer>();
		for (int i=0;i<size;i++){
			state.add(0);
		}
	}
	
	public static void generateGoal(){
		goal = new ArrayList<Integer>();
		for (int i=0;i<size;i++){
			goal.add(1);
		}
	}
	public ArrayList<ArrayList<Integer>> getMoves(){
		return moves;
	}
	public BTNode getParent(){
		return parent;
	}
	public ArrayList<Integer> getState(){
		return state;
	}
	public void addChildren(BTNode b){
		children.add(b);
	}
	public ArrayList<BTNode> getChildren(){
		return children;
	}
	
	public static void main(String args[]){
		BTNode node = new BTNode(5);
		System.out.println(node.getState().toString());
		node.getPossibleMoves();

		System.out.println(node.getMoves().toString());
		
		for(ArrayList<Integer> move: node.getMoves()){
			BTNode b = new BTNode(move,node);
			node.addChildren(b);
		}
		
		for(BTNode c: node.getChildren()){
			c.getPossibleMoves();
			System.out.println(c.getMoves().toString());
		}
	}
}
