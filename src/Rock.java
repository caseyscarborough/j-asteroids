import java.awt.Polygon;


public class Rock extends Polygon {
	int uLeftXPos, uLeftYPos;
	int xDirection = 1;
	int yDirection = 1;
	
	int width = GameBoard.boardWidth;
	int height = GameBoard.boardHeight;
	
	int[] polyXArray, polyYArray;
	
	public static int[] sPolyXArray = {10,13,26,30,29,31,26,22,8,1,2,1,4};
	public static int[] sPolyYArray = {0,2,1,8,15,20,31,31,29,22,16,7,0};
	
	public Rock(int[] polyXArray, int[] polyYArray, int pointsInPoly, int randomStartXPos, int randomStartYPos) {
		super(polyXArray, polyYArray, pointsInPoly); // Call the constructor of Polygon
		// Set the x and y direction speeds to a random int between 1 and 5
		this.xDirection = (int) (Math.random() * 4 + 1);
		this.yDirection = (int) (Math.random() * 4 + 1);
		
		this.uLeftXPos = randomStartXPos;
		this.uLeftYPos = randomStartYPos;
		
	}
	
	// Used to move 
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
