package utility;

/**
 * 2 dimensional vector
 * @author Mario Velez
 *
 */
public class Vec2d {
	public float x;
	public float y;
	
	public Vec2d(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	/**
	 * creates a copy of a Vec2d
	 * @param copy - the Vec2d to copy
	 */
	public Vec2d(Vec2d copy)
	{
		this(copy.x, copy.y);
	}
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	public void print()
	{
		System.out.println(toString());
	}
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	public void set(Vec2d vec)
	{
		this.x = vec.x;
		this.y = vec.y;
	}
	public void negate()
	{
		x = -x;
		y = -y;
	}
	public float length()
	{
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public void add(float x, float y)
	{
		this.x += x;
		this.y += y;
	}
	public void add(Vec2d vec)
	{
		this.x += vec.x;
		this.y += vec.y;
	}
	public static Vec2d add(Vec2d a, Vec2d b)
	{
		return new Vec2d(a.x + b.x, a.y + b.y);
	}
	
	public void subtract(float x, float y)
	{
		this.x -= x;
		this.y -= y;
	}
	public void subtract(Vec2d vec)
	{
		this.x -= vec.x;
		this.y -= vec.y;
	}
	public static Vec2d subtract(Vec2d a, Vec2d b)
	{
		return new Vec2d(a.x - b.x, a.y - b.y);
	}
	
	public void mult(float x, float y)
	{
		this.x *= x;
		this.y *= y;
	}
	public void mult(Vec2d vec)
	{
		this.x *= vec.x;
		this.y *= vec.y;
	}
	public static Vec2d mult(Vec2d a, Vec2d b)
	{
		return new Vec2d(a.x * b.x, a.y * b.y);
	}
	
	public void mult(float scale)
	{
		this.x *= scale;
		this.y *= scale;
	}
	public static Vec2d mult(Vec2d a, float scale)
	{
		return new Vec2d(a.x * scale, a.y * scale);
	}
	
	public float dist(Vec2d vec)
	{
		return (float) (Math.sqrt((x-vec.x)*(x-vec.x) + (y-vec.y)*(y-vec.y)));
	}
	public static float dist(Vec2d a, Vec2d b)
	{
		return (float) (Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y)));
	}
	
	public float dotProduct(Vec2d vec)
	{
		return x*vec.x + y*vec.y;
	}
	public static float dotProduct(Vec2d a, Vec2d b)
	{
		return a.x*b.x + a.y*b.y;
	}
	
	public float crossProduct(Vec2d vec)
	{
		return x*vec.y - y*vec.x;
	}
	public static float crossProduct(Vec2d a, Vec2d b)
	{
		return a.x*b.y - a.y*b.x;
	}
	
	public void normalize()
	{
		float length = length();
		x /= length;
		y /= length;
	}
	public static Vec2d normalize(Vec2d vec)
	{
		float length = vec.length();
		return new Vec2d(vec.x / length, vec.y / length);
	}
	
	/**
	 * reflects a vector off a surface with a normal
	 * @param a - the Vec2d to be reflected
	 * @param normal - the normal of the surface
	 * @returns the reflected vector normalized
	 */
	public static Vec2d reflect(Vec2d ri, Vec2d normal)
	{
		Vec2d normalized = normalize(normal);
		float product = dotProduct(ri, normalized);
		Vec2d n = mult(normalized, product);
		n.mult(2);
		Vec2d rf = subtract(ri, n);
		//rf.normalize();
		return rf;
	}
	/**
	 * the left hand normal of this Vec2d
	 * @return - the normal vector
	 */
	public Vec2d leftNormal()
	{
		return new Vec2d(y, -x);
	}
	/**
	 * the right hand normal of this Vec2d
	 * @return - the normal vector
	 */
	public Vec2d rightNormal()
	{
		return new Vec2d(-y, x);
	}
	/**
	 * rotates left by 90 degrees
	 */
	public void rotateLeft()
	{
		float temp = x;
		this.x = y;
		this.y = -temp;
	}
	/**
	 * rotates right by 90 degrees
	 */
	public void rotateRight()
	{
		float temp = x;
		this.x = -y;
		this.y = temp;
	}

}
/**
 * preset vectors
 * @author Mario Velez
 *
 */
enum Vec2dConstant {;
	public static final Vec2d ZERO = new Vec2d(0, 0);
	public static final Vec2d NORTH = new Vec2d(1, 0);
	public static final Vec2d SOUTH = new Vec2d(-1, 0);
	public static final Vec2d EAST = new Vec2d(0, 1);
	public static final Vec2d WEST = new Vec2d(0, -1);
}
