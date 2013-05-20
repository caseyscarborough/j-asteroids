import java.awt.Polygon;

/**
 * The SpaceShip class holds data needed for the drawing and
 * movement of the space ship for the game.
 * @author Casey Scarborough
 * @see GameBoard
 *
 */
@SuppressWarnings("serial")
public class SpaceShip extends Polygon {
	int uLeftXPos = 500;
	int uLeftYPos = 400;
	int xDirection = 0;
	int yDirection = 0;
	
	int width = GameBoard.boardWidth;
	int height = GameBoard.boardHeight;
	
	// Holds x and y coordinates for the ship
	public static int[] polyXArray = {-13, 14, -13, -5, -13};
	public static int[] polyYArray = {-15, 0, 15, 0, -15};
	
	static int rotationAngle = 0;
	
	public SpaceShip() {
		super(polyXArray, polyYArray, 5);
	}
	
	public void move() {
		int uLeftXPos = super.xpoints[0];
		int uLeftYPos = super.ypoints[0];
		
		// Collision detection for rocks, if it hits a wall, direction changes
		if(uLeftXPos < 0 || (uLeftXPos + 25) > width) this.xDirection *= -1;
		if(uLeftYPos < 0 || (uLeftYPos + 50) > height) this.yDirection *= -1;
		
		// Move the values of the points for the polygon in their direction
		for(int i = 0; i < super.xpoints.length; i++) {
			super.xpoints[i] += xDirection;
			super.ypoints[i] += yDirection;
		}
		
		super.xpoints = SpaceShip.polyXArray;
		super.ypoints = SpaceShip.polyYArray;
	}
}
