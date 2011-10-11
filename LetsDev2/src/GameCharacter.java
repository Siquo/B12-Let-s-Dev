
public class GameCharacter extends GameObject {
    private float rotation, fx, fy;
    public float walkingSpeed, bulletSpeed;
    
    private Vec3 currentMove;
    private static final float PI2 = (float)Math.PI*2 ;
    
    public GameCharacter() {
        super();
        tile = new GameTile(0,128,"Characters.png",32,32,0,-16);
        rotation = 0;
        walkingSpeed = 0.1f;
        bulletSpeed = 0.3f;
        currentMove = new Vec3();
    }
    public GameCharacter(float tx, float ty, float tz){
        super(tx,  ty,  tz);
        tile = new GameTile(0,128,"Characters.png",32,32,0,-16);
        rotation = 0;
        currentMove = new Vec3();
        walkingSpeed = 0.1f;        
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
    public void move(Vec3 moveDir){
        moveDir.norm();
        fx += Math.sin(rotation)*moveDir.x*walkingSpeed;
        fy += Math.cos(rotation)*moveDir.y*walkingSpeed;
        x = (int)fx;
        y = (int)fy;
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
    }
    
    public void fire() {
    	GameProjectile bullet = new GameProjectile(x, y, z);
    	bullet.speed = new Vec3((float)Math.sin(rotation)*bulletSpeed, (float)Math.cos(rotation)*bulletSpeed,0).norm();
    }
}
