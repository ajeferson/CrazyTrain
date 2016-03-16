package br.com.os.model;

import java.awt.Point;

import br.com.os.enums.Direction;
import br.com.os.interfaces.Item;
import br.com.os.interfaces.SemaphoreController;
import br.com.os.other.Sprite;
import br.com.os.other.Constants;
import br.com.os.other.SpriteSheet;
import br.com.os.other.SpriteSheetCooordinate;

/** This class represents a passenger that can take a trip on the train. */
public class Passenger extends Sprite implements Item {

	private static int lastId = 0;
	private final int id;
	private int enteringTime;
	private int leavingTime;
	private int position;
	private boolean travelling = false;

	private SemaphoreController controller;

	public Passenger() {
		this.id = ++lastId;
		this.setName("P" + this.id);
		this.enteringTime = 1000;
		this.leavingTime = 1000;
		this.text = "" + this.id;
	}

	@Override
	public void run() {

		int target;
		
		while(true) {
			
			// Moving to the line
			target = Constants.WINDOW_WIDTH - 5 * Constants.TILE_SIZE;
			this.move(new Point(target, this.getY()), Direction.RIGHTWARDS, Sprite.awesomeTime(Math.abs(this.getX() - target)));
			
			// Waiting up on the line
			this.controller.downLine();
			
			// Entering the roller coaster
			this.controller.downMutex();
			
			this.climbUpTheLadder();
			this.enterRollerCoaster();
			
			this.controller.incrementNumberOfPassengersOnRollerCoaster();
			this.setTravelling(true);
			
			// Giving permission for the roller coaster to move
			if(this.controller.isRollerCoasterFull()) {
				this.controller.upRollerCoaster();
			}
			
			this.controller.upMutex();
			
			// Waiting for the roller coaster to leave
			this.controller.downPassengers();

			// Enjoying landscape
			this.enjoyLandscape();

			// Leaving Roller Coaster
			this.controller.downMutex();
			
			this.leaveRollerCoaster();
			this.climbDownTheLadder();
			
			this.controller.decrementNumberOfPassengersOnRollerCoaster();
			this.setTravelling(false);
			
			// Checking to release roller coaster
			if(this.controller.isRollerCoasterEmpty()) {
				this.controller.upRollerCoaster();
			}
			
			this.controller.upMutex();
			
		}

	}

	/** Returns the id of the next passenger that will eventually be created. */
	public static int nextPassengerId() {
		return lastId + 1;
	}

	// View implementations

	/** Sets the sprite frames to be used. */
	public void build() {
		SpriteSheet spriteSheet = new SpriteSheet(1);
		SpriteSheetCooordinate[] coordinatesRightwards = {
				new SpriteSheetCooordinate(4, 64, 25, 32),
				new SpriteSheetCooordinate(36, 64, 25, 32),
				new SpriteSheetCooordinate(68, 64, 25, 32)
		};
		SpriteSheetCooordinate[] coordinatesLeftwards = {
				new SpriteSheetCooordinate(2, 32, 27, 32),
				new SpriteSheetCooordinate(34, 32, 27, 32),
				new SpriteSheetCooordinate(66, 32, 27, 32)
		};
		SpriteSheetCooordinate[] coordinatesUpwards = {
				new SpriteSheetCooordinate(2, 96, 26, 32),
				new SpriteSheetCooordinate(34, 96, 26, 32),
				new SpriteSheetCooordinate(66, 96, 25, 32)
		};
		SpriteSheetCooordinate[] coordinatesDownwards = {
				new SpriteSheetCooordinate(3, 0, 28, 32),
				new SpriteSheetCooordinate(34, 0, 28, 32),
				new SpriteSheetCooordinate(67, 0, 28, 32)
		};

		super.build(spriteSheet.getSpritesWithCoordinates(coordinatesRightwards),
				spriteSheet.getSpritesWithCoordinates(coordinatesLeftwards),
				spriteSheet.getSpritesWithCoordinates(coordinatesUpwards),
				spriteSheet.getSpritesWithCoordinates(coordinatesDownwards),
				200, -Constants.TILE_SIZE, Constants.WINDOW_HEIGHT - 2 * Constants.TILE_SIZE,
				Constants.PASSENGER_WIDTH, Constants.PASSENGER_HEIGHT);

	}

