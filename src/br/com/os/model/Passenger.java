package br.com.os.model;

import java.awt.Point;

import br.com.os.enums.Direction;
import br.com.os.interfaces.Item;
import br.com.os.interfaces.SemaphoreController;
import br.com.os.model.amazing.AmazingSemaphore;
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
	private int position = 0;
	private static int dist = -1;

	private AmazingSemaphore semaphoreWalking = new AmazingSemaphore(0);

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

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
		boolean willMoveOnTheLine = false;
		boolean sleptBefore = false;

		while(true) {
			
			target = Constants.WINDOW_WIDTH - (4 + this.position) * Constants.TILE_SIZE;

			// Waiting on the line. Moving forward whenever a new position shows up.
			do {

				willMoveOnTheLine = false;
				
				// Going to end of the line
				this.move(new Point(target, this.getY()),
						Direction.RIGHTWARDS, Sprite.awesomeTime(target - this.getX()));

				if(this.position > 1) {
					
					if(sleptBefore) {
						this.controller.wakeUpNextPassenger(this.position);
					}
					
					sleptBefore = true;
					willMoveOnTheLine = true;
					
					this.semaphoreWalking.down();
					
					if(this.position == 1) {
						dist = -1;
					} else {
						if(!this.controller.isRollerCoasterFull()) {
							target = Constants.LADDER_X_POSITION;
							this.position = 1;
						} else {
							if(dist < 0) {
								target = Constants.LADDER_X_POSITION;
								this.position = 1;
								dist = 1;
							} else {
								target = this.getX() + Constants.TILE_SIZE * (this.position - (dist + 1));
								this.position = ++dist;
							}
						}
					}
					
				} else {
					if(sleptBefore && this.controller.isRollerCoasterFull()) {
						this.controller.wakeUpNextPassenger(this.position);
					}
				}
			} while(willMoveOnTheLine);
				
			this.controller.downLine();

			this.climbUpTheLadder();
			this.enterRollerCoaster();
			this.controller.incrementNumberOfPassengersOnRollerCoaster();
			this.controller.passengerDidEnter();

			// Giving permission for the roller coaster to move
			if(this.controller.isRollerCoasterFull()) {
				this.controller.upRollerCoaster();
			}

			this.controller.downPassengers();

			// Enjoying landscape
			this.enjoyLandscape();

			// Waiting for the permission to leave the roller coaster
			this.semaphoreWalking.down();

			// Leaving Roller Coaster
			this.leaveRollerCoaster();
			this.climbDownTheLadder();
			this.controller.decrementNumberOfPassengersOnRollerCoaster();
			this.controller.downMutex();
			this.controller.passengerDidLeave();
			this.controller.upMutex();
			
			if(this.controller.isRollerCoasterEmpty()) {
				dist = -1;
				this.controller.upRollerCoaster();
			} else {
				this.controller.downMutex();
				this.controller.wakeUpNextTravellingPassenger();
				this.controller.upMutex();
			}
			
			sleptBefore = false;
			
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

	/** Ups this passenger walking semaphore. */
	public void wakeUp() {
		this.semaphoreWalking.up();
	}

	/** Moves this passenger from the bottom to top of the ladder. */
	private void climbUpTheLadder() {
		int target = this.getY() - (Constants.LADDER_HEIGHT + Constants.TILE_SIZE);
		this.move(new Point(this.getX(), target), Direction.UPWARDS, Sprite.awesomeTime(this.getY() - target));
	}

	/** Moves this passenger to its appropriate spot on the train, according to the position. */
	private void enterRollerCoaster() {
		// Going in direction of the roller coaster
		Point targetPoint = this.controller.nextAvailablePositionOnRollerCoaster();
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
		while(this.controller.isRollerCoasterTravelling()) {
//			this.text = "Enjoying";
			//			this.setX(this.controller.getXPositionOfRollerCoaster() + this.controller.getWidthOfRollerCoaster() - this.position * Constants.PASSENGER_WIDTH);
			//			this.setY(this.controller.getYPositionOfRollerCoaster() - 30);
			//			this.scene.repaint();
		}
//		this.text = null;
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

	@Override
	public String toString() {
		String str = "Id: " + this.id;
		str += "\nEntering time: " + this.enteringTime;
		str += "\nLeaving time: " + this.leavingTime;
		return str;
	}

}
