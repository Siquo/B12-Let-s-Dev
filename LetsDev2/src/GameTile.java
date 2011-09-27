
public class GameTile implements DrawableTile{
    public int tileLocX, tileLocY, rnd;
    public int tileWidth, tileHeight, tileZ;
    
    public String imageFileName;
    
    public GameTile() {
        imageFileName = "Default.png";
        tileLocX = 0;
        tileLocY = 0;
        tileWidth = 32;
        tileHeight = 20;
        tileZ = 0;
    }
    public GameTile(int x, int y, String fName, int w, int h, int z) {
        imageFileName = fName;
        tileLocX = x;
        tileLocY = y;
        tileWidth = w;
        tileHeight = h;
        tileZ = z;
    }

    public int getTileX(){
        return tileLocX;
    }
    public int getTileY(){
        return tileLocY;
    }
}
