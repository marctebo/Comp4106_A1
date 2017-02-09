import java.util.ArrayList;


public class BridgeAndTorch {

	private static int[] people;
	private int[] times;
	private static ArrayList<int[]> moves;
	private static int[] goal;
	private static int size = 4;
	
	public BridgeAndTorch(){
		people = new int[size];
	}
	
	public static void getPossibleMoves(int[] current){
		int[] temp = current;
		moves = new ArrayList<int[]>();
		if (temp[0] == 0){
			temp[0] = 1;
			while(!compareArrays(temp,goal)){
				for(int i = temp.length-1;i>0;i--){
					if(temp[i] == 0){
						temp[i] = 1;
						if(!compareArrays(temp,current) && !moves.contains(temp)){
							moves.add(temp);
						}
						break;
					}
					temp[i]=0;
				}
				
			}
		}
		
	}
	public static void generateGoal(){
		goal = new int[size];
		for (int i=0;i<size;i++){
			goal[i] = 1;
		}
	}

	public static void printArray(int[] array){
		for (int i = 0; i<array.length;i++){
			System.out.print(array[i] + " ");
		}
		System.out.println("\t");
	}
	public static boolean compareArrays(int[] arr1, int[] arr2){
		if(arr1.length!=arr2.length) return false;
		for(int i = 0; i < arr1.length; i++){
			if(arr1[i]!=arr2[i]) return false;
		}
		return true;
	}
	public static void main(String[] args) {
		BridgeAndTorch b = new BridgeAndTorch();
		generateGoal();
		
		printArray(people);
		System.out.println();
		printArray(goal);
		int[] temp = {1,1,1};
		System.out.println(compareArrays(people,temp));
		
		getPossibleMoves(people);
		
		
	}
	
}
