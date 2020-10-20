package utility;

public class Ray {
	public Vec3d origin;
	public Vec3d direction;
	
	public Ray()
	{
		origin = new Vec3d();
		direction = new Vec3d();
	}
	public Ray(Vec3d origin, Vec3d direction)
	{
		this.origin = new Vec3d(origin);
		this.direction = new Vec3d(direction);
	}
	public Ray(Ray ray)
	{
		origin = new Vec3d(ray.origin);
		direction = new Vec3d(ray.direction);
	}
	public void set(Vec3d origin, Vec3d direction)
	{
		this.origin.set(origin);
		this.direction.set(direction);
	}

}
