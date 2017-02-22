import java.util.ArrayList;


public class SMNode {
	private int[][] state = {{1,2,3},{4,0,5},{6,7,8}};
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
	
	public void getStandardMoves(){
		int[][] temp;
		for(int i = 0;i< xSize;i++){
			for(int j = 0; j < ySize;j++){
				if(state[i][j]==0){
					if(i-1>=0){
						temp = copyState();
						temp[i][j] = temp[i-1][j];
						temp[i-1][j] = 0;
						moves.add(temp);
					}
					if(i+1<xSize){
						temp = copyState();
						temp[i][j] = temp[i+1][j];
						temp[i+1][j] = 0;
						moves.add(temp);
					}
					if(j-1>=0){
						temp = copyState();
						temp[i][j] = temp[i][j-1];
						temp[i][j-1] = 0;
						moves.add(temp);
					}
					if(j+1<ySize){
						temp = copyState();
						temp[i][j] = temp[i][j+1];
						temp[i][j+1] = 0;
						moves.add(temp);
					}
					if(i-1>=0 && j-1>=0){
						temp = copyState();
						temp[i][j] = temp[i-1][j-1];
						temp[i-1][j-1] = 0;
						moves.add(temp);
					}
					if(i-1>=0 && j+1<ySize){
						temp = copyState();
						temp[i][j] = temp[i-1][j+1];
						temp[i-1][j+1] = 0;
						moves.add(temp);
					}
					if(i+1<xSize && j-1>=0){
						temp = copyState();
						temp[i][j] = temp[i+1][j-1];
						temp[i+1][j-1] = 0;
						moves.add(temp);
					}
					if(i+1<xSize && j+1<ySize){
						temp = copyState();
						temp[i][j] = temp[i+1][j+1];
						temp[i+1][j+1] = 0;
						moves.add(temp);
					}
				}
			}
		}
	}
	
	public void getHorseMoves(){
		int[][] temp;
		int hold;
		for(int i = 0;i< xSize;i++){
			for(int j = 0; j < ySize;j++){
				if(i-2>=0 && j-1 >=0){
					temp = copyState();
					hold = temp[i][j];
					temp[i][j] = temp[i-2][j-1];
					temp[i-2][j-1] = hold;
					if(!moves.contains(temp)){
						moves.add(temp);
					}
				}
				if(i-2>=0 && j+1<ySize){
					temp = copyState();
					hold = temp[i][j];
					temp[i][j] = temp[i-2][j+1];
					temp[i-2][j+1] = hold;
					if(!moves.contains(temp)){
						moves.add(temp);
					}
				}
				if(i-1>=0 && j+2<ySize){
					temp = copyState();
					hold = temp[i][j];
					temp[i][j] = temp[i-1][j+2];
					temp[i-1][j+2] = hold;
					if(!moves.contains(temp)){
						moves.add(temp);
					}
				}
				if(i+1<xSize && j+2<ySize){
					temp = copyState();
					hold = temp[i][j];
					temp[i][j] = temp[i+1][j+2];
					temp[i+1][j+2] = hold;
					if(!moves.contains(temp)){
						moves.add(temp);
					}
				}
				if(i+2<xSize && j+1<ySize){
					temp = copyState();
					hold = temp[i][j];
					temp[i][j] = temp[i+2][j+1];
					temp[i+2][j+1] = hold;
					if(!moves.contains(temp)){
						moves.add(temp);
					}
				}
				if(i+2<xSize && j-1>=0){
					temp = copyState();
					hold = temp[i][j];
					temp[i][j] = temp[i+2][j-1];
					temp[i+2][j-1] = hold;
					if(!moves.contains(temp)){
						moves.add(temp);
					}
				}
				if(i-2>=0 && j+1<ySize){
					temp = copyState();
					hold = temp[i][j];
					temp[i][j] = temp[i-2][j+1];
					temp[i-2][j+1] = hold;
					if(!moves.contains(temp)){
						moves.add(temp);
					}
				}
				if(i-2>=0 && j-1>=0){
					temp = copyState();
					hold = temp[i][j];
					temp[i][j] = temp[i-2][j-1];
					temp[i-2][j-1] = hold;
					if(!moves.contains(temp)){
						moves.add(temp);
					}
				}
			}
		}
	}
	public int[][] copyState(){
		int[][] temp = new int[xSize][ySize];
		for(int i = 0;i< xSize;i++){
			for(int j = 0; j < ySize;j++){
				temp[i][j] = this.state[i][j];
			}
		}
		return temp;
	}
	public static void printState(int[][] state){
		for(int i = 0; i < xSize;i++){
			for(int j = 0; j< ySize; j++){
				if(state[i][j] == 0){
					System.out.print("  ");
				}
				else{
					System.out.print(state[i][j] + " ");
				}
			}
			System.out.println();
		}
	}
	public ArrayList<int[][]> getMoves(){
		moves = new ArrayList<int[][]>();
		//getStandardMoves();
		getHorseMoves();
		return moves;
		
	}
	public void containsMove(int[][] move){
		for(int[][] m: moves){
			for(int i = 0; i< xSize;i++){
				for(int j = 0; j< ySize;j++){
					
				}
			}
		}
	}
	public static void main(String args[]){
		SMNode s = new SMNode();
		s.generateGoal();
	    for(int[][] a: s.getMoves()){
	    	printState(a);
	    	System.out.println();
	    }
	}
}
