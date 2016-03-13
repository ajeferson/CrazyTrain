package br.com.os.other;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/** Encapsulates a set of sprites an animates them. */
public class Animator {

	private ArrayList<BufferedImage> sprites;

	private boolean playing = false; // Should be volatile?
	private boolean moving = false;
	private long previousTime;

	private long interval;
	private long movingDuration;
	private double movingPreviousTime;
	private double movingElapsedTime;

	private int currentSpriteIndex;

	private int x;
	private int y;
	private int width;
	private int height;
	private int targetX;
	private int targetY;
	private int initialX;
	private int initialY;
	private int deltaX;
	private int deltaY;

	/** Builds an animator.
	 * @param sprites The ArrayList of BufferedImages to animate.
	 * @param interval The time interval between each sprite.
	 * @param x The initial x position.
	 * @param y The initial y position.
	 * @param width The width of the animator.
	 * @param height The height of the animator. */
	public Animator(ArrayList<BufferedImage> sprites, long interval, int x, int y, int width, int height) {
		this.sprites = sprites;
		this.interval = interval;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/** Updates the animation. It changes the current sprite if the interval has passed. */
	private void update(long time) {
		if(this.playing) {
			if(time - this.previousTime >= this.interval) {
				this.currentSpriteIndex = (this.currentSpriteIndex + 1 < this.sprites.size()) ? (this.currentSpriteIndex + 1) : 0;
				this.previousTime = time;
			}
		}
		if(this.moving) {
			this.movingElapsedTime = time - this.movingPreviousTime;
			if(this.movingElapsedTime < this.movingDuration) {
				double fraction = this.movingElapsedTime / this.movingDuration;
				this.x = this.initialX + (int) (this.deltaX * fraction);
				this.y = this.initialY + (int) (this.deltaY * fraction);
			} else {
				this.x = this.targetX;
				this.y = this.targetY;
				this.moving = false;
			}
		}
	}

	/** Draws the the current sprite.
	 * @param g The Graphics in which to draw the current sprite. */
	private void draw(Graphics g) {
		g.drawImage(this.sprites.get(this.currentSpriteIndex), this.x, this.y, this.width, this.height, null);
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
		this.currentSpriteIndex = 0;
	}


	// Getters and setters

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void move(Point target, long time) {
		this.movingDuration = time;
		this.targetX = (int) target.getX();
		this.targetY = (int) target.getY();
		this.initialX = this.x;
		this.initialY = this.y;
		this.deltaX = this.targetX - this.initialX;
		this.deltaY = this.targetY - this.initialY;
		this.movingPreviousTime = System.currentTimeMillis();
		this.moving = true;

	}

}
