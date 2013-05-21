package asteroids;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * Holds data needed for the drawing and
 * movement of the space ship for the game.
 * @author Casey Scarborough
 * @see GameBoard
 * @version 1.0.1
 */
@SuppressWarnings("serial")
public class SpaceShip extends Polygon {
	
	private double xVelocity = 0, yVelocity = 0;
	int gameBoardWidth = GameBoard.boardWidth;
	int gameBoardHeight = GameBoard.boardHeight;
	
	private double centerX = gameBoardWidth/2, centerY = gameBoardHeight/2;
	public static int[] polyXArray = {-13,14,-13,-5,-13};
	public static int[] polyYArray = {-15,0,15,0,-15};
	
	private int shipWidth = 27, shipHeight = 30;
	private double uLeftXPos = getXCenter() + SpaceShip.polyXArray[0];
	private double uLeftYPos = getYCenter() + SpaceShip.polyYArray[0];
	
	private double rotationAngle = 0, movingAngle = 0;
	
	public SpaceShip() {
		super(polyXArray, polyYArray, 5);
	}
	
	public double getXCenter(){ return centerX; }
	public double getYCenter(){ return centerY; }
	public void setXCenter(double xCenter){ this.centerX = xCenter; }
	public void setYCenter(double yCenter){ this.centerY = yCenter; }
	
	public void increaseXPosition(double increaseAmount) { this.centerX += increaseAmount; }
	public void increaseYPosition(double increaseAmount) { this.centerY += increaseAmount; }
	
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
	
	public void increaseXVelocity(double increaseAmount) { this.xVelocity += increaseAmount; }
	public void increaseYVelocity(double increaseAmount) { this.yVelocity += increaseAmount; }
	public void decreaseXVelocity(double decreaseAmount) { this.xVelocity -= decreaseAmount; }
	public void decreaseYVelocity(double decreaseAmount) { this.yVelocity -= decreaseAmount; }
	
	public void setMovingAngle(double moveAngle) { this.movingAngle = moveAngle; }
	public double getMovingAngle() { return this.movingAngle; }
	public void increaseMovingAngle(double moveAngle) { this.movingAngle += moveAngle; }
	public void stopShip() { this.xVelocity = 0; this.yVelocity = 0; this.movingAngle = rotationAngle; }
	
	public double shipXMoveAngle(double xMoveAngle) {
		return (double) (Math.cos(xMoveAngle * Math.PI / 180));
	}
	
	public double shipYMoveAngle(double yMoveAngle) {
		return (double) (Math.sin(yMoveAngle * Math.PI / 180));
	}
	
	public double getRotationAngle() { return this.rotationAngle; }
	
	public void increaseRotationAngle() { 
		if(getRotationAngle() >= 355) { this.rotationAngle = 0; }
		else { this.rotationAngle += 5; }
	}
	public void decreaseRotationAngle() { 
		if(getRotationAngle() <= 0) { this.rotationAngle = 355; }
		else { this.rotationAngle -= 5; }
	}
	
	public Rectangle getBounds() {
		return new Rectangle(getShipWidth() - 14, getShipHeight() - 15, getShipWidth(), getShipHeight());
	}
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
