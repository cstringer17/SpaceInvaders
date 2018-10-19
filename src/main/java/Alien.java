
public class Alien extends Sprite{

	
	boolean moveRight;
	boolean moveLeft;
	boolean isVisible;
	
	public Alien(int x, int y, int s) {
		super(x,y,s);
		
		moveRight = true;
		moveLeft = false;
		isVisible = true;
	}
	
}
