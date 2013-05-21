package asteroids;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Holds the information needed for creation of the game board
 * and also is used as the main driver for the application.
 * @author Casey Scarborough
 * @since 2013-05-19
 * @version 1.1.1
 * @see Rock
 * @see SpaceShip
 * @see Key
 * @see GameDrawingPanel
 * @see RepaintTheBoard
 */
@SuppressWarnings("serial")
public class GameBoard extends JFrame {
	// Height and width of the gameboard
	public static int boardWidth = 1000;
	public static int boardHeight = 800;
	public static int numberOfAsteroids = 15;
	
	public static boolean keyHeld = false;
	public static int keyHeldCode;
	public static ArrayList<Laser> lasers = new ArrayList<>();
	public static int points = 0;
	public static JLabel score;
	public static JPanel scorePanel, resultsPanel;
	public static JLabel results;
	public static JLayeredPane lpane;
	
	static int shotsFired = 0;

	/**
	 * This is the main method and driver for the application.
	 * It sole purpose is to create the game board.
	 * @param args
	 */
	public static void main(String[] args) {
		new GameBoard();
	}

	/**
	 * The constructor for the GameBoard class; sets the size, 
	 * title, default close operation, and creates a new game panel.
	 */
	public GameBoard() {
		this.setSize(boardWidth, boardHeight);
		this.setTitle("Java Asteroids");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		score = new JLabel();
		score.setFont(new Font("Sans-serif", Font.PLAIN, 20));
		score.setForeground(Color.WHITE);
		score.setText("Score: " + points);
		
		results = new JLabel();
		results.setFont(new Font("Sans-serif", Font.PLAIN, 20));
		results.setForeground(Color.WHITE);
		
		scorePanel = new JPanel(new BorderLayout());
		scorePanel.setBackground(Color.BLACK);
		
		resultsPanel = new JPanel(new BorderLayout());
		resultsPanel.setBackground(Color.BLACK);
		
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			// Check for key presses in the game
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == Key.W) {
					SpaceShip.interaction = true;
					playSoundEffect(Sound.thrust);
					keyHeldCode = e.getKeyCode();
					keyHeld = true;
				} else if (e.getKeyCode() == Key.S) {
					SpaceShip.interaction = true;
					keyHeldCode = e.getKeyCode();
					keyHeld = true;
				} else if (e.getKeyCode() == Key.D) {
					SpaceShip.interaction = true;
					keyHeldCode = e.getKeyCode();
					keyHeld = true;
				 } else if (e.getKeyCode() == Key.A) {
					SpaceShip.interaction = true;
					keyHeldCode = e.getKeyCode();
					keyHeld = true;
				} else if( e.getKeyCode() == Key.E) {
					System.out.println("Stopping ship..."); 
					keyHeldCode = e.getKeyCode();
					keyHeld = true;
				} else if (e.getKeyCode() == Key.ENTER) {
					SpaceShip.interaction = true;
					playSoundEffect(Sound.laser);
					lasers.add(new Laser(GameDrawingPanel.spaceShip.getShipNoseX(), 
							GameDrawingPanel.spaceShip.getShipNoseY(),
							GameDrawingPanel.spaceShip.getRotationAngle()));
					shotsFired += 1;
				}
			}
			
			// When the key is released, lets the object know by setting
			// keyHeld to false
			@Override
			public void keyReleased(KeyEvent e) {
				keyHeld = false;
			}
			
		});
		
		GameDrawingPanel gamePanel = new GameDrawingPanel();
		this.add(gamePanel, BorderLayout.CENTER);
		
		scorePanel.add(score, BorderLayout.WEST);
		this.add(scorePanel, BorderLayout.NORTH);
		this.add(resultsPanel, BorderLayout.SOUTH);
		
		// Create a new thread pool with 5 threads
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(5);
		// Repaint the game board every 20ms, method, initial delay, subsequent delay, time unit
		executor.scheduleAtFixedRate(new RepaintTheBoard(this), 0L, 20L, TimeUnit.MILLISECONDS);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	/**
	 * Used to play sound.
	 * @param soundToPlay a String specifying the location of the sound file.
	 */
	public static void playSoundEffect(String soundToPlay) {
		URL soundLocation;
		try {
			soundLocation = new URL(soundToPlay);
			Clip clip = null;
			clip = AudioSystem.getClip();
			AudioInputStream inputStream;
			inputStream = AudioSystem.getAudioInputStream(soundLocation);
			clip.open(inputStream);
			clip.loop(0);
			clip.start();
		} catch (LineUnavailableException | UnsupportedAudioFileException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void displayResults(int asteroidsDestroyed, int timesExploded) {
		DecimalFormat twoDecimalPlaces = new DecimalFormat("#.##");
		double accuracy = ((double)asteroidsDestroyed/(double)GameBoard.shotsFired)*100;
		accuracy = Double.valueOf(twoDecimalPlaces.format(accuracy));
		results.setText("Times Exploded: " + timesExploded + "      Asteroids Destroyed: " + asteroidsDestroyed + "      Shots Fired: " + GameBoard.shotsFired + "      Accuracy: " + accuracy + "%");
		resultsPanel.add(results, BorderLayout.SOUTH);
	}
}



/**
 * Implements Runnable interface. It is a thread that will continually redraw
 * the screen while all other code still executes
 * @author Casey Scarborough
 *
 */
class RepaintTheBoard implements Runnable {
	GameBoard gameBoard;
	public RepaintTheBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}
	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		gameBoard.repaint();
	}
}


