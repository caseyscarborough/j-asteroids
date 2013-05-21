package asteroids;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * The Rock class is a subclass of Polygon and holds the data and methods
 * needed to draw and move the rocks. Each rock is drawn using a set of
 * points held in two static arrays.
 * @author Casey Scarborough
 * @see GameBoard
 * @version 1.0.4
 */
@SuppressWarnings("serial")
public class Rock extends Polygon {
	int uLeftXPos, uLeftYPos;
	int xDirection = 1;
	int yDirection = 1;
	static int asteroidsDestroyed = 0;
	static int timesExploded = 0;
	
	boolean onScreen = true;
	
	// Get the width and height of the gameboard
	int width = GameBoard.boardWidth;
	int height = GameBoard.boardHeight;
	
	// Hold a height and width for our rock
	int rockWidth = 29;
	int rockHeight = 31;
	
	int[] polyXArray, polyYArray;
	
	// Set two arrays to hold the points for our rock polygons
	public static int[] sPolyXArray = {10,13,26,30,29,31,26,22,8,1,2,1,4};
	public static int[] sPolyYArray = {0,2,1,8,15,20,31,31,29,22,16,7,0};
	public static ArrayList<Rock> rocks;
	
	public Rock(int[] polyXArray, int[] polyYArray, int pointsInPoly, int randomStartXPos, int randomStartYPos) {
		super(polyXArray, polyYArray, pointsInPoly); // Call the constructor of Polygon
		// Set the x and y direction speeds to a random int between 1 and 5
		this.xDirection = (int) (Math.random() * 4 + 1);
		this.yDirection = (int) (Math.random() * 4 + 1);
		
		this.uLeftXPos = randomStartXPos;
		this.uLeftYPos = randomStartYPos;
	}
	
	// Used to return a rectangle used for collision detection of the rocks
	public Rectangle getBounds() {
		return new Rectangle(super.xpoints[0], super.ypoints[0], rockWidth, rockHeight);
	}
	
	// Used to move to rocks and check for collisions between them
	public void move(SpaceShip spaceShip, ArrayList<Laser> lasers) {
		
		// Create a new rectangle based on the current rock
		Rectangle rockToCheck = this.getBounds();
		for(Rock rock : rocks) { // Loop through the rocks and check
			if (rock.onScreen) {
				Rectangle otherRock = rock.getBounds();
				// If we are not checking it against itself, and it intersects with another rock
				if(rock != this && otherRock.intersects(rockToCheck)) {
					// Swap the directions of the rocks (rock1 gets rock2's values and vice versa)
					int tempXDirection = this.xDirection;
					int tempYDirection = this.yDirection;
					this.xDirection = rock.xDirection;
					this.yDirection = rock.yDirection;
					rock.xDirection = tempXDirection;
					rock.yDirection = tempYDirection;
				}
				
				Rectangle shipBox = spaceShip.getBounds();
				if(SpaceShip.interaction == true) {
					if(otherRock.intersects(shipBox)) {
						SpaceShip.interaction = false;
						GameBoard.playSoundEffect(Sound.explosion);
						spaceShip.setXCenter(spaceShip.gameBoardWidth/2);
						spaceShip.setYCenter(spaceShip.gameBoardHeight/2);
						spaceShip.setXVelocity(0);
						spaceShip.setYVelocity(0);
						GameBoard.points -= 10;
						GameBoard.score.setText("Score: " + GameBoard.points);
						System.out.println("HIT! You lose 10 points."); 
						System.out.println("Score: " + GameBoard.points);
						timesExploded += 1;
					}
				}
				
				for(Laser laser : lasers) {
					if(laser.onScreen) {
						if(otherRock.contains(laser.getXCenter(), laser.getYCenter()) | rockToCheck.contains(laser.getXCenter(), laser.getYCenter())) {
							rock.onScreen = false;
							laser.onScreen = false;
							GameBoard.playSoundEffect(Sound.explosion);
							GameBoard.points += 10;
							GameBoard.score.setText("Score: " + GameBoard.points);
							System.out.println("You destroyed an asteroid! You gain 10 points.");
							System.out.println("Score: " + GameBoard.points);
							asteroidsDestroyed += 1;
							System.out.println("Asteroids destroyed: " + asteroidsDestroyed); 
							if (asteroidsDestroyed >= GameBoard.numberOfAsteroids) {
								System.out.println("Game Over!");
								GameBoard.score.setText("Game Over! Final Score: " + GameBoard.points);
								GameBoard.displayResults(asteroidsDestroyed, timesExploded);
							}
						}
					}
				}
			}
		}
		
		int uLeftXPos = super.xpoints[0];
		int uLeftYPos = super.ypoints[0];
		
		// Collision detection for rocks, if it hits a wall, direction changes
		if(uLeftXPos < 0 || (uLeftXPos + 29) > width) this.xDirection *= -1;
		if(uLeftYPos < 0 || (uLeftYPos + 31) > height) this.yDirection *= -1;
		
		// Move the values of the points for the polygon in their direction
		for(int i = 0; i < super.xpoints.length; i++) {
			super.xpoints[i] += xDirection;
			super.ypoints[i] += yDirection;
		}
	}
	
	
	// Used to create array of polygon x-points based on a random x starting position
	public static int[] getPolyXArray(int randomStartXPos) {
		// Clones the array as not to change the original asteroid shape
		int[] tempPolyXArray = (int[])sPolyXArray.clone();
		for(int i = 0; i < tempPolyXArray.length; i++) {
			tempPolyXArray[i] += randomStartXPos;
		}
		return tempPolyXArray;
	}
	
	// Used to create array of polygon y-points based on a random y starting position
	public static int[] getPolyYArray(int randomStartYPos) {
		int[] tempPolyYArray = (int[])sPolyYArray.clone();
		for(int i = 0; i < tempPolyYArray.length; i++) {
			tempPolyYArray[i] += randomStartYPos;
		}
		return tempPolyYArray;
	}
}
