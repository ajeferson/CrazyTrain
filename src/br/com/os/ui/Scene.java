package br.com.os.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import br.com.os.other.Animator;
import br.com.os.other.BufferedImageLoader;
import br.com.os.other.Constants;
import br.com.os.other.SpriteSheet;
import br.com.os.other.SpriteSheetCooordinate;

/** It's the scenario. */
public class Scene extends JPanel {
	
	private static final long serialVersionUID = 8905347569137169009L;

//	private Passenger passenger = new Passenger(3000, 3000);
	private Animator animator;
	
	public Scene() {
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		this.mockAnimator();
	}
	
	private void mockAnimator() {
		SpriteSheet spriteSheet = new SpriteSheet(1);
		SpriteSheetCooordinate[] coordinates = {
				new SpriteSheetCooordinate(0, 0, 60, 60),
				new SpriteSheetCooordinate(60, 0, 60, 60),
				new SpriteSheetCooordinate(120, 0, 60, 60)
		};
		this.animator = new Animator(spriteSheet.getSpritesWithCoordinates(coordinates), 150);
		this.animator.play();
	}
	
	// JPanel override
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.animator.update(System.currentTimeMillis());
		g.drawImage(this.animator.getCurrentSprite(), 0, 0, 100, 100, null);
		this.repaint();
	}
	
}
