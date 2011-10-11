

public class GameObject extends GamePhysical {
    Terrain terrain;
	Vec3 speed;
	
    public GameObject() {
        super();
        speed = new Vec3();
        terrain = null;
    }
    public GameObject(float i, float j , float k) {
        super(i,j,k);
        speed = new Vec3();
        hasMoved();
    }
    
    public void tick(){
        if(speed.size() > 0) {
	        x += speed.x;
	        y += speed.y;
	        z += speed.z;
	        hasMoved();
        }
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
