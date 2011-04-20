package app;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;




/**
 * George Dittmar (c) Spring 2011
 * 
 * Main class to run the random MiniChess game
 * 
 * @author george
 * 
 */
public class MiniChess {

	boolean isThinking = false;
	static Timer timer;

	public void startRandomGame() {

		State board = new State();
		board.initBoard();

		// loop till the game is over
		long start = System.currentTimeMillis();// value?
		
		while (!board.gameOver()) {

			// call state.moves then randomly pick one then call state.move(m)
			
		
			System.out.println(board.printBoard());
			
			
			board = negamaxDriver(board);
		

		}

		System.out.println("Time: "+ (System.currentTimeMillis()-start));
	
		System.out.println("Game Over: \n" + board.printBoard());

	}

	/**
	 * The driver of the negamax algorithm
	 * 
	 * @param s
	 * @return
	 */
	public State negamaxDriver(State s) {
		isThinking = true;
		timer = new Timer();
				
		
		// starts the timer
		timer.schedule(new AiThinkTime(), 0, // initial delay
				1 * 1000); // subsequent rate
		
		int depth = 1;
		float max = Float.NEGATIVE_INFINITY;
		State move = s; // maybe do a negamax run to get an initial
		
		while (isThinking) {
			
			
			for (Move m : s.moves()) {
				
				// check if time has run out if so just break out of the loop
				State sTemp = s.move(m);
				float maxPrime = negaMax(depth, sTemp);
				
				
				if (maxPrime > max) {
				
					max = maxPrime;
					move = sTemp;
					
				}
				
			}
			
			depth++;
		}
		
		return move;
	}

	/**
	 * Performs the actual negamax search
	 * 
	 * @param depth
	 * @param s
	 * @return
	 */
	public float negaMax(int depth, State s) {

		float max = Float.NEGATIVE_INFINITY;
		// check to see if we have reached the maximum depth or if the state is
		// an ending state
		if (s.gameOver() || depth <= 0 || !isThinking) {
			
			return (float) (s.eval());
		}

		// possibly change this to a while loop that checks to see if there is time or not && there are moves to make
		//while(!isThinking&& count <s.moves().size())
		for (Move m : s.moves()) {
			
			// check if time is up
			State sTemp = s.move(m);

			float score = -negaMax(depth - 1, sTemp);
			if (score > max) {
				max = score;
			}
		}

		return  max;

	}

	public static void main(String[] args) {
		MiniChess game = new MiniChess();
		game.startRandomGame();
	}

	public class AiThinkTime extends TimerTask {

		int time = 2;

		@Override
		public void run() {
			// TODO Auto-generated method stub

			if (time > 0) {
				time--;
			} else {
				isThinking = false;
				// look at first threads ending state see if the score is better
				// than what we currently have
				timer.cancel();
			}
		}
	}
}
