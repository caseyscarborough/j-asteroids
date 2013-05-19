import java.awt.Polygon;

public class SpaceShip extends Polygon {
	int uLeftXPos = 500;
	int uLeftYPos = 400;
	int xDirection = 0;
	int yDirection = 0;
	
	int width = GameBoard.boardWidth;
	int height = GameBoard.boardHeight;
	
	public static int[] polyXArray = {500, 527, 500, 508, 500};
	public static int[] polyYArray = {400, 415, 430, 415, 400};
	
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
	}
}
