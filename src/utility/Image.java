package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Driver;

public class Image {
	public BufferedImage buffer;
	public File image;
	
	public Image(String fileName)
	{
		image = new File(fileName);
		buffer = new BufferedImage(Driver.world.camera.width, Driver.world.camera.height, BufferedImage.TYPE_INT_RGB);
		
	}
	public void write(String fileType)
	{
		try {
			ImageIO.write(buffer, fileType, image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}

}
