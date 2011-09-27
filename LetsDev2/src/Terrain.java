

public class Terrain extends GamePhysical {
    public TerrainType terType;
    
    public Terrain(float i, float j, float k, TerrainType t) {
        super(i,j,k);
        terType = t;
        tile = terType.tile;
    }
}
