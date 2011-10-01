


public class TerrainType {
    public String terrainName;
    public boolean passable;
    public boolean walkable;
    public GameTile tile;
    
    public TerrainType(String s, int x, int y, int w, int h, boolean wa, boolean pa) {
    	walkable = wa;
    	passable = pa;
        terrainName = s;
        tile = new GameTile(x, y, "Terrain.png", w, h, 0, 0);
    }

    public TerrainType(String s, int x, int y, int w, int h) {
    	walkable = true;
    	passable = true;
        terrainName = s;
        tile = new GameTile(x, y, "Terrain.png", w, h, 0, 0);
    }
    public TerrainType(String s, GameTile t, boolean wa, boolean pa){
        terrainName = s;
    	walkable = wa;
    	passable = pa;
        tile = t;
    }

    public int getTileX(){
        return tile.getTileX();
    }
    public int getTileY(){
        return tile.getTileY();
    }
}
