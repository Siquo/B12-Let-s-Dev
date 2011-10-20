
public class GameProjectile extends GameObject {
    public GameProjectile() {
        super();
        tile = new GameTile(0,160,"Graphics.png",30,30,0,-16);
    }
    public GameProjectile(float i, float j , float k) {
        super(i,j,k);
        tile = new GameTile(0,160,"Graphics.png",30,30,0,-16);
    }
    
    public void hasMoved(){ // call this after you've moved.

    	super.hasMoved();
    }
    
}