/**
 * The GameDrawingPanel class extends JComponent and contains the objects
 * that will be used in the game. It is essentially the game panel.
 * @author Casey Scarborough
 */
@SuppressWarnings("serial")
class GameDrawingPanel extends JComponent {
	
	/**
	 * An ArrayList used to hold all of the currently displayed rocks.
	 */
	public ArrayList<Rock> rocks = new ArrayList<>();
	
	// Get the x and y points for the rock
	int[] polyXArray = Rock.sPolyXArray;
	int[] polyYArray = Rock.sPolyYArray;
	
	static SpaceShip spaceShip = new SpaceShip();
	
	// Get the game board's height and width
	int width = GameBoard.boardWidth;
	int height = GameBoard.boardHeight;
	
	// Create 15 Rock objects and store them in our rocks ArrayList
	public GameDrawingPanel() {
		for(int i = 0; i < GameBoard.numberOfAsteroids; i++) {
			// Get a random x and y starting position, the -40 is to keep rock on the screen
			int randomStartXPos = (int) (Math.random() * (GameBoard.boardWidth - 40) + 1);
			int randomStartYPos = (int) (Math.random() * (GameBoard.boardHeight - 40) + 1);
			
			// Create the rock using the constructor and add it to our array
			rocks.add(new Rock(Rock.getPolyXArray(randomStartXPos), Rock.getPolyYArray(randomStartYPos), 13, randomStartXPos, randomStartYPos));
			Rock.rocks = rocks;
		}
	}
	
	/**
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		Graphics2D graphicSettings = (Graphics2D) g;
		
		AffineTransform identity = new AffineTransform();
		
		// Fill the background width black the height and width of the game board
		graphicSettings.setColor(Color.BLACK);
		graphicSettings.fillRect(0, 0, getWidth(), getHeight());
		
		// Set the rendering rules
		graphicSettings.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphicSettings.setPaint(Color.WHITE);
		
		// Cycle through all rocks in rocks ArrayList
		for(Rock rock : rocks) {
			if(rock.onScreen) {
				rock.move(spaceShip, GameBoard.lasers); // Move the rock
				graphicSettings.draw(rock); // Draw it on the screen
			}
		}
		
		// Check to see if the D or A keys are being held and spins the ship in the correct direction
		if(GameBoard.keyHeld == true && GameBoard.keyHeldCode == Key.D) {
			spaceShip.increaseRotationAngle();
		} else if (GameBoard.keyHeld == true && GameBoard.keyHeldCode == Key.A) {
			spaceShip.decreaseRotationAngle();
		} else if (GameBoard.keyHeld == true && GameBoard.keyHeldCode == Key.W) {
			spaceShip.setMovingAngle(spaceShip.getRotationAngle());
			spaceShip.increaseXVelocity(spaceShip.shipXMoveAngle(spaceShip.getMovingAngle())*0.1);
			spaceShip.increaseYVelocity(spaceShip.shipYMoveAngle(spaceShip.getMovingAngle())*0.1);
		} else if (GameBoard.keyHeld == true && GameBoard.keyHeldCode == Key.S) {
			spaceShip.setMovingAngle(spaceShip.getRotationAngle());
			spaceShip.decreaseXVelocity(spaceShip.shipXMoveAngle(spaceShip.getMovingAngle())*0.1);
			spaceShip.decreaseYVelocity(spaceShip.shipYMoveAngle(spaceShip.getMovingAngle())*0.1);
		} else if (GameBoard.keyHeld == true && GameBoard.keyHeldCode == Key.E) {
			spaceShip.stopShip();
		}
		
		spaceShip.move();
		
		// Sets the origin of the screen
		graphicSettings.setTransform(identity);
		// Moves the ship to the center of the screen
		graphicSettings.translate(spaceShip.getXCenter(), spaceShip.getYCenter());
		// Rotate the ship
		graphicSettings.rotate(Math.toRadians(spaceShip.getRotationAngle()));
		
		graphicSettings.draw(spaceShip);
		
		// Draw lasers
		for (Laser laser : GameBoard.lasers) {
			laser.move();
			if(laser.onScreen) {
				graphicSettings.setTransform(identity);
				graphicSettings.translate(laser.getXCenter(), laser.getYCenter());
				graphicSettings.draw(laser);
			}
		}
	}
}
