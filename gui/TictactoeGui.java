package gui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import tictactoe.*;

@SuppressWarnings("serial")
public class TictactoeGui extends JFrame {
	private Container pane;
	private JButton[][] board;
	private Session game;
	public TictactoeGui(){
		game = new Session();
		pane = getContentPane();
		pane.setLayout(new GridLayout( 3 , 3 ));
		this.setTitle("MiniMax Bot");
		setSize( 300 , 300 );
		setDefaultCloseOperation( DISPOSE_ON_CLOSE ); 
		board = new JButton[3][3];
		setVisible(true);
		initializeBoard();
	}

	private void initializeBoard() {

		for(int i = 0 ; i < 3 ; i++)
			for(int j = 0 ; j < 3 ; j++)
			{
				int p=i;
				int q=j;
				JButton b = new JButton();
				b.setFont(new Font( Font.MONOSPACED , Font.BOLD , 78));
				b.setBackground(Color.LIGHT_GRAY);
				b.addActionListener(new ActionListener() {		
					@Override
					public void actionPerformed(ActionEvent e) {
						if(b.getText()=="")
							play(p,q);
					}
				});
				board[i][j] = b;
				pane.add(b);
			}
		placeRandom();
	}
	private void play(int i , int j)
	{
		try 
		{
			game.player_move( i , j , 1 );
			board[i][j].setText( "X" );
			checkDraw();
			int arr[] = game.comp_move_multi();
			board[arr[1]][arr[2]].setText( "O" );
			if( arr[0] == 1 ) 
			{
				System.out.println(game.count);
				JOptionPane.showMessageDialog(null, "Computer Won");
				game.b.clear();
				clearb();
				placeRandom();
			}
			checkDraw();
		}
		catch (Exception expt) {
			JOptionPane.showMessageDialog(null, expt.getMessage());
		}
	}

	private void checkDraw() {
		if( game.b.getempty() == 0 ) {
			System.out.println(game.count);
			JOptionPane.showMessageDialog(null, "Draw");
			game.b.clear();
			clearb();
		}
	}
	private void placeRandom() {
		Random r = new Random();
		if(r.nextInt(2) == 1) {
			try {
				game.comp_move_multi();
			}
			catch (Exception e){}
			board[0][0].setText("O");
		}
	}
	private void clearb() {
		for(JButton row[]: board)
			for (JButton j : row) {
				j.setText("");
			}
	}
	public static void main(String a[]) {
		new TictactoeGui();
	}
}