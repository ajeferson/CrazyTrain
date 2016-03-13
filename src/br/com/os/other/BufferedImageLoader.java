package br.com.os.other;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	/** Loads a BufferedImage from the images directory. */
	public static BufferedImage loadImage(String imageName) {
		URL url = BufferedImageLoader.class.getResource("/images/" + imageName);
		BufferedImage img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
}
