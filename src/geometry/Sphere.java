package geometry;

import utility.Color;
import utility.Ray;
import utility.Vec3d;

public class Sphere extends GeometricObject {
	public Vec3d center;
	public float radius;
	
	public Sphere(Vec3d center, float radius, Color color)
	{
		this.center = new Vec3d(center);
		this.radius = radius;
		this.color = new Color(color);
	}

	@Override
	public float hit(Ray ray)
	{
		float a = ray.direction.dotProduct(ray.direction);
		float b = 2*Vec3d.subtract(ray.origin, center).dotProduct(ray.direction);
		float c = Vec3d.subtract(ray.origin, center).dotProduct(Vec3d.subtract(ray.origin, center)) - radius*radius;
		float discriminant = b*b - 4*a*c;
		if(discriminant < 0f)
			return 0f;
		float t = (float) (-b - Math.sqrt(discriminant)) / (2*a);
		if(t > 10E-9)
			return t;
		return 0f;
	}

	@Override
	public Vec3d normal(Vec3d vec, Ray ray) {
		return Vec3d.normalize(Vec3d.subtract(vec, center));
	}

}
