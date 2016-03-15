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
	private boolean shouldWait = true;
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
	}

	@Override
	public void run() {

		boolean sleptBefore = false;
		
		int target = Constants.WINDOW_WIDTH - (4 + this.position) * Constants.TILE_SIZE;
		
		while(true) {

			// Waiting on the line. Moving forward whenever a new position shows up.
			do {
				
				// Going to end of the line
				this.move(new Point(target, this.getY()),
						Direction.RIGHTWARDS, Sprite.awesomeTime(target - this.getX()));
				
				this.shouldWait = this.position != 1 || !this.controller.isRollerCoasterAlive() || this.controller.isRollerCoasterFull();
				
				if(this.shouldWait) {
					
					if(sleptBefore) {
						this.controller.wakeUpNextPassenger(this.position);
					}
					sleptBefore = true;
					
					this.semaphoreWalking.down();
					
					if(this.position == 1) {
						dist = -1;
						this.shouldWait = false;
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
					
				}
				
			} while(this.shouldWait);
			
			this.climbTheLadder();
			this.reachSpot();
			this.controller.incrementNumberOfPassengersOnRollerCoaster();
			this.controller.passengerDidEnter();
			
			if(this.controller.isRollerCoasterFull()) {
				this.controller.upRollerCoaster();
			}
			
//			this.controller.downPassengers();
			this.semaphoreWalking.down();
			
			//			
			//			// Entering on the train
			//			this.controller.downMutex();
			//			this.enter();
			//			this.controller.upMutex();
			//			
			//			// Entering on train
			//			this.controller.downMutex();
			//			this.controller.incrementNumberOfPassengersOnRollerCoaster(); //numPass++
			//			if(this.controller.isRollerCoasterFull()) {
			//				this.controller.upRollerCoaster();
			//			}
			//			this.controller.upMutex();
			//			
			//			// Waiting for the permission to enjoy the landscape
			//			this.controller.downPassengers();
			//			
			//			// Enjoying the landscape
			//			this.controller.downMutex();
			//			
			//			System.out.println(this.getName() + " has will start enjoying the landscape...");
			//			
			//			this.shouldEnjoyLandscape = true;
			//			this.controller.upMutex();
			//			
			//			while(this.shouldEnjoyLandscape) {
			//				this.controller.downMutex();
			//				this.shouldEnjoyLandscape = this.controller.isRollerCoasterMoving();
			//				if(this.shouldEnjoyLandscape) {
			//					this.enjoyLandscape();
			//				}
			//				this.controller.upMutex();
			//			}
			//			
			//			// Actually getting out the train
			//			this.controller.downMutex();
			//			this.controller.decrementNumberOfPassengersOnRollerCoaster(); //numPas--
			//			this.leave();
			//			
			//			System.out.println(this.getName() + " left the Crazy Train...");
			//			
			//			// Saying to train: "Everybody is out"
			//			if(this.controller.isRollerCoasterEmpty()) {
			//				this.controller.upRollerCoaster();
			//			}
			//			
			//			this.controller.upMutex();

		}

	}

	@SuppressWarnings("unused")
	private void enter() {
		// Spend some time here
		System.out.println(this.getName() + " enters the Crazy Train...");
	}

	@SuppressWarnings("unused")
	private void leave() {
		// Spend some time here
		//		System.out.println(this.getName() + " leaves the Crazy Train...");
	}

	@SuppressWarnings("unused")
	private void enjoyLandscape() {
		//		System.out.println(this.getName() + " enjoys the landscape...");
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

	/** Returns the id of the next passenger that will eventually be created. */
	public static int nextPassengerId() {
		return lastId + 1;
	}

	@Override
	public String toString() {
		String str = "Id: " + this.id;
		str += "\nEntering time: " + this.enteringTime;
		str += "\nLeaving time: " + this.leavingTime;
		return str;
	}

	// View implementations

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
	
	public void wakeUp() {
		this.semaphoreWalking.up();
	}
	
	private void climbTheLadder() {
		// Climbing the ladder
		int target = this.getY() - (Constants.LADDER_HEIGHT + Constants.TILE_SIZE);
		this.move(new Point(this.getX(), target), Direction.UPWARDS, Sprite.awesomeTime(this.getY() - target));
	}
	
	private void reachSpot() {
		// Going in direction of the roller coaster
		Point targetPoint = this.controller.nextAvailablePositionOnRollerCoaster();
		this.move(targetPoint, Direction.LEFTWARDS, Sprite.awesomeTime(Math.abs(this.getX() - (int) targetPoint.getX())));

		// Entering on the roller coaster
		this.setY((this.getY() + this.getHeight()) - Constants.ROLLER_COASTER_HEIGHT - 30);
		this.setDirection(Direction.RIGHTWARDS);
	}
	
	

}
