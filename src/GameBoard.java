import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class GameBoard extends JFrame {
	// Height and width of the gameboard
	public static int boardWidth = 800;
	public static int boardHeight = 800;

	
	public static void main(String[] args) {
		new GameBoard();
	}
	

	public GameBoard() {
		this.setSize(boardWidth, boardHeight);
		this.setTitle("Java Asteroids");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GameDrawingPanel gamePanel = new GameDrawingPanel();
		this.add(gamePanel, BorderLayout.CENTER);
		
		// Create a new thread pool with 5 threads
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
		// Repaint the game board every 20ms, method, initial delay, subsequent delay, time unit
		executor.scheduleAtFixedRate(new RepaintTheBoard(this), 0L, 20L, TimeUnit.MILLISECONDS);
		this.setVisible(true);
	}
}


// Implements Runnable interface, is a thread that will continually redraw
// the screen while all other code still executes
class RepaintTheBoard implements Runnable {
	GameBoard gameBoard;
	public RepaintTheBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	public void run() {
		gameBoard.repaint();
	}
}

// The panel that we are drawing on
class GameDrawingPanel extends JComponent {
	// Create an array list to hold all rocks
	public ArrayList<Rock> rocks = new ArrayList<>();
	
	// Get the x and y points for the rock
	int[] polyXArray = Rock.sPolyXArray;
	int[] polyYArray = Rock.sPolyYArray;
	
	// Get the game board's height and width
	int width = GameBoard.boardWidth;
	int height = GameBoard.boardHeight;
	
	// Create 50 Rock objects and store them in our rocks ArrayList
	public GameDrawingPanel() {
		for(int i = 0; i < 10; i++) {
			// Get a random x and y starting position, the -40 is to keep rock on the screen
			int randomStartXPos = (int) (Math.random() * (GameBoard.boardWidth - 40) + 1);
			int randomStartYPos = (int) (Math.random() * (GameBoard.boardHeight - 40) + 1);
			
			// Create the rock using the constructor and add it to our array
			rocks.add(new Rock(Rock.getPolyXArray(randomStartXPos), Rock.getPolyYArray(randomStartYPos), 13, randomStartXPos, randomStartYPos));
		}
	}
	
	public void paint(Graphics g) {
		Graphics2D graphicSettings = (Graphics2D) g;
		// Fill the background width black the height and width of the game board
		graphicSettings.setColor(Color.BLACK);
		graphicSettings.fillRect(0, 0, getWidth(), getHeight());
		
		// Set the rendering rules
		graphicSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphicSettings.setPaint(Color.WHITE);
		
		// Cycle through all rocks in rocks ArrayList
		for(Rock rock : rocks) {
			rock.move(); // Move the rock
			graphicSettings.draw(rock); // Draw it on the screen
		}
		
	}
}