	/** Moves this passenger from the bottom to top of the ladder. */
	private void climbUpTheLadder() {
		int target = this.getY() - (Constants.LADDER_HEIGHT + Constants.TILE_SIZE);
		this.move(new Point(this.getX(), target), Direction.UPWARDS, Sprite.awesomeTime(this.getY() - target));
	}

	/** Moves this passenger to its appropriate spot on the train, according to the position. */
	private void enterRollerCoaster() {
		
		// Going in direction of the roller coaster
		this.position = this.controller.numberOfPassengersOnTheRollerCoaster();
		Point targetPoint = new Point(this.controller.getXPositionOfRollerCoaster() + (Constants.ROLLER_COASTER_WIDTH / 2) * (this.controller.numberOfSeatsOfTheRollerCoaster() - this.position - 1),
				this.controller.getYPositionOfRollerCoaster() + Constants.ROLLER_COASTER_HEIGHT - Constants.PASSENGER_HEIGHT);
		this.move(targetPoint, Direction.LEFTWARDS, Sprite.awesomeTime(Math.abs(this.getX() - (int) targetPoint.getX())));

		// Entering on the roller coaster
		this.setY((this.getY() + this.getHeight()) - Constants.ROLLER_COASTER_HEIGHT - 30);
		this.setDirection(Direction.RIGHTWARDS);
		
	}

	/** Makes the passenger to leave the roller coaster and stays above the exiting laadder. */
	private void leaveRollerCoaster() {
		int target;
		this.setY((this.getY() + 30) + (Constants.ROLLER_COASTER_HEIGHT - this.getHeight()));
		target = Constants.TILE_SIZE * 2;
		this.move(new Point(target, this.getY()), Direction.LEFTWARDS, Sprite.awesomeTime(Math.abs(target - this.getX())));
	}
	
	/** Makes this passenger to go down the ladder */
	private void climbDownTheLadder() {
		int target = Constants.WINDOW_HEIGHT - 2 * Constants.TILE_SIZE;
		this.move(new Point(this.getX(), target),  Direction.UPWARDS, Sprite.awesomeTime(target - this.getY()));
	}

	/** Enjoys the landscape while the roller coaster is travelling */
	private void enjoyLandscape() {
		boolean travelling;
		
		do {
			
			this.controller.downMutex();
			travelling = this.controller.isRollerCoasterTravelling();
			this.controller.upMutex();
			
			this.text = "Enjoying";
			
			// Udating passenger position
			this.setX(this.controller.getXPositionOfRollerCoaster() + this.controller.getWidthOfRollerCoaster() - (this.position + 1) * Constants.PASSENGER_WIDTH);
			this.setY(this.controller.getYPositionOfRollerCoaster() - 30);			
			this.scene.repaint();
			
		} while(travelling);
		
		this.text = "" + this.id;
	}

	// Getters and Setters

	public void setController(SemaphoreController controller) {
		this.controller = controller;
	}

	public int getPassengerId() {
		return id;
	}

	public int getEnteringTime() {
		return enteringTime;
	}

	public void setEnteringTime(int enteringTime) {
		this.enteringTime = enteringTime;
	}

	public int getLeavingTime() {
		return leavingTime;
	}

	public void setLeavingTime(int leavingTime) {
		this.leavingTime = leavingTime;
	}
	
	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public boolean isTravelling() {
		return travelling;
	}

	public void setTravelling(boolean travelling) {
		this.travelling = travelling;
	}

	@Override
	public String toString() {
		String str = "Id: " + this.id;
		str += "\nEntering time: " + this.enteringTime;
		str += "\nLeaving time: " + this.leavingTime;
		return str;
	}

}
