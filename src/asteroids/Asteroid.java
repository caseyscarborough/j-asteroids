package asteroids;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * The asteroid class is a subclass of Polygon and holds the data and methods
 * needed to draw and move the asteroids. Each asteroid is drawn using a set of
 * points held in two static arrays.
 * @author Casey Scarborough
 * @see GameBoard
 * @version 1.0.4
 */
@SuppressWarnings("serial")
public class Asteroid extends Polygon {
	
	private int uLeftXPos;
	private int uLeftYPos;
	
	private int xDirection = 2;
	private int yDirection = 2;
	private static int asteroidsDestroyed = 0;
	private static int timesExploded = 0;
	
	boolean onScreen = true;
	
	// Get the width and height of the gameboard
	private int width = GameBoard.boardWidth;
	private int height = GameBoard.boardHeight;
	
	// Hold a height and width for our asteroid
	private int asteroidWidth = 29;
	private int asteroidHeight = 31;
		
	// Set two arrays to hold the points for our asteroid polygons
	private static int[] sPolyXArray = {10,13,26,30,29,31,26,22,8,1,2,1,4};
	private static int[] sPolyYArray = {0,2,1,8,15,20,31,31,29,22,16,7,0};
	public static ArrayList<Asteroid> asteroids;
	
	public Asteroid(int[] polyXArray, int[] polyYArray, int pointsInPoly, int randomStartXPos, int randomStartYPos) {
		super(polyXArray, polyYArray, pointsInPoly); // Call the constructor of Polygon
		// Set the x and y direction speeds to a random int between -4 and 4, not including 0
		int randomX = (int) (Math.random() * 8);
		int randomY = (int) (Math.random() * 8);
		if(randomX != 4 && randomY != 4) {
			this.xDirection = randomX - 4;
			this.yDirection = randomY - 4;
		}
	}
	
	// Getter methods
	public static int[] getStartingPolyXArray() { return sPolyXArray; }
	public static int[] getStartingPolyYArray() { return sPolyYArray; }
	
	// Used to return a rectangle used for collision detection of the asteroids
	public Rectangle getBounds() {
		return new Rectangle(super.xpoints[0], super.ypoints[0], asteroidWidth, asteroidHeight);
	}
	
	// Used to move to asteroids and check for collisions between them
	public void move(SpaceShip spaceShip, ArrayList<Laser> lasers) {
		
		// Create a new rectangle based on the current asteroid
		Rectangle asteroidToCheck = this.getBounds();
		for(Asteroid asteroid : asteroids) { // Loop through the asteroids and check
			if (asteroid.onScreen) {
				Rectangle otherasteroid = asteroid.getBounds();
				// If we are not checking it against itself, and it intersects with another asteroid
				if(asteroid != this && otherasteroid.intersects(asteroidToCheck)) {
					// Swap the directions of the asteroids (asteroid1 gets asteroid2's values and vice versa)
					int tempXDirection = this.xDirection;
					int tempYDirection = this.yDirection;
					this.xDirection = asteroid.xDirection;
					this.yDirection = asteroid.yDirection;
					asteroid.xDirection = tempXDirection;
					asteroid.yDirection = tempYDirection;
				}
				// Check for collision with asteroid and ship
				Rectangle shipBox = spaceShip.getBounds();
				if(SpaceShip.interaction == true) {
					// If the asteroid's boundary intersects the ship's boundary
					if(otherasteroid.intersects(shipBox)) {
						// Set interaction to false to keep from dying from start and play sound
						SpaceShip.interaction = false;
						Sound.playSoundEffect(Sound.explosion);
						// Set the ship to the center and set velocity to 0
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
				// Check for collision with laser and asteroid
				for(Laser laser : lasers) {
					if(laser.onScreen) {
						// If the asteroid or the other asteroid's boundary contains a laser
						if(otherasteroid.contains(laser.getXCenter(), laser.getYCenter()) | asteroidToCheck.contains(laser.getXCenter(), laser.getYCenter())) {
							// Remove the asteroid and the laser and play sound
							asteroid.onScreen = false;
							laser.onScreen = false;
							Sound.playSoundEffect(Sound.explosion);
							GameBoard.points += 10;
							GameBoard.score.setText("Score: " + GameBoard.points);
							System.out.println("You destroyed an asteroid! You gain 10 points.");
							System.out.println("Score: " + GameBoard.points);
							asteroidsDestroyed += 1;
							System.out.println("Asteroids destroyed: " + asteroidsDestroyed); 
							// If all asteroids are destroyed, end game
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
		
		uLeftXPos = super.xpoints[0];
		uLeftYPos = super.ypoints[0];
		
		// Collision detection for asteroids, if it hits a wall, direction changes
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
