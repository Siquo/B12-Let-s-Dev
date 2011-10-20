

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

public class GameWorld {
    static final int HDIM = 100;  //Horizontal max dim   
    static final int VDIM = 50;  //Vertical max dim MUST BE SMALLER THAN HDIM
    public Random rnd;
    public int seed;
    public HashMap<Integer, TreeMap<Integer, Terrain> > terrain;
    public HashMap<String, TerrainType> terTypes;
    
    public LinkedList<GameObject> objects;
    public LinkedList<GameObject> toBeDeleted;
    
    public GameWorld(int sd) {
        super();
        GamePhysical.setWorld(this);
        seed = sd;
        rnd = new Random(seed);
        terrain = new HashMap<Integer,TreeMap<Integer,Terrain> >();
        terTypes = new HashMap<String,TerrainType>(100);
        terTypes.put("Rock Floor1", new TerrainType("Rock Floor1", new GameTile(64,32,"Graphics.png",32,30,0,0), true, true));
        terTypes.put("Rock Floor0", new TerrainType("Rock Floor0", new GameTile(96,32,"Graphics.png",32,30,0,0), true, true));
        terTypes.put("Rock Wall1", new TerrainType("Rock Wall1", new GameTile(0,64,"Graphics.png",32,30,0,0),false,false));
        terTypes.put("Rock Wall0", new TerrainType("Rock Wall0", new GameTile(32,64,"Graphics.png",32,30,0,0),false,false));
        terTypes.put("Ore Wall1", new TerrainType("Ore Wall1", new GameTile(0,32,"Graphics.png",32,30,0,0),false,false));
        terTypes.put("Ore Wall0", new TerrainType("Ore Wall0", new GameTile(32,32,"Graphics.png",32,30,0,0),false,false));
        terTypes.put("Stairs up", new TerrainType("Stairs up", new GameTile(0,0,"Graphics.png",32,30,0,0),true,true));
        terTypes.put("Stairs down", new TerrainType("Stairs down", new GameTile(0,32,"Graphics.png",32,30,0,0),true,true));
        objects = new LinkedList<GameObject>();
        toBeDeleted = new LinkedList<GameObject>();
    }
    
    
    public int getMapID(int i,int j,int k){
//        return i*HDIM*HDIM*4+j*HDIM*2+k;
        return i*HDIM*2+j;
    }
    
    public Terrain getTerrain(int i,int j,int k){
        if(terrain.get(k) == null){
            terrain.put(k, getNewLevel(k));
        }
        int mapID = getMapID(i,j,k);
        Terrain t = terrain.get(k).get(mapID);
        if(t != null){
            return t;
        } else {
            t  = generateTerrain(i,j,k);
            terrain.get(k).put(mapID, t);
            return t;
        }
        //return null;
    }
    
    public void addObject(GameObject ob){ // add object to the world
        objects.add(ob);
    }
    
    public void removeObject(GameObject ob) {
    	ob.delete(); // set deleted flag on object
    	toBeDeleted.add(ob);
    }
    
    public void tickAll(){ // Update objects and stuff
        Iterator<GameObject> i = objects.iterator();
        GameObject o;
        while(i.hasNext()){
            o = (GameObject)i.next();
            o.tick();
        }
        i = toBeDeleted.iterator();
        while(i.hasNext()){
            o = (GameObject)i.next();
          	objects.remove(o);
        }
        
    }
    
    private Terrain generateTerrain(int i, int j, int k){
    	if(k == 0) {
        	String tmp = "Rock Floor"+rnd.nextInt(2);
        	return new Terrain(i,j,k, terTypes.get(tmp));
    	}
    	
        if(i==10 && j == 10){
        	String tmp = "Rock Floor"+rnd.nextInt(2);
        	return new Terrain(i,j,k, terTypes.get(tmp));
        }
        if(rnd.nextInt(10)<2){
        //if(j> 30 || i > 20 ){ //|| rnd.nextInt(10)<2){
        	String tmp = "Rock Wall"+rnd.nextInt(2);
            return new Terrain(i,j,k, terTypes.get(tmp));
        }
    	String tmp = "Rock Floor"+rnd.nextInt(2);
      	return new Terrain(i,j,k, terTypes.get(tmp));
    }
    
    public LinkedList<GamePhysical> getDrawable(int i, int j, int k){
        LinkedList<GamePhysical> posArr = new LinkedList<GamePhysical>();
        Terrain t = getTerrain(i,j,k);
        if(t != null){
            posArr.add(t);
        }
        return posArr;
    }
    public TreeMap<Integer, Terrain> getNewLevel(int k){
        TreeMap<Integer, Terrain> t = new TreeMap<Integer, Terrain>();
        for(int i=0;i<HDIM;i++){
            for(int j=0;j<HDIM;j++){
                t.put(getMapID(i,j,k), generateTerrain(i,j,k));
            }
        }
        return t;
    }
    public boolean isOutOfBounds(GamePhysical g) {
    	if(g.x < -HDIM || g.x > HDIM || g.y < -HDIM || g.y > HDIM) {
    		return true;
    	}
    	return false;
    }

}
