package br.com.os.other;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import br.com.os.enums.Direction;
import br.com.os.model.amazing.AmazingSemaphore;

/** Encapsulates a set of sprites an animates them. */
public class Animator extends Thread {

	protected ArrayList<BufferedImage> spritesRightwards;
	protected ArrayList<BufferedImage> spritesLeftwards;
	protected ArrayList<BufferedImage> spritesUpwards;
	protected ArrayList<BufferedImage> spritesDownwards;
	protected BufferedImage lastSprite;
	private AmazingSemaphore mutex = new AmazingSemaphore(1);

	private boolean playing = false; // Should be volatile?
	private boolean moving = false;
	public boolean isMoving() {
		this.mutex.down();
		boolean m = this.moving;
		this.mutex.up();
		return m;
	}

	private long previousTime;

	protected long interval;
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

	private Direction direction = Direction.NONE;
	
	public Animator() {
	}

	/** Builds an animator.
	 * @param spritesRightwards The sprites for when it's moving rightwards.
	 * @param spritesLeftwards The sprites for when it's moving leftwards.
	 * @param spritesUpwards The sprites for when it's moving upwards.
	 * @param spritesDownwards The sprites for when it's moving downwards.
	 * @param interval The time interval between each sprite.
	 * @param x The initial x position.
	 * @param y The initial y position.
	 * @param width The width of the animator.
	 * @param height The height of the animator. */
	public void build(ArrayList<BufferedImage> spritesRightwards,
			ArrayList<BufferedImage> spritesLeftwards,
			ArrayList<BufferedImage> spritesUpwards,
			ArrayList<BufferedImage> spritesDownwards,
			long interval, int x, int y, int width, int height) {
		this.spritesRightwards = spritesRightwards;
		this.spritesLeftwards = spritesLeftwards;
		this.spritesUpwards = spritesUpwards;
		this.spritesDownwards = spritesDownwards;
		this.lastSprite = spritesRightwards.get(0);
		this.interval = interval;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/** Updates the animation. It changes the current sprite if the interval has passed. */
	public void update(long time) {
		this.mutex.down();
		if(this.playing) {
			if(this.direction != Direction.NONE && time - this.previousTime >= this.interval) {
				this.currentSpriteIndex = (this.currentSpriteIndex + 1 < this.spritesRightwards.size()) ? (this.currentSpriteIndex + 1) : 0;
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
				this.lastSprite = this.getIdleSprite();
				this.direction = Direction.NONE;
			}
		}
		this.mutex.up();
	}

	/** Draws the the current sprite.
	 * @param g The Graphics in which to draw the current sprite. */
	public void draw(Graphics g) {
		this.mutex.down();
		g.drawImage(this.getSprite(), this.x, this.y, this.width, this.height, null);
		this.mutex.up();
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

	/** Chooses the appropriate sprite according to the current direction. */
	private BufferedImage getSprite() {
		switch (this.direction) {
		case RIGHTWARDS:
			return this.spritesRightwards.get(this.currentSpriteIndex);
		case LEFTWARDS:
			return this.spritesLeftwards.get(this.currentSpriteIndex);
		case UPWARDS:
			return this.spritesUpwards.get(this.currentSpriteIndex);
		case DOWNWARDS:
			return this.spritesDownwards.get(this.currentSpriteIndex);
		default:
			return this.lastSprite;
		}
	}

	/** Returns the idle sprite according to the direction. */
	private BufferedImage getIdleSprite() {
		switch(this.direction) {
		case RIGHTWARDS:
			return this.spritesRightwards.get(Constants.PASSENGER_IDLE_SPRITE_INDEX);
		case LEFTWARDS:
			return this.spritesLeftwards.get(Constants.PASSENGER_IDLE_SPRITE_INDEX);
		case UPWARDS:
			return this.spritesUpwards.get(Constants.PASSENGER_IDLE_SPRITE_INDEX);
		case DOWNWARDS:
			return this.spritesDownwards.get(Constants.PASSENGER_IDLE_SPRITE_INDEX);
		default:
			return this.spritesRightwards.get(Constants.PASSENGER_IDLE_SPRITE_INDEX);
		}
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

	public void move(Point target, Direction direction, long time) {
		this.movingDuration = time;
		this.targetX = (int) target.getX();
		this.targetY = (int) target.getY();
		this.initialX = this.x;
		this.initialY = this.y;
		this.deltaX = this.targetX - this.initialX;
		this.deltaY = this.targetY - this.initialY;
		this.direction = direction;
		this.movingPreviousTime = System.currentTimeMillis();
		this.moving = true;

	}

}
