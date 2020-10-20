package main;

import scene.World;
import utility.Image;


public class Driver {

	public static World world;
	public static Image image;
	
	public static void main(String[] args) {
		
		
		world = new World(640, 480, 1f);
		image = new Image("Image.png");
		
		int width = world.camera.width;
		int height = world.camera.height;
		
		long start = System.currentTimeMillis();
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				int color = world.rayCast(x, y);
				image.buffer.setRGB(x, y, color);
			}
			//if(y%5 == 0)
			//System.out.println("row: " + y + " out of " + height + " complete");
		}
		long end = System.currentTimeMillis();
		image.write("PNG");
		System.out.println("render time: " + (end-start) + "ms");
		float temp = 1.0f;
		for(int i = 0; i < 50; i++)
			temp *= 0.999f;
		System.out.println(temp);
	}

}
