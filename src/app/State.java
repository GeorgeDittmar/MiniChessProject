package app;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * George Dittmar (c) Spring 2011
 * 
 * State class that holds a board state and methods to do moves on a board
 * 
 * @author george
 * 
 */
public class State implements Cloneable {
	private char[][] board = new char[6][5];

	char onMove;
	int turn;
	boolean isDraw = false;
	boolean kingGone = false;

	// hack probably
	int king = -1;
	private String colLabels = "abcde";
	private String w = "RNBQK";
	private String b = "kqbnr";

	public State clone() {
		State s = new State(kingGone, king);
		return s;

	}

	public char[][] getBoard() {
		return board;
	}

	public State() {

	}

	public State(boolean kingGone, int king) {
		this.kingGone = kingGone;
		this.king = king;

	}

	public void setKingGone(boolean kingGone) {
		this.kingGone = kingGone;
	}

	public void setKing(int king) {
		this.king = king;
	}

	/**
	 * Initialize the board for the game with default settings
	 */
	public void initBoard() {

		onMove = 'w';
		turn = 1;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {

				if (i == 0) {
					board[i][j] = b.charAt(j);

				} else if (i == 1) {
					board[i][j] = 'p';

				} else if (i == 4) {
					board[i][j] = 'P';

				} else if (i == 5) {
					board[i][j] = w.charAt(j);
				} else {

					board[i][j] = '.';
				}
			}
		}
	}

	/**
	 * takes in a board and updates the state with that board
	 * 
	 * @param board
	 */
	public void readBoard(String newBoard) {

		// grab the onMove and turn information
		Scanner scan = new Scanner(newBoard);

		turn = Integer.parseInt(scan.next());

		onMove = scan.next().charAt(0);
		scan.nextLine();

		int i = 0;
		while (scan.hasNextLine()) {
			String line = scan.nextLine();

			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = line.charAt(j);
			}
			i++;
		}

	}

	/**
	 * Prints the current board state.
	 * 
	 * @return
	 */
	public String printBoard() {
		StringBuilder output = new StringBuilder();
		output.append(turn + " " + onMove + "\n");
		for (char[] row : board) {

			for (char c : row) {
				output.append(Character.toString(c) + "");
			}
			output.append("\n");

		}
		return output.toString();

	}

	/**
	 * returns all possible moves that a piece on the board can make, including
	 * standing still (this needs to be fixed in later versions)
	 * 
	 * @param x
	 * @param y
	 * @param dx
	 * @param dy
	 * @return
	 */
	public ArrayList<Move> moves() {

		ArrayList<Move> moves = new ArrayList<Move>();

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {

				char piece = board[row][col];
				if (piece != '.' && color(piece) == onMove) {

					// did this to simplify my switch case chain because we
					// check before hand that the piece is onMove before we move
					// it
					piece = Character.toLowerCase(piece);
					switch (piece) {
					case 'r':

						moves.add(scanAll(piece, row, col, -1, 0, false)); // move
																			// N
						moves.add(scanAll(piece, row, col, 1, 0, false)); // move
																			// S
						moves.add(scanAll(piece, row, col, 0, 1, false)); // move
																			// E
						moves.add(scanAll(piece, row, col, 0, -1, false));// move
																			// W
						break;
					case 'q':

						moves.add(scanAll(piece, row, col, -1, 0, false)); // move
																			// N
						moves.add(scanAll(piece, row, col, -1, 1, false)); // moves
																			// NE
						moves.add(scanAll(piece, row, col, -1, -1, false)); // moves
																			// NW
						moves.add(scanAll(piece, row, col, 1, 0, false)); // move
																			// S
						moves.add(scanAll(piece, row, col, 1, 1, false));// SE
						moves.add(scanAll(piece, row, col, 1, -1, false));// SW
						moves.add(scanAll(piece, row, col, 0, 1, false)); // move
																			// E
						moves.add(scanAll(piece, row, col, 0, -1, false));// move
																			// W
						break;
					case 'k':

						moves.add(scanAll(piece, row, col, -1, 0, true));
						moves.add(scanAll(piece, row, col, -1, 1, true));
						moves.add(scanAll(piece, row, col, -1, -1, true));
						moves.add(scanAll(piece, row, col, 1, 0, true));
						moves.add(scanAll(piece, row, col, 1, 1, true));
						moves.add(scanAll(piece, row, col, 1, -1, true));
						moves.add(scanAll(piece, row, col, 0, 1, true));
						moves.add(scanAll(piece, row, col, 0, -1, true));
						break;
					case 'b':
						moves.add(scanAll(piece, row, col, -1, 1, false)); // moves
																			// NE
						moves.add(scanAll(piece, row, col, -1, -1, false)); // moves
																			// NW
						moves.add(scanAll(piece, row, col, 1, 1, false));// SE
						moves.add(scanAll(piece, row, col, 1, -1, false));// SW

						// move N,S,E,W
						moves.add(scanBishop(piece, row, col, 1, 0));
						moves.add(scanBishop(piece, row, col, -1, 0));
						moves.add(scanBishop(piece, row, col, 0, 1));
						moves.add(scanBishop(piece, row, col, 0, -1));
						break;
					case 'n':

						moves.add(scanKnight(piece, row, col, 2, 1));
						moves.add(scanKnight(piece, row, col, 2, -1));
						moves.add(scanKnight(piece, row, col, -2, 1));
						moves.add(scanKnight(piece, row, col, -2, -1));
						moves.add(scanKnight(piece, row, col, 1, 2));
						moves.add(scanKnight(piece, row, col, -1, 2));
						moves.add(scanKnight(piece, row, col, 1, -2));
						moves.add(scanKnight(piece, row, col, -1, -2));

						break;
					case 'p':

						if (onMove == 'w') {
							moves.addAll(scanPawn(piece, row, col, -1, 0));
						} else {
							moves.addAll(scanPawn(piece, row, col, 1, 0));
						}
						break;
					default:
						break;
					}

				}

			}
		}
		moves = removeNonMoves(moves);
		if (moves.size() == 0) {
			isDraw = true;
		}
		return moves;
	}

	/**
	 * helper method to remove moves that don't have a piece going anywhere.
	 * This will be removed in later versions when I can refactor my movement
	 * code and handle move generation more gracefully. 04/7/2011
	 * 
	 */
	private ArrayList<Move> removeNonMoves(ArrayList<Move> moves) {
		ArrayList<Move> clean = new ArrayList<Move>();
		for (int i = 0; i < moves.size(); i++) {
			if (!moves.get(i).from.toString().equalsIgnoreCase(
					moves.get(i).to.toString())) {
				clean.add(moves.get(i));
			}
		}
		return clean;
	}

	/**
	 * Used with pieces that can move as far as they want as long as they are
	 * within the boundaries of the board OR so long as they do not run into
	 * another piece. Also has a boolean flag that lets pieces that only move
	 * one square call this method as well, for example the King.
	 * 
	 * @param row
	 * @param col
	 * @param dRow
	 * @param dCol
	 * @param piece
	 * @return
	 */
	public Move scanAll(char piece, int row, int col, int dRow, int dCol,
			boolean singleMove) {

		Move m;
		int sRow = row;
		int sCol = col;

		do {
			if (isInBounds(row + dRow, col + dCol)) {
				// test if the piece has run into another piece
				if (board[row + dRow][col + dCol] != '.') {

					if (!isFriend(row + dRow, col + dCol)) {

						row += dRow;
						col += dCol;

						break;
					} else if (isFriend(row + dRow, col + dCol)) {

						break;
					}
				} else {

					row += dRow;
					col += dCol;
				}
			} else {
				break;
			}
		} while (!singleMove);

		m = new Move(new Square(row, col), new Square(sRow, sCol));

		return m;
	}

	/**
	 * looks to see if a piece you are about to run into is a friend or foe by
	 * checking the color of the target square
	 * 
	 * @return
	 */
	private boolean isFriend(int row, int col) {
		if (color(board[row][col]) == onMove) {

			return true;
		}
		return false;
	}

	/**
	 * tests to make sure that the move is within the bounds of the mini chess
	 * board
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean isInBounds(int row, int col) {
		if ((row) < 6 && (col) < 5 && (row) >= 0 && (col) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * scans to see if the adjacent square to a bishop is open or not
	 * 
	 * @param piece
	 * @param row
	 * @param col
	 * @param dRow
	 * @param dCol
	 * @return
	 */
	public Move scanBishop(char piece, int row, int col, int dRow, int dCol) {
		Move m;
		int sRow = row;
		int sCol = col;
		if (isInBounds(row + dRow, col + dCol)) {
			if (board[row + dRow][col + dCol] == '.') {

				row += dRow;
				col += dCol;
			}
		}

		m = new Move(new Square(row, col), new Square(sRow, sCol));
		return m;
	}

	/**
	 * scans for moves a pawn can make. Will be rethought in next few versions
	 * but works for now.
	 * 
	 * @param row
	 * @param col
	 * @param dRow
	 * @param dCol
	 * @return
	 */
	public ArrayList<Move> scanPawn(char piece, int row, int col, int dRow,
			int dCol) {

		ArrayList<Move> pawnMoves = new ArrayList<Move>();

		int sRow = row;
		int sCol = col;

		// three possible moves at any time to for a pawn

		// check if the move down can be done
		if (isInBounds(row + dRow, col + dCol)
				&& board[row + dRow][col + dCol] == '.') {
			int nRow = row + dRow;
			int nCol = col + dCol;
			Move m = new Move(new Square(nRow, nCol), new Square(sRow, sCol));
			pawnMoves.add(m);
		}

		// check if there is a piece to capture to the right of the pawn
		if (isInBounds(row + dRow, col + dCol + 1)
				&& board[row + dRow][col + 1] != '.') {
			if (!isFriend(row + dRow, col + 1)) {
				int nRow = row + dRow;
				int nCol = col + 1;
				Move m = new Move(new Square(nRow, nCol),
						new Square(sRow, sCol));
				pawnMoves.add(m);
			}
		}

		// check if there is a piece to capture to the left of the pawn
		if (isInBounds(row + dRow, col + dCol - 1)
				&& board[row + dRow][col - 1] != '.') {
			if (!(isFriend(row + dRow, col - 1))) {
				int nRow = row + dRow;
				int nCol = col - 1;
				Move m = new Move(new Square(nRow, nCol),
						new Square(sRow, sCol));
				pawnMoves.add(m);
			}
		}

		return pawnMoves;

	}

	/**
	 * Handles the movement of the knight piece
	 * 
	 * @param piece
	 * @param row
	 * @param col
	 * @param dRow
	 * @param dCol
	 * @return
	 */
	public Move scanKnight(char piece, int row, int col, int dRow, int dCol) {
		Move m;

		int sRow = row;
		int sCol = col;

		if (isInBounds(row + dRow, col + dCol)) {

			if (board[row + dRow][col + dCol] != '.') {

				if (!isFriend(row + dRow, col + dCol)) {

					row += dRow;
					col += dCol;

				} else if (isFriend(row + dRow, col + dCol)) {

				}
			} else {

				row += dRow;
				col += dCol;
			}
		}

		m = new Move(new Square(row, col), new Square(sRow, sCol));
		return m;
	}

	/**
	 * help to see who is on move
	 * 
	 * @param p
	 * @return
	 */
	public char color(char p) {
		if (p >= 65 && p <= 90) {

			return 'w';
		}

		if (p >= 97 && p <= 122) {
			return 'b';
		}

		throw new Error("Incorrect character in board.");
	}

	/**
	 * Takes a move object and see's if there is actually a piece on move and
	 * then moves said piece. This method also checks to see if a piece being
	 * moved is a pawn and can allow for promotion to a Queen. Also this method
	 * see's if the target space a move is about to land on will take an
	 * opponents King or not.
	 * 
	 * @param m
	 * @return
	 */
	public State move(Move m) {

		// coordinates of to and from squares
		int fRow = m.from.row;
		int fCol = m.from.col;
		int tRow = m.to.row;
		int tCol = m.to.col;

		char piece = board[fRow][fCol];

		if (color(piece) == onMove) {

			char tempHolder = board[tRow][tCol];

			board[tRow][tCol] = pawnToQueenCheck(piece, tRow);
			board[fRow][fCol] = '.';

			State s = new State();

			s.readBoard(this.printBoard());
			// s.board = board;
			s.onMove = nextPlayer();

			if (s.onMove == 'w') {
				s.turn++;
			}

			kingDead(tempHolder, s);

			// undo the move on the object. very hacky need to clean this up
			board[fRow][fCol] = piece;
			board[tRow][tCol] = tempHolder;

			return s;
		}
		throw new Error("Piece not on move!");

	}

	/**
	 * helper method to switch the players on move during a game
	 * 
	 * @return
	 */
	private char nextPlayer() {
		if (onMove == 'w') {
			return 'b';
		}

		return 'w';
	}

	/**
	 * Still need to implement this functionality but did not seem to be needed
	 * for HW 1
	 * 
	 * @param move
	 * @return
	 */
	public State humanMove(String move) {

		ArrayList<Move> legalMoves = moves();

		for (Move m : legalMoves) {
			System.out.println(m.toString());
			if (m.toString().equals(decodeMove(move))) {

				return move(m);

			}
		}

		throw new Error("Invalid move!");

	}

	public String decodeMove(String move) {
		StringBuilder decode = new StringBuilder();
		// in form of a1-b1
		int col = colLabels.indexOf(move.charAt(0));
		Character c1 = new Character(move.charAt(1));
		int row = Integer.parseInt(c1.toString())-1;
		int dCol = colLabels.indexOf((move.charAt(move.indexOf('-') + 1)));
		Character c2 = new Character(move.charAt(move.length() - 1));
		int dRow = Integer.parseInt((c2.toString()))-1;

		decode.append("((");
		decode.append(col + ",");
		decode.append(row);
		decode.append("),(");
		decode.append(dCol + "," + dRow + "))");
		return decode.toString();
	}

	/**
	 * little helper method to test if a king has died on the board due to the
	 * most recent move
	 * 
	 * @param p
	 */
	private State kingDead(char p, State s) {

		if (p == 'k') {
			s.kingGone = true;
			s.king = 0;
			return s;
		} else if (p == 'K') {
			s.kingGone = true;
			s.king = 1;
			return s;
		}
		return s;

	}

	/**
	 * Does a check to see if a pawn is at the opposite end of the board and if
	 * so upgrades it to Queen.
	 * 
	 * @param p
	 * @return
	 */
	public char pawnToQueenCheck(char p, int row) {

		// if white on move see if a just hit N border of board
		if (onMove == 'w' && p == 'P') {
			if (row == 0) {
				return 'Q';
			}
		}
		// if black on move see if it hit the S border of the board
		if (onMove == 'b' && p == 'p') {
			if (row == 5) {
				return 'q';
			}
		}

		return p;
	}

	/**
	 * Test if a game is over by seeing if the king has been taken, or if a draw
	 * has occurred
	 * 
	 * @return
	 */
	public boolean gameOver() {

		if (turn == 40 || kingGone || isDraw) {
			return true;
		}
		return false;
	}

	public float eval() {

		float wScore = 0;
		float bScore = 0;

		for (char[] row : board) {

			for (char c : row) {

				switch (c) {
				case 'p':
					bScore += 100;
					break;
				case 'P':
					wScore += 100;
					break;
				case 'n':
				case 'b':
					bScore += 300;
					break;
				case 'N':
				case 'B':
					wScore += 300;
					break;
				case 'q':
					bScore += 900;
					break;
				case 'Q':
					wScore += 900;
					break;
				case 'r':
					bScore += 500;
					break;
				case 'R':
					wScore += 500;
					break;

				default:
					break;

				}

			}

		}

		if (isDraw) {
			return 0;
		}

		if (kingGone && onMove == 'w') {
			if (king == 1) {
				return -10000;
			} else if (king == 0) {
				return 10000;
			}
		} else if (kingGone && onMove == 'b') {
			if (king == 1) {
				return 10000;
			} else if (king == 0) {
				return -10000;
			}
		}
		Random rand = new Random();
		
		double randEval = rand.nextDouble()/10000;
		// loop through to found pieces by both sides
		if (onMove == 'w') {

			return (float) ((float) (wScore - bScore)+randEval);
		} else {
			return (float) ((float) (bScore - wScore)+ randEval);
		}

	}

}
