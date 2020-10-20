package geometry;

import material.Material;
import utility.Color;
import utility.Ray;
import utility.Vec3d;

public abstract class GeometricObject {
	public Material material;
	public Color color;
	
	public abstract float hit(Ray ray);
	public abstract Vec3d normal(Vec3d vec, Ray ray);

}
