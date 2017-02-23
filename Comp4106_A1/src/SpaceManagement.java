import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class SpaceManagement {
	private SMNode start;
	
	public SpaceManagement(){
		int[][] begin = {{6,7,3},{5,0,2},{4,1,8}}; //6,7,3,5,0,2,4,1,8 3x3
		start = new SMNode(begin,null);
	}
	public SMNode getStart(){
		return start;
	}
	public SMNode depthFirstSearch(){
		Stack<SMNode> s = new Stack<SMNode>();
		for(int[][] a: start.getMoves()){
			s.push(new SMNode(a,start));
		}
		SMNode temp = s.pop();
		while(!temp.containsMove(SMNode.goal,temp.getMoves())){
			for(int[][] a: temp.getMoves()){
				if(a!=null){
					s.push(new SMNode(a,temp));
				}
			}
			temp = s.pop();
		}
		
		return new SMNode(SMNode.goal,temp);
	}
	public SMNode breadthFirstSearch(){
		Queue<SMNode> q = new ArrayDeque<SMNode>();
		for(int[][] a: start.getMoves()){
			q.add(new SMNode(a,start));
		}
		SMNode temp = q.remove();
		while(!temp.containsMove(SMNode.goal,temp.getMoves())){
			for(int[][] a: temp.getMoves()){
				if(a!=null){
					q.add(new SMNode(a,temp));
				}
			}
			temp = q.remove();
		}
		
		return new SMNode(SMNode.goal,temp);
		
	}
	public void printSuccessRoute(SMNode end){
		ArrayList<SMNode> path = new ArrayList<>();
		path.add(0,end);
		while(end.getParent()!=null){
			path.add(0,end.getParent());
			end = end.getParent();
		}
		for(SMNode s: path){
			SMNode.printState(s.getState());
			System.out.println();
		}
	}
	public static void main(String args[]){
		SpaceManagement sm = new SpaceManagement();
		System.out.println("STARTING POSITION BFS");
		SMNode end = sm.breadthFirstSearch();
		sm.printSuccessRoute(end);
		System.out.println("STARTING POSITION DFS");
		end = sm.depthFirstSearch();
		sm.printSuccessRoute(end);
	}
}
