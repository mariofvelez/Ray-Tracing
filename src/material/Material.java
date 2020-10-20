package material;

import geometry.GeometricObject;
import utility.Color;
import utility.Ray;
import utility.Vec3d;

/**
 * determines how rays interact with the object
 * @author User
 *
 */
public abstract class Material {
	
	/**
	 * what the amount of light from each RGB value that is reflected
	 */
	Color p;
	/**
	 * the index of refraction, the material will refract if n != 0
	 */
	public float n; //index of refraction
	public Color color;
	/**
	 * reflects a ray based off this material
	 * @param ray
	 * @param pos
	 * @return
	 */
	public Vec3d bounce(Ray ray, Vec3d normal)
	{
		//reflect
		if(n == 0)
		{
			return Vec3d.reflect(ray.direction, normal);
		}
		//refract
		else
		{
			return null;
		}
	}
}
enum MaterialType {;
	
	public static final Material GLASS = null;
	public static final Material MIRROR = null;
	public static final Material DIAMOND = null;
	public static final Material FLAT_WHITE = null;
}
