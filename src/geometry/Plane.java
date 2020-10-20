package geometry;

import utility.Color;
import utility.Ray;
import utility.Vec3d;

public class Plane extends GeometricObject {
	public Vec3d origin;
	public Vec3d normal;
	
	public Plane(Vec3d origin, Vec3d normal, Color color)
	{
		this.origin = new Vec3d(origin);
		this.normal = new Vec3d(normal);
		this.color = new Color(color);
	}

	@Override
	public float hit(Ray ray)
	{
		float t = Vec3d.subtract(origin, ray.origin).dotProduct(normal) / ray.direction.dotProduct(normal);
		if(t > 10E-9)
			return t;
		return 0f;
	}

	@Override
	public Vec3d normal(Vec3d vec, Ray ray) {
		return normal;
	}

}
