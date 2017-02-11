import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BridgeAndTorch {

	private static int[] people;
	private int[] times;
	private static List<ArrayList<Integer>> moves;
	private static int[] goal;
	private static int size = 6;
	
	public BridgeAndTorch(){
		people = new int[size];
	}
	
	public static void getPossibleMoves(int[] current){
		int[] temp = current.clone();
		moves = new ArrayList<ArrayList<Integer>>();
		if (temp[0] == 0){
			temp[0] = 1;
			while(!compareArrays(temp,goal)){
				for(int i = temp.length-1;i>0;i--){
					if(temp[i] == 0){
						temp[i] = 1;
						if(!compareArrays(temp,current) && !moves.contains(temp)){
							moves.add(toArrayList(temp));
						}
						break;
					}
					temp[i]=0;
				}
			}
		}
		else if (temp[0] == 1){
			do{
				for(int i = temp.length-1;i>=0;i--){
					if(temp[i] == 0){
						temp[i] = 1;
						if(!compareArrays(temp,current) && !moves.contains(temp) && temp[0]==0){
							moves.add(toArrayList(temp));
						}
						break;
					}
					temp[i]=0;
				}
			}while(!compareArrays(temp,current));
		}
		
	}
	public static void generateGoal(){
		goal = new int[size];
		for (int i=0;i<size;i++){
			goal[i] = 1;
		}
	}
	public static ArrayList<Integer> toArrayList(int[] array){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i = 0; i<array.length;i++){
			temp.add(new Integer(array[i]));
		}
		return temp;
	}
	public static void printArray(int[] array){
		for (int i = 0; i<array.length;i++){
			System.out.print(array[i] + " ");
		}
		System.out.println("\t");
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
		System.out.println("START");
		
		getPossibleMoves(people);

		for(ArrayList<Integer> a: moves){
			System.out.println(a.toString() + "\t["+ checkValidMove(toArrayList(people),a)+ "]");
		}
		moves = new ArrayList<ArrayList<Integer>>();
		int[] ar = new int[]{1,0,1,1,0,0};
		System.out.println();
		printArray(ar);
		System.out.println("START");
		getPossibleMoves(ar);
		
		for(ArrayList<Integer> a: moves){
			System.out.println(a.toString() + "\t["+ checkValidMove(toArrayList(ar),a)+ "]");
		}
		System.out.println(countDifferent(toArrayList(ar),moves.get(1)));
		
;	}
	
}
