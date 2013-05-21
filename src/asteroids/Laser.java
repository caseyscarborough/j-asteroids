package asteroids;

import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * Contains the data and instructions for movement
 * for the lasers shot in the game.
 * @author Casey Scarborough
 * @version 1.0.0
 * @see GameBoard
 * @see SpaceShip
 *
 */
@SuppressWarnings("serial")
public class Laser extends Polygon {
	// Get width and height of the game board
	int gameBoardWidth = GameBoard.boardWidth;
	int gameBoardHeight = GameBoard.boardHeight;
	
	// Set the center position and set points for the laser
	private double centerX = 0, centerY = 0;
	public static int[] polyXArray = {-3,3,3,-3,-3};
	public static int[] polyYArray = {-3,-3,3,3,-3};
	
	// Set width and height, visibility, angle, and velocity
	private int laserWidth = 6, laserHeight = 6;
	public boolean onScreen = false;
	private double movingAngle = 0;
	private double xVelocity = 5, yVelocity = 5;
	
	/** Constructor for Laser sets the center point, velocity, and visibility to true. */
	public Laser(double centerX, double centerY, double movingAngle) {
		super(polyXArray, polyYArray, 5);
		
		this.centerX = centerX;
		this.centerY = centerY;
		this.movingAngle = movingAngle;
		
		this.onScreen = true;
		
		this.setXVelocity(this.laserXMoveAngle(movingAngle)*10);
		this.setYVelocity(this.laserYMoveAngle(movingAngle)*10);
	}
	
	// Setter and Getter methods
	public double getXCenter() { return this.centerX; }
	public double getYCenter() { return this.centerY; }
	public void setXCenter(double xCenter) { this.centerX = xCenter; }
	public void setYCenter(double yCenter) { this.centerY = yCenter; }
	
	public double getXVelocity() { return this.xVelocity; }
	public double getYVelocity() { return this.yVelocity; }
	public void setXVelocity(double xVelocity) { this.xVelocity = xVelocity; }
	public void setYVelocity(double yVelocity) { this.yVelocity = yVelocity; }
	
	public int getWidth() { return this.laserWidth; }
	public int getHeight() { return this.laserHeight; }
	
	public void setMovingAngle(double movingAngle) { this.movingAngle = movingAngle; }
	public double getMovingAngle() { return this.movingAngle; }
	
	/**
	 * Used to increase the x position by a set amount.
	 * @param increaseAmount the amount to increase by
	 */
	public void changeXPosition(double increaseAmount) { this.centerX += increaseAmount; }
	/**
	 * Used to increase the y position by a set amount.
	 * @param increaseAmount the amount to increase by
	 */
	public void changeYPosition(double increaseAmount) { this.centerY += increaseAmount; }
	
	public Rectangle getBounds() {
		return new Rectangle((int) getXCenter() - 6, (int) getYCenter() - 6, getWidth(), getHeight());
	}
	
	
	/**
	 * Returns the angle in the Y direction in radians in which the laser should move in.
	 */
	public double laserYMoveAngle(double movingAngle) {
		return (double) (Math.sin(movingAngle * Math.PI / 180));
	}

	/**
 	 * Returns the angle in the X direction in radians in which the laser should move in.
	 */
	public double laserXMoveAngle(double movingAngle) {
		return (double) (Math.cos(movingAngle * Math.PI / 180));
	}
	
	/**
	 * Handles the movement of the laser on the screen.
	 */
	public void move() {
		if(this.onScreen) {
			this.changeXPosition(this.getXVelocity());
			if (this.getXCenter() < 0 | this.getXCenter() > gameBoardWidth) {
				this.onScreen = false;
			}
			this.changeYPosition(this.getYVelocity());
			if (this.getYCenter() < 0 | this.getYCenter() > gameBoardHeight) {
				this.onScreen = false;
			}
		}
	}
}
