package app;

import java.io.File;
import java.util.Scanner;

public class TestApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		State s = new State();

		s.initBoard();
		System.out.println(s.printBoard());
		Scanner scan = new Scanner(System.in);
		String command = scan.next();
		System.out.println(s.humanMove(command).printBoard());
		
		
	}

}
