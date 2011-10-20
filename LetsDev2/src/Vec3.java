

public class Vec3 {
    public float x, y, z;
    public Vec3() {
        super();
        x = 0;
        y = 0;
        z = 0;
    }
    public Vec3(float tx, float ty, float tz) {
        super();
        x = tx;
        y = ty;
        z = tz;
    }
    public float size(){
        return (float)Math.sqrt(x*x+y*y+z*z);
    }
    public Vec3 norm(){
    	float s = size();
    	if(s!=0){
          mult(1/s);
    	}
    	return this;
    }
    public void add(Vec3 f){
        x += f.x;
        y += f.y;
        z += f.z;
    }
    public Vec3 mult(float a){
        x *= a;
        y *= a;
        z *= a;
        return this;
    }
    public void setZero(){
        x = 0;
        y = 0;
        z = 0;
    }
}
