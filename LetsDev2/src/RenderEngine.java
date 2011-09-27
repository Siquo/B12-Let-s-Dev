

import java.awt.Graphics2D;

import java.awt.*;


import java.net.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class RenderEngine {
    int viewPortX, viewPortY;
    int viewWidth, viewHeight;
    int tileSizeX, tileSizeY;
    
    GameApplet app;
    HashMap<String, Image> images;
    URL url;
    int viewTileMinX, viewTileMinY, viewTileMaxX, viewTileMaxY;
    int z; // Current z level to draw
    
    MediaTracker mt;
    
    public RenderEngine(URL u, GameApplet a) {
        super();
        url = u;
        app = a;
        mt = new MediaTracker(app);

        // This is in pixels
        viewPortX = 300;
        viewPortY = 100;
        tileSizeX = 32;
        tileSizeY = 20;
        viewWidth = GameApplet.CANVAS_WIDTH ;/// tileSizeX;
        viewHeight = GameApplet.CANVAS_HEIGHT ;// tileSizeY;
        
        // this is in tiles
        viewTileMinX = viewPortX/tileSizeX;
        viewTileMaxX = viewTileMinX + viewWidth/tileSizeX;
        viewTileMinY = viewPortY/tileSizeY;
        viewTileMaxY = viewTileMinY + viewWidth/tileSizeY;

        images = new HashMap<String, Image>();
    }
    
    public void centerViewPort(GamePhysical p){
        int x, y, tx, ty;
        x = (int)(p.x-p.y)/2;
        y = (int)(p.tile.tileZ +((p.x+p.y)/2))/2;
        tx = p.tile.getTileX();
        ty = p.tile.getTileY();    	
        viewPortX = viewWidth + tx;
        viewPortY = viewHeight + ty;
        
    }
    
    public void drawWorld(Graphics2D g, GameWorld w){
        LinkedList<GamePhysical> d;
        for(int i=viewTileMinX;i<viewTileMaxX;i++){
            for(int j=viewTileMinY;j<viewTileMaxY;j++){
                d = w.getDrawable(i,j,z);
                Iterator<GamePhysical> iterator = d.iterator();
                while(iterator.hasNext()){
                    GamePhysical t = iterator.next();
                    if(t != null){
                        drawTile(g, t);
                    }
                }
            }
        }
        Iterator i = w.objects.iterator();
        GameObject o;
        while(i.hasNext()){
            o = (GameObject) i.next();
            drawTile(g, o);
            g.drawString("Object:"+o.x+ " "+o.y, 300, 200);
        }
    }
    private Image getImage(String s){
        if(images.get(s) != null){
            return images.get(s);
        }
        // Load image:
        // now tell the mediaTracker to stop the applet execution
        // (in this example don't paint) until the images are fully loaded.
        // must be in a try catch block.
        Image img = app.getImage(url,s);

        // tell the MediaTracker to kep an eye on this image, and give it ID 1;
        mt.addImage(img, images.size()+1);
        try {
             mt.waitForAll();
        }
        catch (InterruptedException  e) {} 
        images.put(s, img);
        return img;
    }
    private void drawTile(Graphics2D g, GamePhysical d){
    //draw this objec ton the screen
        //g.setColor(c);
        //g.drawRect(x, y, tileSizeX, tileSizeY);
        //g.drawString("TRY:"+d.tile.imageFileName, 300, 400);
        Image img = getImage(d.tile.imageFileName);
        if(img == null){ return; } // should I throw something here? probably
        //g.drawString("Succeeded", 300, 420);
        int x, y, tx, ty;
        x = (int)(d.x-d.y)/2;
        y = (int)(d.tile.tileZ +((d.x+d.y)/2))/2;
        tx = d.tile.getTileX();
        ty = d.tile.getTileY();
        //x*=16;
        //y*=10;
        x+= viewPortX;
        y+= viewPortY;
        //g.drawString("tile "+viewPortX+" "+viewPortY+" "+tx+" "+ty, 100, y);
        g.drawImage(img,x,y,x+d.tile.tileWidth, y+d.tile.tileHeight, tx, ty, tx+d.tile.tileWidth, ty+d.tile.tileHeight, null);    
    }
}
