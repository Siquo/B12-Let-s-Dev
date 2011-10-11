
import java.awt.Graphics2D;

import sound.AudioPlayer;
import sound.AudioPlayer.MusicType;



// Handles actions from input/network
public class GameController {
    private GameWorld world;
    private RenderEngine renderer;
    private GameCharacter currentChar;
    private GameApplet app;
    private AudioPlayer soundManager;
    public UserSettings settings;
    
    public static enum MovementType { WALKING, FLYING } 
    
    public static final int GRIDSIZE = 32;    
    public GameController(GameApplet a) {
        super();
        app = a;
    }
    
    public void draw(Graphics2D g2d){
        g2d.drawString("keylen: "+app.listener.keysDown.size(), 20, 200);
        renderer.drawWorld(g2d, world);
    }
    
    public void update(){
    	
        world.tickAll();
        centerViewPort(currentChar);

    }
    
    public void setGameWorld(GameWorld wld){
        world = wld;
    }
    public void setRenderEngine(RenderEngine r){
        renderer = r;
    }
    public void setSettings(UserSettings s){
        settings = s;
    }
    
    public void startGame(){
    	currentChar = new GameCharacter(10,10,0);
    	soundManager.playMusic(MusicType.MS_NORMAL);
        addObject (currentChar);
    }
    
    public void addObject(GameObject ob){
        // add object to current world
        world.addObject(ob);
    }
    
    public void moveViewPort(int x, int y){
        //move  view to xy
        renderer.viewPortX = x;
        renderer.viewPortY = y;
    }
    public void moveViewRelative(int x, int y){
        renderer.viewPortX = x;//-(GameApplet.CANVAS_WIDTH);
        renderer.viewPortY = y-(GameApplet.CANVAS_HEIGHT/2);
    }
    public void centerViewPort(GamePhysical p){
    	renderer.centerViewPort(p);
    }
    public void moveCurrentChar(Vec3 dir){
        currentChar.move(dir);
    }
    public void moveCurrentCharAbs(Vec3 dir){
        currentChar.moveAbs(dir);
    }
    public void rotateCurrentChar(float r){
        currentChar.rotate(r);
    }
    public Vec3 getCurrentCharacterScreenLoc(){
    	return (renderer.getScreenLoc(currentChar));
    }
    public void characterFire() {
    	currentChar.fire();
    }

	public boolean checkValidMove(MovementType mt, GameObject ob, Vec3 moveDir) {
		Vec3 newPos = new Vec3(ob.x, ob.y, ob.z);
		newPos.add(moveDir);
		Terrain t = world.getTerrain((int)newPos.x,(int)newPos.y,(int)newPos.z);
		if(mt == MovementType.WALKING){ return t.terType.walkable; }
		if(mt == MovementType.FLYING){ return t.terType.passable; }
		
		return false;
	}

	public AudioPlayer getSoundManager() {
		return soundManager;
	}

	public void setSoundManager(AudioPlayer soundManager) {
		this.soundManager = soundManager;
	}
}
