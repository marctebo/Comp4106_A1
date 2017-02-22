import java.util.*;


public class BridgeAndTorch {
	public BTNode start;
	public static int[] times;
	public int successTime;
	public int fastestPerson;

	public BridgeAndTorch(int size){
		start = new BTNode(size);
		times = new int[size - 1];
	}
	public BTNode depthFirstSearch(){
		Stack<BTNode> s = new Stack<BTNode>();
		for(ArrayList<Integer> a: start.getMoves()){
			s.push(new BTNode(a,start));
		}
		BTNode temp = s.pop();
		while(!temp.getMoves().contains(BTNode.goal)){
			for(ArrayList<Integer> a: temp.getMoves()){
				s.push(new BTNode(a,temp));
			}
			temp = s.pop();
		}
		
		return new BTNode(BTNode.goal,temp);
		
	}
	
	public BTNode breadthFirstSearch(){
		Queue<BTNode> q = new ArrayDeque<BTNode>();
		//start.getMoves();
		for(ArrayList<Integer> a: start.getMoves()){
			q.add(new BTNode(a,start));
		}
		BTNode temp = q.remove();
		while(!temp.getMoves().contains(BTNode.goal)){
			for(ArrayList<Integer> a: temp.getMoves()){
				q.add(new BTNode(a,temp));
			}
			temp = q.remove();
		}
		
		return new BTNode(BTNode.goal,temp);
		
	}
	
	public BTNode aStarH1(){
		Stack<BTNode> s = new Stack<BTNode>();
		for(ArrayList<Integer> a: start.getMoves()){
			s.push(new BTNode(a,start));
		}
		BTNode temp = s.pop();
		while(!temp.getMoves().contains(BTNode.goal)){
			if(BTNode.getChangedIndices(temp.getParent().getState(),temp.getState()).contains(fastestPerson+1)){
				for(ArrayList<Integer> a: temp.getMoves()){
					s.push(new BTNode(a,temp));
				}
			}
			temp = s.pop();
			while(temp.getMoves().contains(BTNode.goal) && !BTNode.getChangedIndices(temp.getParent().getState(),temp.getState()).contains(fastestPerson+1)){
				temp = s.pop();
			}
		}
		
		return new BTNode(BTNode.goal,temp);
	}
	
	public BTNode aStarH2(){
		Queue<BTNode> q = new ArrayDeque<BTNode>();
		BTNode temp = new BTNode(BTNode.goal.size());
		ArrayList<BTNode> hold = new ArrayList<>();
		for(ArrayList<Integer> a: start.getMoves()){
			q.add(new BTNode(a,start));
		}
		do{
			while(!q.isEmpty()){
				temp = q.remove();
				hold.add(temp);
			}
			temp = hold.remove(getCheapestPath(hold));
			while(!hold.isEmpty()){
				q.add(hold.remove(0)); //potential problem
			}
			for(ArrayList<Integer> a: temp.getMoves()){
				q.add(new BTNode(a,temp));
			}
		}
		while(!temp.getMoves().contains(BTNode.goal));
		
		return new BTNode(BTNode.goal,temp);
	}
	
	public BTNode aStarH3(){
		Queue<BTNode> q = new ArrayDeque<BTNode>();
		BTNode temp = new BTNode(BTNode.goal.size());
		ArrayList<BTNode> hold = new ArrayList<>();
		for(ArrayList<Integer> a: start.getMoves()){
			q.add(new BTNode(a,start));
		}
		for(BTNode b: q){
			if(!BTNode.getChangedIndices(b.getParent().getState(),b.getState()).contains(fastestPerson+1)){
				q.remove(b);
			}
		}
		do{
			while(!q.isEmpty()){
				temp = q.remove();
				hold.add(temp);
			}
			temp = hold.remove(getCheapestPath(hold));
			while(!hold.isEmpty()){
				q.add(hold.remove(0)); //potential problem
			}
			for(ArrayList<Integer> a: temp.getMoves()){
				q.add(new BTNode(a,temp));
			}
		}
		while(!temp.getMoves().contains(BTNode.goal));
		
		return new BTNode(BTNode.goal,temp);
	}
	public static ArrayList<BTNode> getSuccessRoute(BTNode end){
		BTNode temp = end;
		ArrayList<BTNode> path = new ArrayList<BTNode>();
		path.add(0,temp);
		do{
			temp = temp.getParent();
			path.add(0,temp);
		}
		while(temp.getParent() != null);
		return path;
	}
	
