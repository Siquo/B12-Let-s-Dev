
public class GameCharacter extends GameObject {
    private float rotation, fx, fy;
    public float walkingSpeed;
    private Vec3 currentMove;
    private static final float PI2 = (float)Math.PI*2 ;
    
    public GameCharacter() {
        super();
        tile = new GameTile(0,128,"Characters.png",32,32,30);
        rotation = 0;
        walkingSpeed = 10.0f;
        currentMove = new Vec3();
    }
    public GameCharacter(float tx, float ty, float tz){
        super(tx,  ty,  tz);
        tile = new GameTile(0,128,"Characters.png",32,32,30);
        rotation = 0;
        currentMove = new Vec3();
        walkingSpeed = 10.0f;        
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
        currentMove.norm();
        currentMove.mult(walkingSpeed);
        x += currentMove.x;
        y += currentMove.y;
        currentMove.setZero();
    }
}
