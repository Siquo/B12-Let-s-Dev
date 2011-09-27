


public class TerrainType {
    public String terrainName;
    public boolean passable;
    public GameTile tile;
    
    public TerrainType(String s, int x, int y, int w, int h) {
        super();
        terrainName = s;
        tile = new GameTile(x, y, "Terrain.png", w, h, 0);
    }
    public TerrainType(String s, GameTile t){
        terrainName = s;
        tile = t;
    }

    public int getTileX(){
        return tile.getTileX();
    }
    public int getTileY(){
        return tile.getTileY();
    }
}
