package br.com.os.other;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	/** Loads a BufferedImage from the images directory. */
	public static BufferedImage loadImage(String imageName) throws IOException {
		URL url = BufferedImageLoader.class.getResource("/images/" + imageName);
		BufferedImage img = ImageIO.read(url);
		return img;
	}
	
}
