import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class SpaceManagement {
	private SMNode start;
	public static int xSize;
	public static int ySize;
	
	private int[][] begin3x3 = {{6,7,3},{5,0,2},{4,1,8}}; //works for dfs and bfs
	//private int[][] begin3x3 = {{8,4,2},{3,1,7},{6,5,0}};
	private int[][] begin2x4 = {{0,2,4,6},{7,1,3,5}};
	private int[][] begin2x5 = {{1,0,3,6,8},{7,4,5,2,9}};
	public SpaceManagement(int xSize, int ySize){
		this.xSize = xSize;
		this.ySize = ySize;
		if(xSize == 3 && ySize == 3){
			start = new SMNode(begin3x3,null);
		}
		else if(xSize == 2 && ySize == 4){
			start = new SMNode(begin2x4,null);
		}
		else if(xSize ==2 && ySize == 5){
			start = new SMNode(begin2x5,null);
		}
	}
	public void setStart(Scanner scan){
		do{
			for (int i=0;i<xSize;i++){
				System.out.println("Please enter the values for row: " + (i+1) + ". Requires " + ySize + " entries. Separate each by a space.");
				String[] input = scan.nextLine().split(" ");
				for(int j = 0; j<ySize;j++){
					start.getState()[i][j] = Integer.parseInt(input[j]);
				}
			}
		}
		while(!checkValidStart());
	}
	public boolean checkValidStart(){
		ArrayList<Integer> numbers = new ArrayList<>();
		for(int i = 0; i<xSize;i++){
			for(int j=0;j<ySize;j++){
				if(numbers.contains(start.getState()[i][j])){
					return false;
				}
				else{
					numbers.add(start.getState()[i][j]);
				}
			}
		}
		if(numbers.size()<xSize*ySize){
			return false;
		}
		if(!numbers.contains(0)){
			return false;
		}
		return true;
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
	public static int getxSize() {
		return xSize;
	}
	public static void setxSize(int xSize) {
		SpaceManagement.xSize = xSize;
	}
	public static int getySize() {
		return ySize;
	}
	public static void setySize(int ySize) {
		SpaceManagement.ySize = ySize;
	}
	public static void main(String args[]){
		long start, end;
		System.out.println("Please enter the dimensions separated by a space (Options: 3 3, 2 4, 2 5) ");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		String[] nums = input.split(" ");
		
		while(nums.length!= 2){
			System.out.println("Make sure there are only 2 dimensions");
			input = scan.nextLine();
			nums = input.split(" ");
		}
		SpaceManagement sm = new SpaceManagement(Integer.parseInt(nums[0]),Integer.parseInt(nums[1]));
		System.out.println("Do you wish to enter your own dimensions? (y/n)");
		if(scan.nextLine().equals("y")){
			sm.setStart(scan);
		}
		
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
