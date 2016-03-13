package br.com.os.other;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/** Encapsulates a set of sprites an animates them. */
public class Animator {

	private ArrayList<BufferedImage> sprites;

	private volatile boolean playing = false;
	private long previousTime;
	private long interval;
	private int currentSprite;

	/** Builds an animator.
	 * @param sprites The ArrayList of BufferedImages to animate.
	 * @param interval The time interval between each sprite. */
	public Animator(ArrayList<BufferedImage> sprites, long interval) {
		this.sprites = sprites;
		this.interval = interval;
	}

	/** Updates the animation. It changes the current sprite if the interval has passed. */
	private void update(long time) {
		if(this.playing) {
			if(time - this.previousTime >= this.interval) {
				this.currentSprite = (this.currentSprite + 1 < this.sprites.size()) ? (this.currentSprite + 1) : 0;
				this.previousTime = time;
			}
		}
	}
	
	/** Draws the the current sprite.
	 * @param g The Graphics in which to draw the current sprite. */
	private void draw(Graphics g) {
		g.drawImage(this.getCurrentSprite(), 50, 50, 40, 40, null);
	}
	
	/** Updates and draw the current sprite.
	 * @param time The current time, for testing if the current sprite has to change.
	 * @param g The Graphics object in which to draw the current sprite. */
	public void updateAndDraw(long time, Graphics g) {
		this.update(time);
		this.draw(g);
	}

	/** Starts animating the sprites. */
	public void play() {
		this.playing = true;
		this.previousTime = 0;
		this.currentSprite = 0;
	}
	
	/** 
	 * @return The BufferedImage that represents the current sprite. */
	public BufferedImage getCurrentSprite() {
		return this.sprites.get(this.currentSprite);
	}

}
