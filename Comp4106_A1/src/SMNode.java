import java.util.ArrayList;


public class SMNode {
	private int[][] state;
	private static int xSize = 3;
	private static int ySize = 3;
	private ArrayList<int[][]> moves;
	public static int[][] goal;
	
	public SMNode(){
		goal = new int[xSize][ySize];
	}
	public void generateGoal(){
		int x = 0;
		int count = 1;
		for (int i = 0; i < ySize; i++){
			goal[x][i] = count;
			count++;
			if(i == ySize-1){
				x++;
				while(x<xSize){
					goal[x][i] = count;
					x++;
					count++;
				}
				x--;
				i--;
				while(i > 0){
					goal[x][i] = count;
					i--;
					count++;
				}
				if(count == xSize*ySize){
					break;
				}
				
			}
			if(i==0 && x==xSize-1){
				goal[x][i] = count;
				count++;
				if(goal[x-1][i] == 0){
					goal[x-1][i] = count;
				}
				break;
			}
		}	
	}
	public void printGoal(){
		for(int i = 0; i < xSize;i++){
			for(int j = 0; j< ySize; j++){
				System.out.print(goal[i][j] + " ");
			}
			System.out.println();
		}
	}
	public static void main(String args[]){
		SMNode s = new SMNode();
		s.generateGoal();
	    s.printGoal();
	}
}
