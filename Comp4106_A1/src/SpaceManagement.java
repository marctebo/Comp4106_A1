import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class SpaceManagement {
	private SMNode start;
	
	public SpaceManagement(){
		//int[][] begin = {{6,7,3},{5,0,2},{4,1,8}}; //6,7,3,5,0,2,4,1,8 3x3
		int[][] begin = {{8,4,2},{3,1,7},{6,5,0}};
		start = new SMNode(begin,null);
	}
	public SMNode getStart(){
		return start;
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

	public SMNode aStarH1(){
		Stack<SMNode> s = new Stack<SMNode>();
		for(int[][] a: start.getMoves()){
			s.push(new SMNode(a,start));
		}
		SMNode temp = s.pop();
		SMNode temp2 = s.pop();
		do{
			 if(SMNode.countIncorrect(temp2.getState())<SMNode.countIncorrect(temp.getState())){
				 temp = temp2;
			 }
			 if(s.isEmpty()){
				 break;
			 }
			 temp2 = s.pop();
		}
		while(temp2!=null);
		
		while(!temp.containsMove(SMNode.goal,temp.getMoves())){
			for(int[][] a: temp.getMoves()){
				if(a!=null){
					s.push(new SMNode(a,temp));
				}
			}
			temp = s.pop();
			temp2 = s.pop();
			do{

				 if(SMNode.countIncorrect(temp2.getState())<SMNode.countIncorrect(temp.getState())){
					 temp = temp2;
				 }
				 if(s.isEmpty()){
					 break;
				 }
				 temp2 = s.pop();
			}
			while(temp2!=null);
		}
		
		return new SMNode(SMNode.goal,temp);
	}
	
	public SMNode aStarH2(){
		Stack<SMNode> s = new Stack<SMNode>();
		ArrayList<SMNode> hold = new ArrayList<>();
		for(int[][] a: start.getMoves()){
			s.push(new SMNode(a,start));
		}
		SMNode temp = s.pop();
		SMNode temp2 = s.pop();
		do{
			 if(SMNode.getTotalDistance(temp2.getState())<SMNode.getTotalDistance(temp.getState())){
				 hold.add(temp);
				 temp = temp2;
			 }
			 else{
				 hold.add(temp2);
			 }
			 if(s.isEmpty()){
				 break;
			 }
			 temp2 = s.pop();
		}
		while(temp2!=null);
		
		while(!temp.containsMove(SMNode.goal,temp.getMoves())){
			if(!hold.isEmpty()){
				for(SMNode n: hold){
					s.push(n);
				}
			}
			for(int[][] a: temp.getMoves()){
				if(a!=null){
					s.push(new SMNode(a,temp));
				}
			}
			temp = s.pop();
			temp2 = s.pop();
			do{

				 if(SMNode.getTotalDistance(temp2.getState())<SMNode.getTotalDistance(temp.getState())){
					 hold.add(temp);
					 temp = temp2;
				 }
				 else{
					 hold.add(temp2);
				 }
				 if(s.isEmpty()){
					 break;
				 }
				 temp2 = s.pop();
			}
			while(temp2!=null);
		}
		
		return new SMNode(SMNode.goal,temp);
	}
	public SMNode aStarH3(){
		Stack<SMNode> s = new Stack<SMNode>();
		for(int[][] a: start.getMoves()){
			s.push(new SMNode(a,start));
		}
		SMNode temp = s.pop();
		SMNode temp2 = s.pop();
		do{
			 if(SMNode.countIncorrect(temp2.getState())< SMNode.countIncorrect(temp.getState())){
				 temp = temp2;
			 }
			 else if(SMNode.countIncorrect(temp2.getState())== SMNode.countIncorrect(temp.getState())){
				 if(SMNode.getTotalDistance(temp2.getState())<SMNode.getTotalDistance(temp.getState())){
					 temp = temp2; 
				 }
			 }
			 if(s.isEmpty()){
				 break;
			 }
			 temp2 = s.pop();
		}
		while(temp2!=null);
		
		while(!temp.containsMove(SMNode.goal,temp.getMoves())){
			for(int[][] a: temp.getMoves()){
				if(a!=null){
					s.push(new SMNode(a,temp));
				}
			}
			temp = s.pop();
			temp2 = s.pop();
			do{
				 if(SMNode.countIncorrect(temp2.getState())< SMNode.countIncorrect(temp.getState())){
					 temp = temp2;
				 }
				 else if(SMNode.countIncorrect(temp2.getState())== SMNode.countIncorrect(temp.getState())){
					 if(SMNode.getTotalDistance(temp2.getState())<SMNode.getTotalDistance(temp.getState())){
						 temp = temp2; 
					 }
				 }
				 if(s.isEmpty()){
					 break;
				 }
				 temp2 = s.pop();
			}
			while(temp2!=null);
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
		long start, end;
		SpaceManagement sm = new SpaceManagement();
		
		System.out.println("STARTING POSITION A*: HEURISTIC 1");
		start = System.currentTimeMillis();
		SMNode sol = sm.aStarH1();
		end = System.currentTimeMillis();
		sm.printSuccessRoute(sol);
		System.out.println("Total Number of Moves: " + SMNode.getNumberOfMoves(sol));
		System.out.println("Total Computation Time: " + (end-start)/1000.0 + " seconds\n");
		
		System.out.println("STARTING POSITION A*: HEURISTIC 2");
		start = System.currentTimeMillis();
		sol = sm.aStarH2();
		end = System.currentTimeMillis();
		sm.printSuccessRoute(sol);
		System.out.println("Total Number of Moves: " + SMNode.getNumberOfMoves(sol));
		System.out.println("Total Computation Time: " + (end-start)/1000.0 + " seconds\n");
		
		System.out.println("STARTING POSITION A*: HEURISTIC 3");
		start = System.currentTimeMillis();
		sol = sm.aStarH3();
		end = System.currentTimeMillis();
		sm.printSuccessRoute(sol);
		System.out.println("Total Number of Moves: " + SMNode.getNumberOfMoves(sol));
		System.out.println("Total Computation Time: " + (end-start)/1000.0 + " seconds\n");
		
		System.out.println("STARTING POSITION BFS");
		start = System.currentTimeMillis();
		sol = sm.breadthFirstSearch();
		end = System.currentTimeMillis();
		sm.printSuccessRoute(sol);
		System.out.println("Total Number of Moves: " + SMNode.getNumberOfMoves(sol));
		System.out.println("Total Computation Time: " + (end-start)/1000.0 + " seconds\n");
		
		System.out.println("STARTING POSITION DFS");
		start = System.currentTimeMillis();
		sol = sm.depthFirstSearch();
		end = System.currentTimeMillis();
		sm.printSuccessRoute(sol);
		System.out.println("Total Number of Moves: " + SMNode.getNumberOfMoves(sol));
		System.out.println("Total Computation Time: " + (end-start)/1000.0 + " seconds\n");

	}
}