	public void printSuccessRoute(BTNode end){
		ArrayList<ArrayList<Integer>> changed = new ArrayList<>();
		
		successTime = 0;
		
		while(end.getParent()!=null){
			ArrayList<Integer> temp = BTNode.getChangedIndices(end.getState(), end.getParent().getState());
			changed.add(0, temp);
			end = end.getParent();
		}
		for(ArrayList<Integer> i: changed){
			//use max();
			int val = 0;
			System.out.print("Person(s) ");
			for(Integer a: i){
				if(a.intValue() != 0){
					System.out.print(a + " ");
					if(times[a.intValue()-1]>val){
						val = times[a.intValue()-1];
					}
				}
			}
			successTime+=val;
			System.out.println("crossed the bridge.");
		}
	}
	public static void setTimes(String[] input){
		for(int i = 0;i<input.length;i++){
			times[i] = Integer.parseInt(input[i]);
		}
	}
	
	public int getSuccessTime(){
		return successTime;
	}
	
	public int getQuickest(){
		int low = 0;
		for(int i = 1; i< times.length;i++){
			if(times[i]< times[low]){
				low = i;
			}
		}
		return low;
		
	}
	
	public int getFastestPerson() {
		return fastestPerson;
	}
	public void setFastestPerson(int fastestPerson) {
		this.fastestPerson = fastestPerson;
	}
	
	public static int getCheapestPath(ArrayList<BTNode> hold){
		int cheap = 0;
		int temp = 0;
		int index = 0;
		for(BTNode b: hold){
			temp = getDistance(b);
			if (cheap == 0 || temp < cheap){
				cheap = temp;
				index = hold.indexOf(b);
			}
		}
		return index;
	}
	public static int getDistance(BTNode node){
		ArrayList<ArrayList<Integer>> changed = new ArrayList<>();
		int curr = 0;
		int time = 0;
		while(node.getParent()!= null){
			ArrayList<Integer> temp = BTNode.getChangedIndices(node.getState(), node.getParent().getState());
			changed.add(0, temp);
			node = node.getParent();
		}
		for(ArrayList<Integer>i: changed){
			for(Integer a:i){
				if(a.intValue() != 0){
					if(times[a.intValue()-1]>curr){
						curr = times[a.intValue()-1];
					}
				}
			}
			time+=curr;
			curr = 0;
		}
		return time;
	}
	public static void main(String[] args) {
		long start, end;
		System.out.println("Please enter the number of people (Minimum 3)");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		while(Integer.parseInt(input) < 3){
			System.out.println("Please enter a value greater than 3");
			input = scan.nextLine();
		}
		int size = Integer.parseInt(input) + 1;
		BridgeAndTorch bt = new BridgeAndTorch(size);
		
		System.out.println("Now enter the corresponding times (Separated by one space)");
		input = scan.nextLine();
		String[] nums = input.split(" ");
		
		while(nums.length!= size - 1){
			System.out.println("Make sure each person has a crossing time (No extra times)");
			input = scan.nextLine();
			nums = input.split(" ");
		}
		
		setTimes(nums);
		bt.setFastestPerson(bt.getQuickest());
		start = System.currentTimeMillis();
		BTNode f = bt.depthFirstSearch();
		end = System.currentTimeMillis();
		System.out.println("\nDFS");
		bt.printSuccessRoute(f);
		System.out.println("Success Time: " + bt.getSuccessTime()+ " minutes");
		System.out.println("Computation Time: " + (end-start)/1000.0 + " seconds\n");
		
		start = System.currentTimeMillis();
		BTNode g = bt.breadthFirstSearch();
		end = System.currentTimeMillis();
		System.out.println("BFS");
		bt.printSuccessRoute(g);
		System.out.println("Success Time: " + bt.getSuccessTime()+ " minutes");
		System.out.println("Computation Time: " + (end-start)/1000.0 + " seconds\n");
		
		start = System.currentTimeMillis();
		BTNode h = bt.aStarH1();
		end = System.currentTimeMillis();
		System.out.println("A*: Heuristic 1");
		bt.printSuccessRoute(h);
		System.out.println("Success Time: " + bt.getSuccessTime()+ " minutes");
		System.out.println("Computation Time: " + (end-start)/1000.0 + " seconds\n");
		
		start = System.currentTimeMillis();
		BTNode i = bt.aStarH2();
		end = System.currentTimeMillis();
		System.out.println("A*: Heuristic 2");
		bt.printSuccessRoute(i);
		System.out.println("Success Time: " + bt.getSuccessTime()+ " minutes");
		System.out.println("Computation Time: " + (end-start)/1000.0 + " seconds\n");
		
		start = System.currentTimeMillis();
		BTNode j = bt.aStarH3();
		end = System.currentTimeMillis();
		System.out.println("A*: Heuristic 3");
		bt.printSuccessRoute(j);
		System.out.println("Success Time: " + bt.getSuccessTime()+ " minutes");
		System.out.println("Computation Time: " + (end-start)/1000.0 + " seconds\n");
	}
}