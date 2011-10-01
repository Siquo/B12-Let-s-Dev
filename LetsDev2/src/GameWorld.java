

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
    
    public GameWorld(int sd) {
        super();
        GamePhysical.setWorld(this);
        seed = sd;
        rnd = new Random(seed);
        terrain = new HashMap<Integer,TreeMap<Integer,Terrain> >();
        terTypes = new HashMap<String,TerrainType>(100);
        terTypes.put("Rock Floor", new TerrainType("Rock Floor", new GameTile(32,0,"Terrain.png",32,15,0,12), true, true));
        terTypes.put("Rock Wall", new TerrainType("Rock Wall", new GameTile(64,60,"Terrain.png",32,30,0,0),false,false));
        objects = new LinkedList<GameObject>();
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
    
    public void addObject(GameObject ob){
        objects.add(ob);
    }
    public void tickAll(){ // Update objects and stuff
        Iterator<GameObject> i = objects.iterator();
        GameObject o;
        while(i.hasNext()){
            o = (GameObject)i.next();
            o.tick();
        }
    }
    
    private Terrain generateTerrain(int i, int j, int k){
        if(i==10 && j == 10){
        	return new Terrain(i,j,k, terTypes.get("Rock Floor"));
        }
        if(rnd.nextInt(10)<2){
        //if(j> 30 || i > 20 ){ //|| rnd.nextInt(10)<2){
            return new Terrain(i,j,k, terTypes.get("Rock Wall"));
        }
      	return new Terrain(i,j,k, terTypes.get("Rock Floor"));
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

}
