package br.com.os.model;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

import br.com.os.enums.Direction;
import br.com.os.interfaces.Controller;
import br.com.os.interfaces.Item;
import br.com.os.other.Constants;
import br.com.os.sprite.PassengerCoordinates;
import br.com.os.sprite.Sprite;
import br.com.os.sprite.SpriteSheet;

/** This class represents a passenger that can take a trip on the train. */
public class Passenger extends Sprite implements Item {

	private SpriteSheet spriteSheet = new SpriteSheet();
	
	private static int lastId = 0;
	private final int id;
	private int enteringTime;
	private int leavingTime;
	private boolean travelling = false;
	private boolean keepAlive = true;
	
	// Positions
	private int linePosition;
	private int travelPosition = -1;
	
	private int delta = 0;

	private Controller controller;

	public Passenger(int enteringTime, int leavingTime) {
		this.id = ++lastId;
		this.setName("P" + this.id);
		this.enteringTime = enteringTime;
		this.leavingTime = leavingTime;
		this.text = "" + this.id;
		this.drawStatus = false;
	}

	@Override
	public void run() {
		
		// Adding to the array of passengers
		this.controller.downArrayList();
		this.controller.addPassenger(this);
		this.controller.didAddedPassenger();
		this.controller.upArrayList();
		
		while(this.keepAlive) {
			
			this.setAwakenColor();
			
			// Positioning correctly on the line
			this.goToTheEndOfLine();
			
			// Saying that this passenger is waiting on the line
			this.travelPosition = -1;
			
			this.setSleepingColor();
			
			// Waiting up on the line
			this.controller.downLine();
			
			// Entering the roller coaster
			this.controller.downMutex();
			
			this.setAwakenColor();
			
			// When it is supposed to delete a passenger that is on the line
			if(!this.keepAlive) {
				this.controller.upLine(); // ups because this passenger is going to leave the line
				continue;
			}
			
			// Just for making sure to be on the bottom of the entrance ladder
			this.moveToTheEntranceLadder();
			
			this.climbUpTheLadder();
			this.enterRollerCoaster();
			
			this.controller.incrementNumberOfPassengersOnRollerCoaster();
			this.setTravelling(true);
			
			// Organizing the line
			this.controller.downArrayList();
			this.controller.organizeLineWithPosition(this.linePosition);
			this.controller.upArrayList();
			
			// Giving permission for the roller coaster to move
			if(this.controller.isRollerCoasterFull()) {
				this.controller.upRollerCoaster();
			}
			
			this.controller.upMutex();
			
			this.setSleepingColor();
			
			// Waiting for the roller coaster to leave
			this.controller.downPassengers();
			
			this.setAwakenColor();
			
			// Enjoying landscape
			this.enjoyLandscape();

			this.setSleepingColor();
			
			// Leaving Roller Coaster
			this.controller.downMutex();
			
			this.setAwakenColor();
			
			this.leaveRollerCoaster();
			this.climbDownTheLadder();
			
			this.controller.decrementNumberOfPassengersOnRollerCoaster();
			this.setTravelling(false);
			
			// Checking to release roller coaster
			if(this.controller.isRollerCoasterEmpty()) {
				this.controller.upRollerCoaster();
			}
			
			// Gives the turn only if the passenger is getting back to the line
			if(this.keepAlive) {
				this.controller.upMutex();
			}
			
		}
		
		// Leaving the line
		this.controller.downArrayList();
		this.leaveTheLine();
		
		// Organizing the line only if the deleted passenger was not leaving the roller coaster
		if(this.travelPosition < 0) { 
			this.controller.organizeLineWithPosition(this.linePosition);
		}
		
		// Telling the controller that this passenger has just died
		this.controller.passengerDidDie(this.id);
		
		// Releasing the semaphores
		this.controller.upArrayList();
		this.controller.upMutex();

	}

	/** Returns the id of the next passenger that will eventually be created. */
	public static int nextPassengerId() {
		return lastId + 1;
	}

	// View implementations

	/** Sets the sprite frames to be used. */
	public void build() {
		
		Random randomGenerator = new Random();
		
		int codigoPassageiro = randomGenerator.nextInt(7);
		
		PassengerCoordinates passengerCoordinates = new PassengerCoordinates(codigoPassageiro +1);

		super.build(spriteSheet.getSpritesWithCoordinates(passengerCoordinates.getCoordinatesRightwards()),
				spriteSheet.getSpritesWithCoordinates(passengerCoordinates.getCoordinatesLeftwards()),
				spriteSheet.getSpritesWithCoordinates(passengerCoordinates.getCoordinatesUpwards()),
				150, -Constants.TILE_SIZE, Constants.WINDOW_HEIGHT - 2 * Constants.TILE_SIZE,
				Constants.PASSENGER_WIDTH, Constants.PASSENGER_HEIGHT);
		

	}
	
	/** Makes the passenger to go from its current position to the end of the line */
	private void goToTheEndOfLine() {
		this.controller.downArrayList();
		this.linePosition = this.controller.numberOfPassengersOnTheLine();
		this.controller.upArrayList();
		int target = Constants.WINDOW_WIDTH - (5 + this.linePosition) * Constants.TILE_SIZE;
		this.move(new Point(target, this.getY()), this.getX() < target ? Direction.RIGHTWARDS : Direction.LEFTWARDS, Sprite.awesomeTime(Math.abs(this.getX() - target)));
		this.setDirection(Direction.RIGHTWARDS);
	}
	
	private void leaveTheLine() {
		this.move(new Point(-Constants.PASSENGER_WIDTH, this.getY()), Direction.LEFTWARDS, Sprite.awesomeTime(this.getX() + Constants.PASSENGER_WIDTH));
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
		
		int changeTime = 30;
		int max = 5;
		int initialY = this.getY();
		Random random = new Random();
		int delta = random.nextInt(max) + 1;
		
		long initialTime = System.currentTimeMillis();
		long elapsedTime;
		boolean up = true;
		
		do {
			
			this.controller.downMutex();
			travelling = this.controller.isRollerCoasterTravelling();
			this.controller.upMutex();
			
			elapsedTime = System.currentTimeMillis() - initialTime;
			if(elapsedTime >= changeTime) {
				if(delta == max || delta == 0) {
					up = !up;
				}
				delta += up ? 1 : -1;
				initialTime = System.currentTimeMillis();
			}
			
			// Updating passenger position
			this.setX(this.controller.getXPositionOfRollerCoaster() + this.controller.getWidthOfRollerCoaster() - (this.travelPosition + 1) * Constants.PASSENGER_WIDTH);
			this.setY(this.controller.getYPositionOfRollerCoaster() - 30 + delta);
			this.setDirection(this.controller.getDirectionOfRollerCoaster());
			this.scene.repaint();
			
		} while(travelling);
		
		this.setY(initialY);
		this.scene.repaint();
		
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
	
	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}
	
	@Override
	public String getItemId() {
		return "" + this.id;
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

	private void setSleepingColor() {
		this.statusColor = Color.GREEN;
	}
	
	private void setAwakenColor() {
		this.statusColor = Color.YELLOW;
	}
	
}
