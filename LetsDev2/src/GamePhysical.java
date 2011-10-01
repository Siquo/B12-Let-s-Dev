
import java.awt.Graphics;



public class GamePhysical {
	static private GameWorld world;
	
    static enum Direction {
        NORTH, EAST, SOUTH, WEST, UP, DOWN
    }
    
    public float x, y, z;
    public GameTile tile;
    public GamePhysical() {
        super();
        x = y = z =0;
        tile = null;
    }
    public GamePhysical(float tx, float ty, float tz) {
        x = tx;
        y = ty;
        z = tz;
        tile = null;
    }
    
    public void draw(Graphics g){

        
    }
    static public GameWorld getWorld(){
    	return world;
    }
    static public void setWorld(GameWorld w){
    	world = w;
    }    
}
