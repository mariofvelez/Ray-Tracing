package scene;

import java.util.ArrayList;
import java.util.Random;

import geometry.GeometricObject;
import geometry.Plane;
import geometry.Polygon3D;
import geometry.RectPrism3D;
import geometry.Sphere;
import main.Driver;
import utility.Color;
import utility.Ray;
import utility.Vec3d;

public class World {
	public Camera camera;
	public ArrayList<GeometricObject> objects;
	public Color backgroundColor;
	
	Vec3d focalPoint = new Vec3d(0f, -140f, 40f);
	
	float scale = 0.8f;
	int antiAlias = 1;
	int reflections = 50;
	
	int width;
	int height;
	
	Random random;
	
	Vec3d light = new Vec3d(1200f, 2000f, 2000f);
	
	Sphere sphere;
	
	public World(int width, int height, float size)
	{
		camera = new Camera(focalPoint, new Vec3d(0, 5, 0), width, height, size);
		//camera = new Camera(new Vec3d(0f, -20f, 20f), new Vec3d(0f, 0f, 20f), (float) (Math.PI/3));
		camera.setDimensions(16f, 9f);
		backgroundColor = new Color(0.0f, 0.0f, 0.0f);
		objects = new ArrayList<GeometricObject>();
		
		this.width = width;
		this.height = height;
		
		random = new Random();
		
		
		
		sphere = new Sphere(new Vec3d(0, 0, 50f), 10f, new Color(1f, 0f, 0f));
		objects.add(sphere);
		Polygon3D polygon = new Polygon3D(new Vec3d(-5f, 10f, 0f), new Vec3d(5f, 10f, 0f), new Vec3d(0f, 10f, 10f));
		polygon.color.set(0.5f, 0f, 0.5f);
		//objects.add(polygon);

		Plane plane = new Plane(new Vec3d(0f, 0f, -10f), new Vec3d(0f, 0f, 1f), new Color(0.1f, 0.1f, 0.1f));
		objects.add(plane);
	}
	/*
	 * returns the first color it touches
	 * if it doesn't touch a color, it returns the background color
	 */
	public int rayCast(int x, int y)
	{
		Color color = new Color();
		for(float r = 0; r < antiAlias; r++)
		{
			for(float c = 0; c < antiAlias; c++)
			{
				Ray ray = camera.projectTrig(x + r/antiAlias, y + c/antiAlias);
				Color closestColor = backgroundColor;
				float closestHit = Float.MAX_VALUE;
				for(int i = 0; i < objects.size(); i++)
				{
					float hit = objects.get(i).hit(ray);
					if(hit < closestHit)
					{
						closestHit = hit;
						closestColor = objects.get(i).color;
					}
				}
				color.add(closestColor);
			}
		}
		color.divide(antiAlias*antiAlias);
		return color.toInteger();
	}
	/*
	 * FIXME - trace this pixel (x, y)
	 * trace the ray through the environments until it hits an object with 0 reflection and 0 refraction
	 * for each time the ray intersects an object, propagate backwards to find the proper color
	 * add soft shadows - each time it bounces, find the total light exposure for that point on the intersection
	 * combine the shadow color and the light color for that pixel and return the final color of the pixel
	 * antialiasing - provide a sample for the pixel and return the average color of the pixels
	 */
	public void trace(int x, int y)
	{
		Color color = new Color(); //color for the pixel
		Color closestColor = new Color();
		Ray ray = new Ray(camera.projection);
		Color r_color = new Color();
		
		//temporary
		boolean did_hit = false;
		int reflects = 1;
		
		for(float r = 0; r < antiAlias; r++)
		{
			for(float c = 0; c < antiAlias; c++)
			{
				/*
				 * find closest object
				 * bounce off objects until hit a light source
				 * if the ray didn't end up at a light source return black for this sample
				 * if the ray hit a light source, hold all the rays in an array and propagate backwards to find the color for the sample
				 */
				ArrayList<Ray> rays = new ArrayList<Ray>();
				float photon_distance = 0;
				float closestHit = Float.MAX_VALUE;
				GeometricObject closestObject = null;
				boolean missed_all_objects = false;
				
				camera.projectTrig(x + r/antiAlias, y + c/antiAlias); //projects the first ray
				ray.set(camera.projection.origin, camera.projection.direction);
				
				r_color.set(0f, 0f, 0f);
				
				while(missed_all_objects == false && photon_distance < 10000f) //while it hasn't hit anything
				{
					for(int i = 0; i < objects.size(); i++)
					{
						float hit = objects.get(i).hit(ray);
					}
				}
				for(int i = 0; i < reflections; i++)
				{
					for(int j = 0; j < objects.size(); j++)
					{
						
						float hit = objects.get(j).hit(ray);
						if(hit <= 0) //didn't hit
							continue;
						if(hit < closestHit) //smallest value
						{
							closestColor = objects.get(j).color;
							closestHit = hit;
							closestObject = objects.get(j);
							did_hit = true;
							closestColor = new Color(0f, 0f, 0f);
						}
					}
					
					//setting ray to reflection
					if(did_hit)
					{
						r_color.add(closestColor);
						reflects++;
						closestHit *= 0.99999f;
						ray.origin.set(Vec3d.add(ray.origin, Vec3d.mult(ray.direction, closestHit)));
						ray.direction.set(Vec3d.reflect(ray.direction, closestObject.normal(ray.origin, ray)));
						
						closestHit = Float.MAX_VALUE;
						closestColor = new Color(backgroundColor);
					}
					else
						break;
					did_hit = false;
				}
				r_color.divide(reflects);
				
//				for(int i = 0; i < objects.size(); i++)
//				{
//					
//					float hit = objects.get(i).hit(camera.projection);
//					if(hit != 0 && hit < closestHit)
//					{
//						closestColor = objects.get(i).color;
//						closestHit = hit;
//						
//						Vec3d hitPoint = Vec3d.normalize(camera.projection.direction);
//						hit *= 0.999f;
//						hitPoint.mult(hit);
//						hitPoint.add(camera.projection.origin);
//						Ray toLight = new Ray(hitPoint, Vec3d.subtract(light, hitPoint));
//						toLight.direction.normalize();
//						
//						for(int j = 0; j < objects.size(); j++)
//						{
//							float hitLight = objects.get(j).hit(toLight);
//							if(hitLight > 0)
//								closestColor = backgroundColor;
//						}
//					}
//				}
				
				color.add(r_color);
				
//				projection.origin.set((x - width/2 + (r+random.nextFloat()) / antiAlias) * scale,
//							   (y - height/2 + (c+random.nextFloat()) / antiAlias) * scale, 100);
			}
		}
		color.divide(antiAlias*antiAlias);
		Driver.image.buffer.setRGB(x, y, color.toInteger());
	}

}
