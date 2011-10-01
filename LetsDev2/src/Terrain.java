import java.util.LinkedList;



public class Terrain extends GamePhysical {
    public TerrainType terType;
    public LinkedList<GameObject> objects;
    
    public Terrain(float i, float j, float k, TerrainType t) {
        super(i,j,k);
        terType = t;
        tile = terType.tile;
        objects = new LinkedList<GameObject>();
    }
    
    public void unRegisterObject(GameObject o){
    	objects.remove(o);
    	
    }
    public void registerObject(GameObject o){
    	if(!objects.contains(o)){
    		objects.add(o);
    	}
    }
}
