package scene;

import utility.Ray;
import utility.Vec3d;

public class Camera {
	public float size;
	
	public int width = 640;
	public int height = 480;
	public float c_width = 16;
	public float c_height = 9;
	
	Vec3d focalPoint;
	Vec3d lookat;
	float angle;
	Vec3d u, v, w;
	Vec3d focalDistance;
	float distance;
	public Ray projection;
	
	public Camera(Vec3d focalPoint, Vec3d lookat, float FOV)
	{
		this.focalPoint = new Vec3d(focalPoint);
		this.lookat = new Vec3d(lookat);
		distance = (float) (height/2/Math.tan(FOV));
		
		focalDistance = new Vec3d();
		projection = new Ray(focalPoint, focalDistance);
		
		compute_uvw();
	}
	public Camera(Vec3d focalPoint, Vec3d focalDistance, int width, int height, float size) {
		this.width = width;
		this.height = height;
		this.size = size;
		
		this.focalPoint = focalPoint;
		this.focalDistance = focalDistance;
		projection = new Ray(focalPoint, focalDistance);
		
		computeAngle();
	}
	public void computeAngle()
	{
		angle = (float) Math.atan2(-focalDistance.x, focalDistance.y);
	}
	public void compute_uvw()
	{
		w = Vec3d.subtract(focalPoint, lookat);
		w.normalize();
		Vec3d up = new Vec3d(0.00424f, 0.00764f, 1.0f);
		u = up.crossProduct(w);
		u.normalize();
		v = w.crossProduct(u);
		v.normalize();
	}
	public void setDimensions(float c_width, float c_height)
	{
		this.c_width = c_width;
		this.c_height = c_height;
	}
	public Ray project1(float x, float y)
	{
		Vec3d temp = Vec3d.mult(v, lerp(-c_height/2, c_height/2, y/height));
		temp.subtract(Vec3d.mult(w, distance));
		Vec3d temp1 = Vec3d.mult(u, lerp(-c_height/2, c_height/2, 1 - y/height));
		temp1.add(temp);
		projection.direction.set(temp1);
		projection.direction.normalize();
		return projection;
	}
	float f_dist = 5;//focalDistance.length();
	
	public Ray project(float x, float y) //screen coordinates
	{
		projection.direction.set(focalDistance);
		
		projection.direction.add(lerp(-c_width/2, c_width/2, x/width), 0f,
								 lerp(-c_height/2, c_height/2, 1 - y/height));
		projection.direction.normalize();
		return projection;
	}
	//float f_dist = 5;//focalDistance.length();
	float x_t_max = (float) Math.atan2(c_width/2, f_dist);
	float x_t_min = (float) Math.atan2(-c_width/2, f_dist);
	float y_t_max = (float) Math.atan2(c_height/2, f_dist);
	float y_t_min = (float) Math.atan2(-c_height/2, f_dist);
	public Ray projectTrig(float x, float y) //screen coordinates
	{
		projection.direction.set(focalDistance);
		
		projection.direction.add((float) Math.sin(lerp(x_t_min, x_t_max, x/width))*f_dist, 0f,
				(float) Math.sin(lerp(y_t_min, y_t_max, 1 - y/height))*f_dist);
//		projection.direction.add(lerp(-c_width/2, c_width/2, x/width), 0f,
//								 lerp(-c_height/2, c_height/2, 1 - y/height));
		projection.direction.normalize();
		return projection;
	}
	public Ray projectOrthogonal(float x, float y)
	{
		projection.origin.set(focalDistance);
		projection.origin.add(lerp(-c_width/2, c_width/2, x/width), 0,
							  lerp(-c_height/2, c_height/2, 1 - y/height));
		//projection.direction.set(focalDistance);
		return projection;
	}
	public static float lerp(float a, float b, float scale)
	{
		return a + (b-a)*scale;
	}
	Vec3d up = new Vec3d(0.00424f, 0.00764f, 1.0f);
	public void rotate(float t)
	{
		angle += t;
		focalDistance.set((float) Math.sin(angle)*f_dist, (float) Math.cos(angle)*f_dist, focalDistance.z);
	}
	public void move(float forward, float side, float up)
	{
		Vec3d dir = Vec3d.normalize(focalDistance);
		focalPoint.add(Vec3d.mult(dir, forward));
		Vec3d up_dir = new Vec3d(0.00424f, 0.00764f, 1.0f);
		Vec3d cross = Vec3d.crossProduct(focalDistance, up_dir);
		focalPoint.add(Vec3d.mult(cross, side));
		focalPoint.add(0f, 0f, up);
	}

}
