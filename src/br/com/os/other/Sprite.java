package br.com.os.other;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import br.com.os.enums.Direction;
import br.com.os.interfaces.SpriteDelegate;
import br.com.os.ui.Scene;

/** Encapsulates a set of sprites an animates them. */
public class Sprite extends Thread {

	protected ArrayList<BufferedImage> spritesRightwards;
	protected ArrayList<BufferedImage> spritesLeftwards;
	protected ArrayList<BufferedImage> spritesUpwards;
	protected ArrayList<BufferedImage> spritesDownwards;
	protected BufferedImage lastSprite;
	protected Scene scene;
	
	protected String text;

	protected boolean playing = false; // Should be volatile?
	protected boolean moving = false;
	protected boolean changeFrames = true;
	
	private SpriteDelegate delegate;

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
	public void update() {
		long time = System.currentTimeMillis();
		if(this.changeFrames) {
			this.updateSprite(time);
		}
		this.updateMovement(time);
	}
	
	/** Changes the frames of the sprite in the appropriate time.
	 * This method repaints the scene if it's time to change a frame.
	 * @param time The current time of the system. */
	private void updateSprite(long time) {
		if(this.playing) {
			if(this.direction != Direction.NONE && time - this.previousTime >= this.interval) {
				this.currentSpriteIndex = (this.currentSpriteIndex + 1 < this.spritesRightwards.size()) ? (this.currentSpriteIndex + 1) : 0;
				this.previousTime = time;
				this.scene.repaint();
			}
		}
	}
	
	/** Updates the coordinates of the sprite according to the current movement status.
	 * This method repaints the screens if some change happens to the coordinates.
	 * @param time The current time, necessary for updating the movement accordingly. */
	private void updateMovement(long time) {
		if(this.moving) {
			this.movingElapsedTime = time - this.movingPreviousTime;
			if(this.movingElapsedTime < this.movingDuration) {
				double fraction = this.movingElapsedTime / this.movingDuration;
				this.x = this.initialX + (int) (this.deltaX * fraction);
				this.y = this.initialY + (int) (this.deltaY * fraction);
				if(this.delegate != null) {
					this.delegate.spriteDidUpdatePositionToPoint(this, new Point(this.x, this.y));
				}
				this.scene.repaint();
			} else {
				this.x = this.targetX;
				this.y = this.targetY;
				this.moving = false;
				if(this.changeFrames) this.lastSprite = this.getIdleSprite();
				this.setDirection(Direction.NONE);
				this.scene.repaint();
			}
		}
	}
	
	public static int awesomeTime(int distance) {
		return (int) (Constants.SPRITE_SPEED_MULTIPLIER * (distance/50.0));
	}

	/** Draws the the current sprite.
	 * @param g The Graphics in which to draw the current sprite. */
	public void draw(Graphics g) {
		g.drawImage(this.getSprite(), this.x, this.y, this.width, this.height, null);
		if(this.text != null) {
			g.setColor(Color.WHITE);
			g.drawString(this.text, this.x - 20, this.y);
		}
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
	
	/** Moves this sprite to the target point.
	 * @param target The target point to which its desired to move.
	 * @param direction The direction to the point, for setting the appropriate sprite.
	 * @param time The duration of the movement. */
	public void move(Point target, Direction direction, long time) {
		this.movingDuration = time;
		this.targetX = (int) target.getX();
		this.targetY = (int) target.getY();
		this.initialX = this.x;
		this.initialY = this.y;
		this.deltaX = this.targetX - this.initialX;
		this.deltaY = this.targetY - this.initialY;
		this.setDirection(direction);
		this.movingPreviousTime = System.currentTimeMillis();
		this.moving = true;
		while(this.isMoving()) {
			this.update();
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
	
	public void setDirection(Direction direction) {
		this.direction = direction;
		if(this.delegate != null) {
			this.delegate.spriteDidChangeDirectionTo(this.direction);
		}
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public SpriteDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(SpriteDelegate delegate) {
		this.delegate = delegate;
	}
	
	public boolean isMoving() {
		return this.moving;
	}

}
