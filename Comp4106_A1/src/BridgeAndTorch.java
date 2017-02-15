import java.util.*;


public class BridgeAndTorch {
	public BTNode start;
	public static int[] times;
	public int successTime;
	
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
	public static void main(String[] args) {
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

		BTNode f = bt.depthFirstSearch();
		System.out.println("DFS");
		bt.printSuccessRoute(f);
		System.out.println("Success Time: " + bt.getSuccessTime());
		BTNode g = bt.breadthFirstSearch();
		System.out.println("BFS");
		bt.printSuccessRoute(g);
		System.out.println("Success Time: " + bt.getSuccessTime());
	}
}