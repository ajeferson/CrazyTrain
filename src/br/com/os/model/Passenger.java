package br.com.os.model;

import java.awt.Point;

import br.com.os.enums.Direction;
import br.com.os.interfaces.Controller;
import br.com.os.interfaces.Item;
import br.com.os.other.Constants;
import br.com.os.sprite.Sprite;
import br.com.os.sprite.SpriteSheet;
import br.com.os.sprite.SpriteSheetCooordinate;

/** This class represents a passenger that can take a trip on the train. */
public class Passenger extends Sprite implements Item {

	private static int lastId = 0;
	private final int id;
	private int enteringTime;
	private int leavingTime;
	private boolean travelling = false;
	
	// Positions
	private int linePosition;
	private int travelPosition;
	
	private int delta = 0;

	private Controller controller;

	public Passenger(int enteringTime, int leavingTime) {
		this.id = ++lastId;
		this.setName("P" + this.id);
		this.enteringTime = enteringTime;
		this.leavingTime = leavingTime;
		this.text = "" + this.id;
	}

	@Override
	public void run() {
		
		// Adding to the array of passengers
		this.controller.downArrayList();
		this.controller.addPassenger(this);
		this.controller.upArrayList();
		
		while(true) {
			
			// Positioning correctly on the line
			this.goToTheEndOfLine();
			
			// Waiting up on the line
			this.controller.downLine();
			
			// Entering the roller coaster
			this.controller.downMutex();
			
			// Just for making sure to be on the bottom of the entrance ladder
			this.moveToTheEntranceLadder();
			
			this.climbUpTheLadder();
			this.enterRollerCoaster();
			
			this.controller.incrementNumberOfPassengersOnRollerCoaster();
			this.setTravelling(true);
			
			// Organizing the line
			this.controller.organizeLineWithPosition(this.linePosition);
			
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
		
		SpriteSheet spriteSheet = new SpriteSheet();
		
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

		super.build(spriteSheet.getSpritesWithCoordinates(coordinatesRightwards),
				spriteSheet.getSpritesWithCoordinates(coordinatesLeftwards),
				spriteSheet.getSpritesWithCoordinates(coordinatesUpwards),
				200, -Constants.TILE_SIZE, Constants.WINDOW_HEIGHT - 2 * Constants.TILE_SIZE,
				Constants.PASSENGER_WIDTH, Constants.PASSENGER_HEIGHT);

	}
	
	/** Makes the passenger to go from its current position to the end of the line */
	private void goToTheEndOfLine() {
		this.linePosition = this.controller.numberOfPassengersOnTheLine();
		int target = Constants.WINDOW_WIDTH - (5 + this.linePosition) * Constants.TILE_SIZE;
		this.move(new Point(target, this.getY()), Direction.RIGHTWARDS, Sprite.awesomeTime(Math.abs(this.getX() - target)));
	}

	/** Makes the passenger to go to bottom of the entrance ladder. */
	private void moveToTheEntranceLadder() {
		int target = Constants.LADDER_ENTRANCE_X_POSITION;
		this.move(new Point(target, this.getY()),
				Direction.RIGHTWARDS,
				Sprite.awesomeTime(Math.abs(this.getX() - target)));
	};
	
	/** Moves this passenger from the bottom to top of the ladder. */
	private void climbUpTheLadder() {
		int target = this.getY() - (Constants.LADDER_HEIGHT + Constants.TILE_SIZE);
		this.move(new Point(this.getX(), target), Direction.UPWARDS, Sprite.awesomeTime(this.getY() - target));
	}

	/** Moves this passenger to its appropriate spot on the train, according to the position. */
	private void enterRollerCoaster() {
		
		// Going in direction of the roller coaster
		this.travelPosition = this.controller.numberOfPassengersOnTheRollerCoaster();
		Point targetPoint = new Point(this.controller.getXPositionOfRollerCoaster() + (Constants.ROLLER_COASTER_WIDTH / 2) * (this.controller.numberOfSeatsOfTheRollerCoaster() - this.travelPosition - 1),
				this.controller.getYPositionOfRollerCoaster() + Constants.ROLLER_COASTER_HEIGHT - Constants.PASSENGER_HEIGHT);
		int time = this.enteringTime;
		if(Constants.PASSENGER_DEFAULT_TIMES) {
			time = Sprite.awesomeTime(Math.abs(this.getX() - (int) targetPoint.getX()));
		}
		this.move(targetPoint, Direction.LEFTWARDS, time);

		// Entering on the roller coaster
		this.setY((this.getY() + this.getHeight()) - Constants.ROLLER_COASTER_HEIGHT - 30);
		this.setDirection(Direction.RIGHTWARDS);
		
	}

	/** Makes the passenger to leave the roller coaster and stays above the exiting ladder. */
	private void leaveRollerCoaster() {
		int target, time;
		this.setY((this.getY() + 30) + (Constants.ROLLER_COASTER_HEIGHT - this.getHeight()));
		target = Constants.TILE_SIZE * 2;
		time = this.leavingTime;
		if(Constants.PASSENGER_DEFAULT_TIMES) {
			time = Sprite.awesomeTime(Math.abs(target - this.getX()));
		}
		this.move(new Point(target, this.getY()), Direction.LEFTWARDS, time);
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
			
			// Updating passenger position
			this.setX(this.controller.getXPositionOfRollerCoaster() + this.controller.getWidthOfRollerCoaster() - (this.travelPosition + 1) * Constants.PASSENGER_WIDTH);
			this.setY(this.controller.getYPositionOfRollerCoaster() - 30);			
			this.scene.repaint();
			
		} while(travelling);
		
		this.text = "" + this.id;
	}

	// Getters and Setters

	public void setController(Controller controller) {
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
		return linePosition;
	}

	public void setPosition(int position) {
		this.linePosition = position;
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

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}
	
	@Override
	public void move(Point target, Direction direction, long time) {
		super.move(target, direction, time);
		if(this.delta > 0) {
			Point point = new Point(this.getX() + delta, this.getY());
			this.delta = 0;
			this.move(point, Direction.RIGHTWARDS, Sprite.awesomeTime(Math.abs(this.getX() - (int) point.getX())));
		}
	}

	@Override
	public String getItemId() {
		return "" + this.id;
	}

}
