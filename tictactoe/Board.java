package tictactoe;


public class Board {
	private int[][] elements = new int[3][3];
	private int empty ;			//Number of empty spots
	Board(){
		empty=9;
	}
	Board(Board b){
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				elements[i][j]=b.elements[i][j];
			}
		}
		this.empty = b.empty;
	}
	public int[][] getBoard() {
		return elements;
	}
	public int getBoard(int x,int y) {
		return elements[x][y];
	}
	//If x,y is not empty PosNotEmpty is thrown  
	public void setBoard(int x,int y, int val)throws PosNotEmptyException {
		if( elements[x][y] != 0 )
			throw new PosNotEmptyException( x + " " + y + " Not Empty" );
		elements[x][y]=val;
		empty--;
	}
	public int getempty(){
		return empty;
	}
	public void clear() {
		for(int i=0;i<3;i++) {
			for(int j=0;j<3;j++) {
				elements[i][j]=0;
			}
		}
		empty=9;
	}
	public void clear(int x,int y)  {
		
		elements[x][y]=0;
		empty++;
	}
	public boolean checkWin() {
		//Check all rows
		for(int i=0;i<3;i++)
			if(elements[i][1] != 0 && elements[i][0] == elements[i][1] && elements[i][1] == elements[i][2] )
				return true;
		//Check all columns
		for(int i=0;i<3;i++)
			if(elements[1][i] != 0 && elements[0][i] == elements[1][i] && elements[1][i] == elements[2][i] )
				return true;
		//Check Diagonals
		if(elements[1][1] != 0 && elements[0][0] == elements[1][1] && elements[1][1] == elements[2][2] )
			return true;
		if(elements[1][1] != 0 && elements[0][2] == elements[1][1] && elements[1][1] == elements[2][0] )
			return true;
		return false;
	}
	@SuppressWarnings("serial")
	class PosNotEmptyException extends Exception{
		PosNotEmptyException(String s){
			super(s);
		}
	}
}
