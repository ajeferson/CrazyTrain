package br.com.os.other;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import images.BufferedImageLoader;

public class Main extends JFrame {
	
	BufferedImage sprite;
	Animator animator;

	public Main() {
		this.setSize(400, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.init();
	}
	
	private void init() {
		try {
			BufferedImageLoader loader = new BufferedImageLoader();
			BufferedImage spriteSheet = loader.loadImage("sprite.png");
			SpriteSheet ss = new SpriteSheet(spriteSheet);
			this.sprite = ss.grabSprite(0, 0, 60, 60);
			
			ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
			sprites.add(ss.grabSprite(0, 0, 60, 60));
			sprites.add(ss.grabSprite(60, 0, 60, 60));
			sprites.add(ss.grabSprite(120, 0, 60, 60));
			sprites.add(ss.grabSprite(180, 0, 60, 60));
			
			this.animator = new Animator(sprites);
			this.animator.setSpeed(20);
			this.animator.play();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	Image dbImage;
	Graphics dbg;
	
	@Override
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponents(dbg);
		g.drawImage(dbImage, 0, 0, null);
	}
	
	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
		if(this.animator != null) {
			this.animator.update(System.currentTimeMillis());
			g.drawImage(this.animator.sprite, 100, 100, 50, 50, null);
		}
		repaint();
	}

	
	public static void main(String[] args) {
		new Main();
	}
	
}
