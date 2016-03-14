package br.com.os.model;

import java.awt.Point;

import br.com.os.enums.Direction;
import br.com.os.interfaces.Item;
import br.com.os.interfaces.SemaphoreController;
import br.com.os.model.amazing.AmazingSemaphore;
import br.com.os.other.Animator;
import br.com.os.other.Constants;
import br.com.os.other.SpriteSheet;
import br.com.os.other.SpriteSheetCooordinate;

/** This class represents a passenger that can take a trip on the train. */
public class Passenger extends Animator implements Item {

	private static int lastId = 0;
	private final int id;
	private int enteringTime;
	private int leavingTime;
	private boolean shouldWait = true;
	private int position = 0;
	
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

		boolean hasSleptBefore = false;
		
		while(true) {

			// Waiting on the line. Moving forward whenever a new position shows up.
			do {
				this.move(new Point(Constants.WINDOW_WIDTH - (4 + this.position) * Constants.TILE_SIZE,
						this.getY()),
						Direction.RIGHTWARDS, Constants.PASSENGER_DEFAULT_MOVE_TIME);
				this.update();
				this.shouldWait = this.position != 1 || !this.controller.isRollerCoasterAlive() || this.controller.isRollerCoasterFull();
				if(this.shouldWait) {
					if(hasSleptBefore) {
						this.controller.wakeUpNextPassenger(this.position);
					}
					hasSleptBefore = true;
					this.semaphoreWalking.down();
					if(this.position == 1) {
						this.shouldWait = false;
					} else {
						this.position -= this.controller.numberOfPassengersOnTheRollerCoaster();
						if(this.position < 1) {
							this.position = 1;
						}
					}
				}
			} while(this.shouldWait);
			
//			System.out.println(this.getName() + " can enter on the roller coaster.");

			// Climbing the ladder
			System.out.println("Subiu: " + this.getName());
			this.move(new Point(this.getX(), this.getY() - 4 * Constants.TILE_SIZE), Direction.UPWARDS, 5000);
			this.update();
			
			// Going in direction of the roller coaster
			this.move(this.controller.nextAvailablePositionOnRollerCoaster(), Direction.LEFTWARDS, 5000);
			this.update();
			
			// Entering on the roller coaste
			this.setY((this.getY() + this.getHeight()) - Constants.ROLLER_COASTER_HEIGHT - 30);
			this.setDirection(Direction.RIGHTWARDS);
			
			this.controller.incrementNumberOfPassengersOnRollerCoaster();
			
//			System.out.println(this.getName() + " entered the roller coaster");
			
			this.controller.passengerDidEnter();
			
			this.semaphoreWalking.down();
			this.semaphoreWalking.down();
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
				80, -Constants.TILE_SIZE, Constants.WINDOW_HEIGHT - 2 * Constants.TILE_SIZE,
				Constants.PASSENGER_WIDTH, Constants.PASSENGER_HEIGHT);

	}

	private void update() {
		while(this.isMoving()) {
			super.update(System.currentTimeMillis());
		}
	}
	
	public void wakeUp() {
		this.semaphoreWalking.up();
	}
	
	

}
