
public class GameCharacter extends GameObject {
    private float rotation, fx, fy;
    public float walkingSpeed, bulletSpeed;
    
    public float gunCoolDown, currentGunCoolDown, gunCooling;
    
    private Vec3 currentMove;
    private static final float PI2 = (float)Math.PI*2 ;
    
    public GameCharacter() {
        super();
        init();
    }
    public GameCharacter(float tx, float ty, float tz){
        super(tx,  ty,  tz);
    	init();
    }
    private void init() {
        tile = new GameTile(0,128,"Graphics.png",32,32,0,-16);
        rotation = 0;
        walkingSpeed = 0.1f;
        bulletSpeed = 0.12f;
        currentMove = new Vec3();
    	gunCoolDown = 50;
    	gunCooling = 1;
    	currentGunCoolDown = 0;
    }
    public void rotate(float val){
        rotation += val;
        if(rotation > PI2){
            rotation -= PI2;
        }
        if (rotation < 0){
            rotation += PI2;
        }
    }
    public void rotateTo(float val){
    	rotation = val;
    }
    public float getRotation() {
    	return rotation;
    }
    public void move(Vec3 moveDir){
    	Vec3 nwDir = new Vec3(0,0,0);
        nwDir.x += (Math.sin(rotation)*moveDir.x + Math.cos(rotation)*moveDir.y);
        nwDir.y += (Math.cos(rotation)*moveDir.x + Math.sin(rotation)*moveDir.y);
        currentMove.add(nwDir);
    }
    public void moveAbs(Vec3 moveDir){
  		currentMove.add(moveDir);
    }
    
    public void tick(){
    	tile.tileX = 2;
    	tile.tileY = -14;
        currentMove.norm();
        currentMove.mult(walkingSpeed);
    	if(GameApplet.controller.checkValidMove(GameController.MovementType.WALKING, this, currentMove)){
	        x += currentMove.x;
	        y += currentMove.y;
	        hasMoved();
    	}
        currentMove.setZero();
        if(currentGunCoolDown > 0) {
        	currentGunCoolDown -= gunCooling;
        }
    }
    
    public void fire() {
    	if(currentGunCoolDown <= 0) {
    		currentGunCoolDown = gunCoolDown;
	    	GameProjectile bullet = new GameProjectile(x, y, z);
	    	bullet.speed = new Vec3((float)Math.cos(rotation), (float)Math.sin(rotation),0).mult(bulletSpeed);
	    	getWorld().addObject(bullet);
    	}
    }
}
