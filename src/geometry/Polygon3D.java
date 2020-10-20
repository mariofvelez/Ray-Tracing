package geometry;

import utility.Color;
import utility.Ray;
import utility.Vec3d;

public class Polygon3D extends GeometricObject {
	Vec3d[] points;
	Vec3d normal;
	Plane p;

	public Polygon3D(Vec3d a, Vec3d b, Vec3d c)
	{
		points = new Vec3d[3];
		points[0] = new Vec3d(a);
		points[1] = new Vec3d(b);
		points[2] = new Vec3d(c);
		
		normal = Vec3d.crossProduct(Vec3d.subtract(b, a), Vec3d.subtract(c, a));
		p = new Plane(a, normal, new Color());
		
		color = new Color();
	}
	public void shift(Vec3d shift)
	{
		for(int i = 0; i < points.length; i++)
			points[i].add(shift);
	}
	@Override
	public float hit(Ray ray) {
		float dist = p.hit(ray);
//		if(dist == 0f)
//			return 0.0f;
		
		Vec3d P = Vec3d.mult(ray.direction, dist);
		P.add(ray.origin);
		
		Vec3d edge0 = Vec3d.subtract(points[1], points[0]);
		Vec3d vp0 = Vec3d.subtract(P, edge0);
		Vec3d C = edge0.crossProduct(vp0);
		if(normal.dotProduct(C) < 10E-9)
			return 0.0f;
		
		Vec3d edge1 = Vec3d.subtract(points[2], points[1]);
		Vec3d vp1 = Vec3d.subtract(P, edge1);
		C = edge0.crossProduct(vp1);
		if(normal.dotProduct(C) < 10E-9)
			return 0.0f;
		
		Vec3d edge2 = Vec3d.subtract(points[0], points[2]);
		Vec3d vp2 = Vec3d.subtract(P, edge2);
		C = edge0.crossProduct(vp2);
		if(normal.dotProduct(C) < 10E-9)
			return 0.0f;
		
		return dist;
		
//		float denom = Vec3d.dotProduct(Vec3d.subtract(points[1], points[0]), normal);
//		if(denom == 0)
//			return 0.0f;
//		float scalar = Vec3d.dotProduct(Vec3d.subtract(points[0], ray.origin), normal);
//		//Vec3d intersection = Vec3d.add(ray.origin, Vec3d.mult(ray.direction, scalar));
//		if(scalar > 10E-9)
//			return scalar;
//		return 0.0f;
	}

	@Override
	public Vec3d normal(Vec3d vec, Ray ray) {
		return normal;
	}

}
