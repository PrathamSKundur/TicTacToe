package tictactoe;

import java.util.ArrayList;
import tictactoe.Board.PosNotEmptyException;

public class Session  {
	public int count;
	public Board b;
	int pos_x,pos_y;
	int  val ;
	public Session(){
		b = new Board();
		count=0;
	}
	//Returns true if player has won (Always false vs computer)
	public boolean player_move(int x, int y, int player) throws PosNotEmptyException {
		b.setBoard(x, y, player);
		return b.checkWin();
	}

	public void print() {
		int arr[][] = b.getBoard();
		for(int i = 0 ; i < 3 ; i++) {
			for(int j = 0 ; j < 3 ; j++)

				System.out.print( arr[i][j] );
			System.out.print('\n');
		}
		System.out.print('\n');
	}

	/* Makes most optimal move using minimax algorithm
	  Returns int[0]=1 if Computer has won
	  int[1],int[2] is the move comp has made  */
	public int[] comp_move() {
	    val = -10;
		int pos = 0;
		int arr[] = {0,0,0};
		int alpha=-1;
		if( b.getempty() == 9 ) {		//If first move , any position is fine
			try {
				b.setBoard(0, 0, 2);
			} 
			catch (Exception e) {
				//This will never occur
			}
			return arr;
		}
		long start = System.nanoTime();
		for ( int i = 0 ; i < 9 ; i++ )
		{       		
			int x= i / 3;
			int y= i % 3;
			int temp;
			try {
				b.setBoard(x, y, 2);
			} catch (Exception e) {
				//Position already taken
				continue;
			}
			if(val < ( temp = minimax( b , false , alpha , 1 ) ) )  {
				val = alpha = temp;
				pos=i;
			}  
			b.clear(x,y);
			if( val == 1 )
				break;
		}

		System.out.println("Exec time : " + ((double)System.nanoTime()-start)/1000000 + "ms");

		try {
			b.setBoard( pos / 3 , pos % 3 , 2);
		} catch (Exception e) {
			// This will never occur
		}	

		arr[0] = b.checkWin() ? 1 : 0;
		arr[1] = pos / 3;
		arr[2] = pos % 3;

		return arr;
	}

	public int[] comp_move_multi() {
		
		val = -10;
		int arr[] = {0,0,0};
		
		//If first move ,  any position is fine 
		if( b.getempty() == 9 ) {		
			try {
				b.setBoard(0, 0, 2);
			} 
			catch (Exception e) {
				//This will never occur
			}
			return arr;
		}

		ArrayList<Thread> branches= new ArrayList<>();
		long start = System.nanoTime();

		for ( int i = 0 ; i < 9 ; i++ ){       	
			int x = i / 3;
			int y = i % 3;
			class branch extends Thread {
				@Override
				public void run() {
					//		System.out.println("Thread " + x + " " + y + " started");
					int branch_val;
					Board temp_board = new Board(b);
					try {
						temp_board.setBoard( x, y, 2 );
					} catch ( Exception e ) {
						//	Will never occur
						//	System.out.println("Thread " + x + " " + y + " exited by exception" + e);
						return ;
					}
					branch_val = minimax(temp_board, false, -1 , 1 );
					synchronized(this) {
						if( branch_val > val ) {
							val = branch_val;
							pos_x = x;
							pos_y = y;
						}
					}
					//		System.out.println("Thread " + x + " " + y + " gave " + branch_val);
				}
			}
			if( b.getBoard(x, y) == 0 ) {
			branch t = new branch();
			branches.add ( t );	
			t.start();
			}
		}
		
		for(int i = 0 ; i < branches.size() ; i++) {
			try {
				branches.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Exec time : " + ((double)System.nanoTime()-start)/1000000 + "ms" );

		try {
			b.setBoard( pos_x , pos_y , 2);
		} catch (Exception e) {
			// This will never occur
		}	

		arr[0] = b.checkWin() ? 1 : 0;
		arr[1] = pos_x ;
		arr[2] = pos_y ;
		return arr;
	}

	private int minimax(Board b, boolean maximizer , int alpha , int beta) 
	{
		count++;
		if(b.checkWin()) {
			return (maximizer)? -1 : 1 ;
		}
		if( b.getempty() == 0 ) {
			return 0;
		}
		int val = (maximizer) ? - 10 : 10 ;
		for (int i = 0 ; i < 9 ; i++)
		{
			int x = i / 3;
			int y = i % 3;
			try {
				b.setBoard( x , y , (maximizer) ? 2 : 1 );
			} catch (Exception e) {
				//Position already filled
				continue;
			}
			if(maximizer)
			{
				val = Math.max( val , minimax (b, false, alpha, beta ) );	
				b.clear(x,y);
				if( val >= beta )
					break;
				alpha=Math.max( alpha , val );
			}
			else
			{
				val = Math.min( val , minimax (b, true, alpha, beta ) );	
				b.clear(x,y);
				if(val <= alpha)
					break;
				beta=Math.min( beta , val );
			}
		}
		return val;
	}
}
