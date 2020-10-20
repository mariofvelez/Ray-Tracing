package main;

import java.util.Random;

import geometry.Plane;
import geometry.Sphere;
import utility.Color;
import utility.Ray;
import utility.Vec3d;

public class Tracer {
	float scale = 0.8f;
	int antiAlias = 8;
	int width = Driver.world.camera.width;
	int height = Driver.world.camera.height;
	
	Random random = new Random();
	
	Sphere sphere = new Sphere(new Vec3d(0f, 0f, 0f), 60, new Color(1f, 0f, 0f));
	Sphere sphere1 = new Sphere(new Vec3d(60f, 0f, 0f), 60, new Color(0f, 1f, 0f));
	Plane plane = new Plane(new Vec3d(0f, 0f, 0f), new Vec3d(0f, 1f, 0.2f), new Color(0f, 0f, 1f));
	Vec3d direction = new Vec3d(0f, 0f, -1f);
	
	public void trace(int x, int y)
	{	
		Color color = new Color(0f, 0f, 0f);
		for(int r = 0; r < antiAlias; r++)
		{
			for(int c = 0; c < antiAlias; c++)
			{
				
				Ray ray = new Ray(new Vec3d((x-width/2+(0+random.nextFloat()) / antiAlias) * scale,
											(y-height/2+(0+random.nextFloat()) / antiAlias) * scale, 100), direction);
				float hit = sphere.hit(ray);
				if(hit != 0f)
					color.add(new Color(1 - hit/100, 0f, 0f));
				hit = sphere1.hit(ray);
				if(hit != 0f)
					color.add(new Color(0f, 1 - hit/100, 0f));
				hit = plane.hit(ray);
				if(hit != 0f)
					color.add(new Color(0f, 0f, hit/1000));
				
			}
		}
		color.divide(antiAlias*antiAlias);
		Driver.image.buffer.setRGB(x, y, color.toInteger());
	}
}
