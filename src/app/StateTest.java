package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

public class StateTest {

	@Test
	public void testInit() {

		State s = new State();
		s.initBoard();
		StringBuilder testBoard = new StringBuilder();
		Scanner scan;
		try {
			scan = new Scanner(new File("queenCaptureBug.txt"));
			int turn = scan.nextInt();

			char onMove = scan.next().toLowerCase().charAt(0);

			testBoard.append(turn + " ");
			testBoard.append(onMove);
			while (scan.hasNextLine()) {

				String line = scan.nextLine();

				for (char c : line.toCharArray()) {
					testBoard.append(c);
				}
				testBoard.append("\n");
			}
			System.out.println("King origional "+s.kingGone);
			s.readBoard(testBoard.toString());

			// call a move and see if it moved correctly
			
			MiniChess m = new MiniChess();
			State t = m.negamaxDriver(s);
			

			System.out.println("BOARD OUTPUT:");
			System.out.println(t.printBoard());
		

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
