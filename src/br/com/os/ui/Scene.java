package br.com.os.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import br.com.os.enums.Direction;
import br.com.os.model.Passenger;
import br.com.os.other.BufferedImageLoader;
import br.com.os.other.Constants;

/** It's the scenario. */
public class Scene extends JPanel {
	
	private static final long serialVersionUID = 8905347569137169009L;
	
	private Passenger passenger = new Passenger(3000, 3000);
	
	private BufferedImage background;
	private ArrayList<BufferedImage> ground;
	
	public Scene() {
		this.passenger.build();
		this.passenger.play();
		this.passenger.move(new Point(Constants.WINDOW_WIDTH - 5 * Constants.TILE_SIZE, 600), Direction.RIGHTWARDS, 5000);
	}
	
	// JPanel override
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.drawBackground(g);
//		this.drawGround(g);
		this.passenger.updateAndDraw(System.currentTimeMillis(), g);
		this.repaint();
	}
	
	private void drawBackground(Graphics g) {
		if(this.background == null) {
			this.background = BufferedImageLoader.loadImage("awesome-background.png");
		}
		g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);
	}
	
	private void drawGround(Graphics g) {
		if(this.ground == null) {
			this.ground = new ArrayList<BufferedImage>();
			this.ground.add(BufferedImageLoader.loadImage("tiles/ground2.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/ground3.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/ground5.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/water.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/ladder.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/box.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/crystal.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/tree.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/signRight.png"));
			
		}
		int i, size = Constants.TILE_SIZE;
		for(i = 0; i < (Constants.WINDOW_WIDTH / size) - 4; i++) {
			g.drawImage(this.ground.get(0), i * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		}
		g.drawImage(this.ground.get(2), i * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		g.drawImage(this.ground.get(3), i * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		g.drawImage(this.ground.get(1), i++ * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		g.drawImage(this.ground.get(2), i++ * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		g.drawImage(this.ground.get(2), i++ * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		g.drawImage(this.ground.get(2), i++ * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		i-=3;
		
		// Water
		int k;
		for(k = (Constants.WINDOW_WIDTH / size) - i; k > 0; k--) {
			g.drawImage(this.ground.get(3), i++ * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		}
		
		// Behind
		for(i = 2; i < 5; i++) {
			for (k = 0; k < Constants.WINDOW_WIDTH / size; k++) {
				g.drawImage(this.ground.get(2), k * size, Constants.WINDOW_HEIGHT - i * size, size, size, null);
			}
		}
		
		for (k = 0; k < Constants.WINDOW_WIDTH / size; k++) {
			g.drawImage(this.ground.get(0), k * size, Constants.WINDOW_HEIGHT - i * size, size, size, null);
		}
		
		for(i = 6; i < 8; i++) {
			for (k = 0; k < Constants.WINDOW_WIDTH / size; k++) {
				g.drawImage(this.ground.get(2), k * size, Constants.WINDOW_HEIGHT - i * size, size, size, null);
			}
		}
		
		for (k = 0; k < Constants.WINDOW_WIDTH / size; k++) {
			g.drawImage(this.ground.get(0), k * size, Constants.WINDOW_HEIGHT - i * size, size, size, null);
		}
		
		// Ladders
		for(k = 2; k < 6; k++) {
			g.drawImage(this.ground.get(4), Constants.WINDOW_WIDTH - 5 * size, Constants.WINDOW_HEIGHT - k * size, size, size, null);
			g.drawImage(this.ground.get(4), 2 * size, Constants.WINDOW_HEIGHT - k * size, size, size, null);
		}
		
		// Boxes
		g.drawImage(this.ground.get(5), 4 * size, Constants.WINDOW_HEIGHT - 2 * size, size, size, null);
		g.drawImage(this.ground.get(5), 11 * size, Constants.WINDOW_HEIGHT - 2 * size, size, size, null);
		
		// Trees
		g.drawImage(this.ground.get(7), 6 * size, Constants.WINDOW_HEIGHT - 2 * size - 73, 100, 123, null);
		g.drawImage(this.ground.get(7), 8 * size, Constants.WINDOW_HEIGHT - 2 * size - 73, 100, 123, null);
		
		// Signs
		g.drawImage(this.ground.get(8), 13 * size, Constants.WINDOW_HEIGHT - 2 * size, size, size, null);
		
	}
	
}
