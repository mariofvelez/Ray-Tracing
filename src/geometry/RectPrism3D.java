package geometry;

import java.util.Random;

import utility.Color;
import utility.Ray;
import utility.Vec3d;

public class RectPrism3D extends GeometricObject {
	public Polygon3D[] faces;
	
	public RectPrism3D(Vec3d pos, Vec3d dimensions)
	{
		faces = new Polygon3D[12];
		
		Vec3d[] corners = new Vec3d[8];
		corners[0] = Vec3d.add(pos, new Vec3d(dimensions.x/2, -dimensions.y/2, dimensions.z/2));
		corners[1] = Vec3d.add(pos, new Vec3d(dimensions.x/2, dimensions.y/2, dimensions.z/2));
		corners[2] = Vec3d.add(pos, new Vec3d(-dimensions.x/2, dimensions.y/2, dimensions.z/2));
		corners[3] = Vec3d.add(pos, new Vec3d(-dimensions.x/2, -dimensions.y/2, dimensions.z/2));
		
		corners[4] = Vec3d.add(pos, new Vec3d(dimensions.x/2, -dimensions.y/2, -dimensions.z/2));
		corners[5] = Vec3d.add(pos, new Vec3d(dimensions.x/2, dimensions.y/2, -dimensions.z/2));
		corners[6] = Vec3d.add(pos, new Vec3d(-dimensions.x/2, dimensions.y/2, -dimensions.z/2));
		corners[7] = Vec3d.add(pos, new Vec3d(-dimensions.x/2, -dimensions.y/2, -dimensions.z/2));
		
		//top
		faces[0] = new Polygon3D(corners[0], corners[1], corners[2]);
		faces[1] = new Polygon3D(corners[2], corners[3], corners[0]);
		//bottom
		faces[2] = new Polygon3D(corners[4], corners[5], corners[6]);
		faces[3] = new Polygon3D(corners[6], corners[7], corners[4]);
		//right side
		faces[4] = new Polygon3D(corners[4], corners[5], corners[1]);
		faces[5] = new Polygon3D(corners[1], corners[0], corners[4]);
		//left side
		faces[6] = new Polygon3D(corners[7], corners[6], corners[3]);
		faces[7] = new Polygon3D(corners[3], corners[4], corners[7]);
		//front side
		faces[8] = new Polygon3D(corners[7], corners[4], corners[0]);
		faces[9] = new Polygon3D(corners[0], corners[3], corners[7]);
		//back side
		faces[10] = new Polygon3D(corners[2], corners[1], corners[5]);
		faces[11] = new Polygon3D(corners[5], corners[6], corners[2]);
		
		Random random = new Random();
		for(int i = 0; i < faces.length; i++)
		{
			float r = random.nextFloat()*0.5f + 0.5f;
			float g = random.nextFloat()*0.5f + 0.5f;
			float b = random.nextFloat()*0.5f + 0.5f;
			faces[i].color = new Color(r, g, b);
		}
	}

	@Override
	public float hit(Ray ray) {
		float minHit = Float.MAX_VALUE;
		boolean did_hit = false;
		for(int i = 0; i < faces.length; i++)
		{
			float hit = faces[i].hit(ray);
			if(hit != 0 && hit < minHit)
			{
				minHit = hit;
				did_hit = true;
			}
		}
		if(did_hit)
			return minHit;
		return 0.0f;
	}

	@Override
	public Vec3d normal(Vec3d vec, Ray ray) {
		// TODO Auto-generated method stub
		return null;
	}
}
