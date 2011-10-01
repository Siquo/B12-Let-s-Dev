
public class GameTile implements DrawableTile{
    public int tileLocX, tileLocY, rnd;
    public int tileWidth, tileHeight, tileY, tileX;
    
    public String imageFileName;
    
    public GameTile() {
        imageFileName = "Default.png";
        tileLocX = 0;
        tileLocY = 0;
        tileWidth = 32;
        tileHeight = 20;
        tileX = 0;
        tileY = 0;
    }
    public GameTile(int x, int y, String fName, int w, int h, int z, int z2) {
        imageFileName = fName;  // file
        tileLocX = x; // location in the file
        tileLocY = y;
        tileWidth = w;  // width/height
        tileHeight = h;
        tileX = z; // These two are graphical offset on screen: draw tileX to the left and tileY to the right
        tileY = z2;// of the actual location
    }

    public int getTileX(){
        return tileLocX;
    }
    public int getTileY(){
        return tileLocY;
    }
}
