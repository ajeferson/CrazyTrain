package br.com.os.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import br.com.os.enums.Direction;
import br.com.os.interfaces.Item;
import br.com.os.interfaces.ItemHandler;
import br.com.os.interfaces.SemaphoreController;
import br.com.os.interfaces.SpriteDelegate;
import br.com.os.model.Passenger;
import br.com.os.model.RollerCoaster;
import br.com.os.model.amazing.AmazingSemaphore;
import br.com.os.other.BufferedImageLoader;
import br.com.os.other.Constants;
import br.com.os.other.Sprite;

/** It's the scenario. */
public class Scene extends JPanel implements SemaphoreController, ItemHandler, ActionListener, SpriteDelegate {

	private static final long serialVersionUID = 8905347569137169009L;

	// Semaphores
	private AmazingSemaphore semaphoreRollerCoaster = new AmazingSemaphore(0);
	private AmazingSemaphore semaphorePassengers = new AmazingSemaphore(0);
	private AmazingSemaphore semaphoreMutex = new AmazingSemaphore(1);
	private AmazingSemaphore semaphoreLine = new AmazingSemaphore(0);

	private BufferedImage background;

	private RollerCoaster rollerCoaster;
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	private ArrayList<Passenger> passengersTravelling = new ArrayList<Passenger>();

	// JPanel override
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.drawBackground(g);
		this.drawPassengers(g);
		this.drawRollerCoaster(g);
		Toolkit.getDefaultToolkit().sync();
	}

	private void drawPassengers(Graphics g) {
		for(Passenger passenger : this.passengers) {
			passenger.draw(g);
		}
		for(Passenger passenger : this.passengersTravelling) {
			passenger.draw(g);
		}
	}

	private void drawRollerCoaster(Graphics g) {
		if(this.rollerCoaster != null) {
			this.rollerCoaster.draw(g);
		}
	}

	private void drawBackground(Graphics g) {
		if(this.background == null) {
			this.background = BufferedImageLoader.loadImage("awesome-background.png");
		}
		g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	// ItemHandler implement

	@Override
	public void didProduceItem(Item item) {
		if(item instanceof RollerCoaster) {
			this.handleCreationOfRollerCoaster((RollerCoaster) item);
		} else if(item instanceof Passenger) {
			this.handleCreationOfPassenger((Passenger) item);
		}
	}

	/** Adds a roller coaster to the canvas and sets it as the current roller coaster. */
	private void handleCreationOfRollerCoaster(RollerCoaster rollerCoaster) {
		this.rollerCoaster = rollerCoaster;
		this.rollerCoaster.setController(this);
		this.rollerCoaster.setScene(this);
		this.rollerCoaster.setDelegate(this);
		this.rollerCoaster.build();
		this.rollerCoaster.play();
		this.rollerCoaster.start();
		this.repaint();
	}

	/** Adds the passenger to the array and make it start. */
	private void handleCreationOfPassenger(Passenger passenger) {
		this.passengers.add(passenger);
		passenger.setScene(this);
		passenger.setController(this);
		passenger.build();
		passenger.play();
		passenger.start();
	}

	// SemaphoreControllerImplement

	@Override
	/** Timer event. Called evertime the timer ends its counting. */
	public void actionPerformed(ActionEvent e) {
		this.repaint();
	}

	@Override
	public void upPassengers(int permits) {
		this.semaphorePassengers.up(permits);
	}

	@Override
	public void upPassengers() {
		this.semaphorePassengers.up();
	}

	@Override
	public void downPassengers() {
		this.semaphorePassengers.down();
	}

	@Override
	public void upRollerCoaster() {
		this.semaphoreRollerCoaster.up();
	}

	@Override
	public void downRollerCoaster() {
		this.semaphoreRollerCoaster.down();
	}

	@Override
	public void upMutex() {
		this.semaphoreMutex.up();
	}

	@Override
	public void downMutex() {
		this.semaphoreMutex.down();
	}

	@Override
	public boolean isRollerCoasterFull() {
		return this.rollerCoaster.isFull();
	}

	@Override
	public boolean isRollerCoasterMoving() {
		return this.rollerCoaster.isMoving();
	}
	
	@Override
	public boolean isRollerCoasterTravelling() {
		return this.rollerCoaster.isTravelling();
	}

	@Override
	public boolean isRollerCoasterEmpty() {
		return this.rollerCoaster.isEmpty();
	}

	@Override
	public void incrementNumberOfPassengersOnRollerCoaster() {
		this.rollerCoaster.incrementOccupiedSeats();
	}

	@Override
	public void decrementNumberOfPassengersOnRollerCoaster() {
		this.rollerCoaster.decrementOccupiedSeats();
	}

	@Override
	public int numberOfPassengersOnTheRollerCoaster() {
		if(this.rollerCoaster == null) {
			return 0;
		}
		return this.rollerCoaster.getOccupiedSeats();
	}
	
	@Override
	public int numberOfSeatsOfTheRollerCoaster() {
		return this.rollerCoaster.getMaxSeats();
	}

	@Override
	public int getWidthOfRollerCoaster() {
		return this.rollerCoaster.getWidth();
	}
	
	@Override
	public int getHeightOfRollerCoaster() {
		return this.rollerCoaster.getHeight();
	}

	@Override
	public void spriteDidUpdatePositionToPoint(Sprite sprite, Point point) {
//		for(Passenger passenger : this.passengersTravelling) {
//			passenger.setX(this.rollerCoaster.getX() + this.rollerCoaster.getWidth() - passenger.getPosition() * Constants.PASSENGER_WIDTH);
//			passenger.setY(this.rollerCoaster.getY() - 30);
//		}
	}

	@Override
	public void spriteDidChangeDirectionTo(Direction direction) {
		for(Passenger passenger : this.passengersTravelling) {
			passenger.setDirection(direction);
		}
	}

	@Override
	public int getXPositionOfRollerCoaster() {
		return this.rollerCoaster.getX();
	}

	@Override
	public int getYPositionOfRollerCoaster() {
		return this.rollerCoaster.getY();
	}

	@Override
	public void upLine(int permits) {
		this.semaphoreLine.up(permits);
	}

	@Override
	public void upLine() {
		this.semaphoreLine.up();
	}

	@Override
	public void downLine() {
		this.semaphoreLine.down();
	}

	@Override
	public int getLineSize() {
		return this.passengers.size();
	}

	@Override
	public void organizeLineWithId(int id) {
		Passenger passenger;
		int c = 0;
		int target;
		for(int i = 0; i < this.passengers.size(); i++) {
			passenger = this.passengers.get(i);
			if(!passenger.isTravelling()) {
				target = Constants.LADDER_X_POSITION - c * Constants.TILE_SIZE;
				passenger.move(new Point(target, passenger.getY()),
						Direction.RIGHTWARDS, Sprite.awesomeTime(Math.abs(passenger.getX() - target)));
				c++;
			}
		}
	}
	
	public void passengerDidEnter(int id) {
		this.passengers.remove(0);
	}

	@Override
	public void inlinePassenger(Passenger passenger) {
		this.passengers.add(passenger);
	}

	@Override
	public void removeTravellerWithId(int id) {
		int index = -1;
		int i = 0;
		while(index < 0) {
			if(this.passengersTravelling.get(i++).getPassengerId() == id) {
				index = i;
			}
		}
		this.passengersTravelling.remove(index);
	}

}
