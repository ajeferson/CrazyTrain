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
		this.passenger.move(new Point(500, 600), Direction.RIGHTWARDS, 5000);
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
		this.drawGround(g);
		this.passenger.updateAndDraw(System.currentTimeMillis(), g);
		this.repaint();
	}
	
	private void drawBackground(Graphics g) {
		if(this.background == null) {
			this.background = BufferedImageLoader.loadImage("background.png");
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
		}
		int i, size = Constants.GROUND_SIZE;
		for(i = 0; i < 11; i++) {
			g.drawImage(this.ground.get(0), i * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		}
		g.drawImage(this.ground.get(1), i++ * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		
		// Water
		int k;
		for(k = (Constants.WINDOW_WIDTH / size) - i; k > 0; k--) {
			g.drawImage(this.ground.get(3), i++ * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		}
		
		// Behind
		for(i = 2; i < 4; i++) {
			for (k = 0; k < Constants.WINDOW_WIDTH / size; k++) {
				g.drawImage(this.ground.get(2), k * size, Constants.WINDOW_HEIGHT - i * size, size, size, null);
			}
		}
		
		for (k = 0; k < Constants.WINDOW_WIDTH / size; k++) {
			g.drawImage(this.ground.get(0), k * size, Constants.WINDOW_HEIGHT - i * size, size, size, null);
		}
		
		for(i = 5; i < 7; i++) {
			for (k = 0; k < Constants.WINDOW_WIDTH / size; k++) {
				g.drawImage(this.ground.get(2), k * size, Constants.WINDOW_HEIGHT - i * size, size, size, null);
			}
		}
		
		for (k = 0; k < Constants.WINDOW_WIDTH / size; k++) {
			g.drawImage(this.ground.get(0), k * size, Constants.WINDOW_HEIGHT - i * size, size, size, null);
		}
		
		// Ladders to roller coaster
		for(k = 2; k < 8; k++) {
			g.drawImage(this.ground.get(4), Constants.WINDOW_WIDTH - 4 * size, Constants.WINDOW_HEIGHT - k * size, size, size, null);
		}
		
	}
	
}
