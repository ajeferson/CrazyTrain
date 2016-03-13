package br.com.os.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import br.com.os.enums.Direction;
import br.com.os.model.Passenger;
import br.com.os.other.Constants;

/** It's the scenario. */
public class Scene extends JPanel {
	
	private static final long serialVersionUID = 8905347569137169009L;
	
	private Passenger passenger = new Passenger(3000, 3000);
	
	public Scene() {
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		this.passenger.build();
		this.passenger.play();
		this.passenger.move(new Point(500, 0), Direction.RIGHTWARDS, 5000);
	}
	
	// JPanel override
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.passenger.updateAndDraw(System.currentTimeMillis(), g);
		this.repaint();
	}
	
}
