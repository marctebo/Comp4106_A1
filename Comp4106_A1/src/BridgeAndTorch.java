import java.util.*;


public class BridgeAndTorch {
	public BTNode start;
	
	public BridgeAndTorch(int size){
		start = new BTNode(size);
	}
	public BTNode depthFirstSearch(){
		Stack<BTNode> s = new Stack<BTNode>();
		//start.getMoves();
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
	
	public void printSuccessRoute(ArrayList<BTNode> path){
		for(BTNode b: path){
			System.out.println(b.getState().toString());
		}
	}
	
	public static void main(String[] args) {
		BridgeAndTorch bt = new BridgeAndTorch(5);
		BTNode f = bt.depthFirstSearch();
		bt.printSuccessRoute(getSuccessRoute(f));
		System.out.println();
		BTNode g = bt.breadthFirstSearch();
		bt.printSuccessRoute(getSuccessRoute(g));
	}
}