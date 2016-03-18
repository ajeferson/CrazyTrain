package br.com.os.model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import br.com.os.enums.Direction;
import br.com.os.interfaces.Item;
import br.com.os.interfaces.Controller;
import br.com.os.other.Constants;
import br.com.os.sprite.BufferedImageLoader;
import br.com.os.sprite.Sprite;

/** This class describes the train, take takes passengers along a trail and takes
 * travellingTime (ms) to make an entire lap. */
public class RollerCoaster extends Sprite implements Item {

	private final int maxSeats;
	private int occupiedSeats;
	private long travelingTime;
	private boolean travelling = false;

	private Controller controller;
	
	private boolean keepAlive = true;

	/** Creates a train
	 * @param maxSeats Max amount of seats on the train
	 * @param travelingTime The amount of time that the train takes to make a lap (in milliseconds)*/
	public RollerCoaster(int maxSeats, long travelingTime) {
		this.maxSeats = maxSeats;
		this.setWidth((maxSeats/2) * Constants.ROLLER_COASTER_WIDTH);
		this.setTravelingTime(travelingTime);
		this.occupiedSeats = 0;
		this.changeFrames = false;
	}

	@Override
	public void run() {

		while(keepAlive) {

			// Saying: "Available seats"
			this.controller.upLine(this.maxSeats);

			// Sleeping while passengers do not enter
			this.controller.downRollerCoaster();
			
			// Checking if it is still alive
			this.controller.downMutex();
			if(!this.keepAlive) {
				if(this.isEmpty()) {
					this.controller.drainLine();
					this.controller.upMutex();
					continue;
				} else {
					this.controller.upMutex();
					this.controller.downRollerCoaster();
				}
			} else {
				this.controller.upMutex();
			}

			// Moving
			this.makeCircuit();
			
			// Waiting for passengers to get out
			this.controller.downRollerCoaster();

		}
		
		// Setting up for when the roller coaster dies
		this.controller.downMutex();
		this.controller.drainLine();
		this.controller.upMutex();
		this.controller.rollerCoasterDidDie();
		System.out.println("Vagao is dead...");

	}

	/** Builds the roller coaster by setting the sprites. */
	public void build() {
		BufferedImage image = BufferedImageLoader.loadImage("roller-coaster.png");
		ArrayList<BufferedImage> array = new ArrayList<BufferedImage>();
		array.add(image);
		super.build(array, null, null, 100,
				Constants.WINDOW_WIDTH/2 - ((this.maxSeats/2) * Constants.ROLLER_COASTER_WIDTH)/2,
				Constants.WINDOW_HEIGHT - 5*Constants.TILE_SIZE - Constants.ROLLER_COASTER_HEIGHT,
				(this.maxSeats/2) * Constants.ROLLER_COASTER_WIDTH,
				Constants.ROLLER_COASTER_HEIGHT);
	}

	/** Draws all the wagons of the roller coaster */
	@Override
	public void draw(Graphics g) {
		for(int i = 0; i < this.maxSeats/2; i++) {
			g.drawImage(this.spritesRightwards.get(0), this.getX() + i * Constants.ROLLER_COASTER_WIDTH,
					this.getY(), Constants.ROLLER_COASTER_WIDTH, Constants.ROLLER_COASTER_HEIGHT, null);
		}
	}

	/** Makes the train to move around the mountains. */
	private void makeCircuit() {

		this.controller.downMutex();
		this.travelling = true;
		this.controller.upMutex();
		
		// Waking up passenger for enjoying the landscape
		this.controller.upPassengers(this.maxSeats);

		long time = this.travelingTime;
		if(Constants.ROLLER_COASTER_DEFAULT_TIMES) {
			time = Constants.ROLLER_COASTER_DEFAULT_TIME;
		}
		
		this.move(new Point(Constants.WINDOW_WIDTH + 2*this.getWidth(), this.getY()), Direction.RIGHTWARDS, time/4);
		this.setY(this.getY() - 3 * Constants.TILE_SIZE);
		this.move(new Point(-2*this.getWidth(), this.getY()), Direction.LEFTWARDS, 2 * (time/4));
		this.setY(this.getY() + 3 * Constants.TILE_SIZE);
		this.move(new Point((Constants.WINDOW_WIDTH / 2) - (this.getWidth() /2), this.getY()), Direction.RIGHTWARDS, time/4);
		this.setDirection(Direction.RIGHTWARDS);

		this.controller.downMutex();
		this.travelling = false;
		this.controller.upMutex();
		
	}

	
	// Getters and Setters

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public int getMaxSeats() {
		return maxSeats;
	}

	public long getTravelingTime() {
		return travelingTime;
	}

	public void setTravelingTime(long travelingTime) {
		this.travelingTime = travelingTime;
	}

	public boolean isFull() {
		return this.occupiedSeats == this.maxSeats;
	}

	public boolean isEmpty() {
		return this.occupiedSeats == 0;
	}

	public void incrementOccupiedSeats() {
		this.occupiedSeats++;
	}

	public void decrementOccupiedSeats() {
		this.occupiedSeats--;
	}

	public int getOccupiedSeats() {
		return occupiedSeats;
	}

	public boolean isTravelling() {
		return this.travelling;
	}

	@Override
	public String toString() {
		String str = "";
		str += ("Max seats: " + this.maxSeats);
		str += ("Available seats: " + (this.maxSeats - this.occupiedSeats));
		str += ("\nTravelling time: " + this.travelingTime + "ms");
		return str;
	}

	@Override
	public String getItemId() {
		return "1";
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

}
