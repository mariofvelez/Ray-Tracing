package utility;

public class Color {
	public float r, g, b;
	
	public Color()
	{
		r = 0f;
		g = 0f;
		b = 0f;
	}
	public Color(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}
	public Color(Color color)
	{
		r = color.r;
		g = color.g;
		b = color.b;
	}
	public void add(Color color)
	{
		r += color.r;
		g += color.g;
		b += color.b;
	}
	public void divide(float scale)
	{
		r /= scale;
		g /= scale;
		b /= scale;
	}
	public int toInteger()
	{
		return (int) (r*255)<<16 | (int) (g*255)<<8 | (int) (b*255);
	}
	public static Color toColor(int color)
	{
		int r = color>>16;
		int g = color<<8>>16;
		int b = color<<16>>24;
		float r1 = r/255f;
		float g1 = g/255f;
		float b1 = b/255f;
		return new Color(r1, g1, b1);
	}
	public void set(Color color) {
		r = color.r;
		g = color.g;
		b = color.b;
	}
	public void set(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
}
enum ColorType {;
	Color BLACK = new Color(0f, 0f, 0f);
}
