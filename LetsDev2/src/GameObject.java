

public class GameObject extends GamePhysical {
    Terrain terrain;
	
    public GameObject() {
        super();
        terrain = null;
    }
    public GameObject(float i, float j , float k) {
        super(i,j,k);
        hasMoved();
    }
    
    public void tick(){
        
    }
    
    public void hasMoved(){ // call this after you've moved.
    	Terrain t = getWorld().getTerrain((int)x,(int)y,(int)z);
    	if(t != terrain){
    		if(terrain != null) terrain.unRegisterObject(this);
    		t.registerObject(this);
    		terrain = t;
    	}
    	
    }
    
}
