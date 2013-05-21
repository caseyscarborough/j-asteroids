package asteroids;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * Holds data needed for the drawing and
 * movement of the space ship for the game.
 * @author Casey Scarborough
 * @see GameBoard
 * @version 1.1.0
 */
@SuppressWarnings("serial")
public class SpaceShip extends Polygon {
	
	// Set the initial velocities for the ship as well as the gameboard width and height
	private double xVelocity = 0, yVelocity = 0;
	int gameBoardWidth = GameBoard.boardWidth;
	int gameBoardHeight = GameBoard.boardHeight;
	
	// Set the center point for the ship and its points to be drawn
	private double centerX = gameBoardWidth/2, centerY = gameBoardHeight/2;
	public static int[] polyXArray = {-13,14,-13,-5,-13};
	public static int[] polyYArray = {-15,0,15,0,-15};
	
	// Set the width and height of the ship
	private int shipWidth = 27, shipHeight = 30;
	private double uLeftXPos = getXCenter() + SpaceShip.polyXArray[0];
	private double uLeftYPos = getYCenter() + SpaceShip.polyYArray[0];

	private double rotationAngle = 0, movingAngle = 0;
	
	// Used so that ship cannot be destroyed until moved
	public static boolean interaction = false;
	
	public SpaceShip() {
		super(polyXArray, polyYArray, 5);
	}
	
	// Getters and setters
	public double getXCenter(){ return centerX; }
	public double getYCenter(){ return centerY; }
	public void setXCenter(double xCenter){ this.centerX = xCenter; }
	public void setYCenter(double yCenter){ this.centerY = yCenter; }

	
	public double getULeftXPos() { return this.uLeftXPos; }
	public double getULeftYPos() { return this.uLeftYPos; }
	public void setULeftXPos(double uLXPos){ this.uLeftXPos = uLXPos; }
	public void setULeftYPos(double uLYPos){ this.uLeftYPos = uLYPos; }
	
	public int getShipWidth() { return this.shipWidth; }
	public int getShipHeight() { return this.shipHeight; }
	
	public double getXVelocity() { return this.xVelocity; }
	public double getYVelocity() { return this.yVelocity; }
	public void setXVelocity(double xVel) { this.xVelocity = xVel; }
	public void setYVelocity(double yVel) { this.yVelocity = yVel; }
	
	public void setMovingAngle(double moveAngle) { this.movingAngle = moveAngle; }
	public double getMovingAngle() { return this.movingAngle; }
	public double getRotationAngle() { return this.rotationAngle; }

	
	// Increase and decrease methods for position and velocity
	public void increaseXPosition(double increaseAmount) { this.centerX += increaseAmount; }
	public void increaseYPosition(double increaseAmount) { this.centerY += increaseAmount; }
	public void increaseXVelocity(double increaseAmount) { this.xVelocity += increaseAmount; }
	public void increaseYVelocity(double increaseAmount) { this.yVelocity += increaseAmount; }
	public void decreaseXVelocity(double decreaseAmount) { this.xVelocity -= decreaseAmount; }
	public void decreaseYVelocity(double decreaseAmount) { this.yVelocity -= decreaseAmount; }
	public void increaseMovingAngle(double moveAngle) { this.movingAngle += moveAngle; }
	
	/**
	 * Stops the ships movement in place.
	 */
	public void stopShip() { this.xVelocity = 0; this.yVelocity = 0; this.movingAngle = rotationAngle; }
	
	
	public double shipXMoveAngle(double xMoveAngle) {
		return (double) (Math.cos(xMoveAngle * Math.PI / 180));
	}
	
	public double shipYMoveAngle(double yMoveAngle) {
		return (double) (Math.sin(yMoveAngle * Math.PI / 180));
	}
	
	/**
	 * Used to rotate the ship clockwise by 5 degrees.
	 */
	public void increaseRotationAngle() { 
		if(getRotationAngle() >= 355) { this.rotationAngle = 0; }
		else { this.rotationAngle += 5; }
	}
	/**
	 * Used to rotate the ship counter-clockwise by 5 degrees.
	 */
	public void decreaseRotationAngle() { 
		if(getRotationAngle() <= 0) { this.rotationAngle = 355; }
		else { this.rotationAngle -= 5; }
	}
	
	/**
	 * @see java.awt.Polygon#getBounds()
	 */
	public Rectangle getBounds() {
		return new Rectangle((int) getXCenter() - 14, (int) getYCenter() - 15, getShipWidth(), getShipHeight());
	}
	
	
	public double getShipNoseX() {
		return this.getXCenter() + Math.cos(rotationAngle) * 14;
	}
	
	public double getShipNoseY() {
		return this.getYCenter() + Math.sin(rotationAngle) * 14;
	}
	
	/**
	 * Used to move the space ship around the screen.
	 */
	public void move() {
		this.increaseXPosition(this.getXVelocity());
		if(this.getXCenter() < 0) {
			this.setXCenter(gameBoardWidth);
		} else if (this.getXCenter() > gameBoardWidth) {
			this.setXCenter(0);
		}
		
		this.increaseYPosition(this.getYVelocity());
		if(this.getYCenter() < 0) {
			this.setYCenter(gameBoardHeight);
		} else if (this.getYCenter() > gameBoardHeight) {
			this.setYCenter(0);
		}
	}
}
