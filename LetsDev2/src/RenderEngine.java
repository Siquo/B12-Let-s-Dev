

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
        int x, y;
        float fx = p.x;
        float fy = p.y;
        x = (int)(GameController.GRIDSIZE*(fx-fy)/2);
        y = (int)(GameController.GRIDSIZE*(((fx+fy)/2))/2);
        if(Math.abs(viewPortX - x) > viewWidth /4){
        	//viewPortX = viewWidth/2 - x;
        }
        if(Math.abs(viewPortY - y) > viewHeight /4){
        	//viewPortY = viewHeight/2 - y;
        }
        
    }
    
    public void drawWorld(Graphics2D g, GameWorld w){
        LinkedList<GamePhysical> d;
        Iterator iter = w.objects.iterator();
        GameObject o;
        for(int i=viewTileMinX;i<viewTileMaxX;i++){
            for(int j=viewTileMinY;j<viewTileMaxY;j++){
                d = w.getDrawable(i,j,z);
                Iterator<GamePhysical> iterator = d.iterator();
                while(iterator.hasNext()){
                    GamePhysical t = iterator.next();
                    if(t != null){
                        drawTile(g, t);
                        iter =  ((Terrain)t).objects.iterator();
                        while(iter.hasNext()){
                            o = (GameObject) iter.next();
                            drawTile(g, o);
                            g.drawString("Object:"+o.x+ " "+o.y, 300, 200);
                        }
                    }
                }
            }
        }
      /*  while(iter.hasNext()){
            o = (GameObject) iter.next();
            drawTile(g, o);
            g.drawString("Object:"+o.x+ " "+o.y, 300, 200);
        }*/
        Vec3 f = getRealLoc(200,100);
        Vec3 t = getScreenLoc(f);
        g.setColor(Color.CYAN);
        g.drawString(" test x"+f.x+" y"+f.y, 20, 230);
        g.drawString(" test x"+t.x+" y"+t.y, 20, 250);
        g.drawRect((int)t.x, (int)t.y, 4, 4);
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
    //draw this object on the screen
        Image img = getImage(d.tile.imageFileName);
        if(img == null){ return; } // should I throw something here? probably
        //g.drawString("Succeeded", 300, 420);
        Vec3 loc = getScreenLoc(d);
        int tx = d.tile.getTileX();
        int ty = d.tile.getTileY();
        g.drawImage(img,(int)loc.x,(int)loc.y,(int)loc.x+d.tile.tileWidth, (int)loc.y+d.tile.tileHeight, tx, ty, tx+d.tile.tileWidth, ty+d.tile.tileHeight, null);
        

        /*int x, y, tx, ty;
        float fx = d.x;
        float fy = d.y;        
        x = (int)(d.tile.tileX +(32*(fx-fy)/2));
        y = (int)(d.tile.tileY +(32*((fx+fy)/2))/2);
        tx = d.tile.getTileX();
        ty = d.tile.getTileY();
        //x*=30;
        //y*=20;
        x+= viewPortX;
        y+= viewPortY;
        g.drawImage(img,x,y,x+d.tile.tileWidth, y+d.tile.tileHeight, tx, ty, tx+d.tile.tileWidth, ty+d.tile.tileHeight, null);*/
        
       
    }
    
    public Vec3 getScreenLoc(Vec3 f){
    	return getScreenLoc(f.x, f.y, f.z, 0, 0);
    }
    public Vec3 getScreenLoc(GamePhysical d){
    	return getScreenLoc(d.x, d.y, 0, d.tile.tileX, d.tile.tileY);
    }
    public Vec3 getScreenLoc(float x, float y, float z, float tx, float ty){
    	return (new Vec3((int)(tx+viewPortX +(32*(x-y)/2)), (int)(ty+viewPortY +(32*((x+y)/2))/2), 0));
    }
    public Vec3 getRealLoc(float u, float v) {
    	
    	float x = -(2*viewPortY+viewPortX-2*v-u)/32;
    	float y = -(2*viewPortY-viewPortX-2*v+u)/32;
    	return (new Vec3(x,y,0));
    }

}
