import java.util.Scanner;

import tictactoe.*;

public class Test {
	@SuppressWarnings("deprecation")
	public static void main(String args[]) {
		Session mysess=new Session();
		int i,j,count=1;
		boolean flag;
		int arr[];
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		System.out.println("Comp move");
		mysess.comp_move_multi();
		mysess.print();
		while(count<5)
		{
			flag=true;
			while(flag)
			{
				flag=false;
				System.out.println("Player move");
				i=Integer.parseInt(in.nextLine());
				j=Integer.parseInt(in.nextLine());
				try {
					mysess.player_move(i,j,1);
				}
				catch (Exception e) {
					System.out.println(e);
					flag=true;
				}			
			}
			mysess.print();

			System.out.println("Comp move");
			arr = mysess.comp_move_multi();
			if(arr[0] == 1)
			{
				mysess.print();
				System.out.println("Comp won");
				System.exit(0);
			}
			mysess.print();
			count++;
		}

		System.out.println("Draw");
	}
}
