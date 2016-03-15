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

	private BufferedImage background;
	private ArrayList<BufferedImage> ground;

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

	@SuppressWarnings("unused")
	private void drawGround(Graphics g) {
		if(this.ground == null) {
			this.ground = new ArrayList<BufferedImage>();
			this.ground.add(BufferedImageLoader.loadImage("tiles/ground2.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/ground3.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/ground5.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/water.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/ladder.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/box.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/crystal.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/tree.png"));
			this.ground.add(BufferedImageLoader.loadImage("tiles/signRight.png"));

		}
		int i, size = Constants.TILE_SIZE;
		for(i = 0; i < (Constants.WINDOW_WIDTH / size) - 4; i++) {
			g.drawImage(this.ground.get(0), i * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		}
		g.drawImage(this.ground.get(2), i * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		g.drawImage(this.ground.get(3), i * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		g.drawImage(this.ground.get(1), i++ * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		g.drawImage(this.ground.get(2), i++ * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		g.drawImage(this.ground.get(2), i++ * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		g.drawImage(this.ground.get(2), i++ * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		i-=3;

		// Water
		int k;
		for(k = (Constants.WINDOW_WIDTH / size) - i; k > 0; k--) {
			g.drawImage(this.ground.get(3), i++ * size, Constants.WINDOW_HEIGHT - size, size, size, null);
		}

		// Behind
		for(i = 2; i < 5; i++) {
			for (k = 0; k < Constants.WINDOW_WIDTH / size; k++) {
				g.drawImage(this.ground.get(2), k * size, Constants.WINDOW_HEIGHT - i * size, size, size, null);
			}
		}

		for (k = 0; k < Constants.WINDOW_WIDTH / size; k++) {
			g.drawImage(this.ground.get(0), k * size, Constants.WINDOW_HEIGHT - i * size, size, size, null);
		}

		for(i = 6; i < 8; i++) {
			for (k = 0; k < Constants.WINDOW_WIDTH / size; k++) {
				g.drawImage(this.ground.get(2), k * size, Constants.WINDOW_HEIGHT - i * size, size, size, null);
			}
		}

		for (k = 0; k < Constants.WINDOW_WIDTH / size; k++) {
			g.drawImage(this.ground.get(0), k * size, Constants.WINDOW_HEIGHT - i * size, size, size, null);
		}

		// Ladders
		for(k = 2; k < 6; k++) {
			g.drawImage(this.ground.get(4), Constants.WINDOW_WIDTH - 5 * size, Constants.WINDOW_HEIGHT - k * size, size, size, null);
			g.drawImage(this.ground.get(4), 2 * size, Constants.WINDOW_HEIGHT - k * size, size, size, null);
		}

		// Boxes
		g.drawImage(this.ground.get(5), 4 * size, Constants.WINDOW_HEIGHT - 2 * size, size, size, null);
		g.drawImage(this.ground.get(5), 11 * size, Constants.WINDOW_HEIGHT - 2 * size, size, size, null);

		// Trees
		g.drawImage(this.ground.get(7), 6 * size, Constants.WINDOW_HEIGHT - 2 * size - 73, 100, 123, null);
		g.drawImage(this.ground.get(7), 8 * size, Constants.WINDOW_HEIGHT - 2 * size - 73, 100, 123, null);

		// Signs
		g.drawImage(this.ground.get(8), 13 * size, Constants.WINDOW_HEIGHT - 2 * size, size, size, null);

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
		if(this.passengers.isEmpty()) {
			passenger.setPosition(1);
		} else {
			passenger.setPosition(this.passengers.get(this.passengers.size() - 1).getPosition() + 1);
		}
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
	public int getLineSize() {
		return this.passengers.size();
	}

	@Override
	public Point nextAvailablePositionOnRollerCoaster() {
		return new Point(this.rollerCoaster.getX() + (Constants.ROLLER_COASTER_WIDTH / 2) * (this.rollerCoaster.getMaxSeats() - this.rollerCoaster.getOccupiedSeats() - 1),
				this.rollerCoaster.getY() + Constants.ROLLER_COASTER_HEIGHT - Constants.PASSENGER_HEIGHT);
	}

	@Override
	public void wakeUpNextPassenger() {
		this.wakeUpNextPassenger(0);
	}
	
	@Override
	public void wakeUpNextPassenger(int index) {
		if(this.passengers.size() > index) {
			Passenger passenger = this.passengers.get(index);
			passenger.wakeUp();
		}
	}

	@Override
	public boolean isRollerCoasterAlive() {
		return this.rollerCoaster != null;
	}

	@Override
	public void passengerDidEnter() {
		Passenger passenger = this.passengers.remove(0);
		this.passengersTravelling.add(passenger);
		passenger.setPosition(this.passengersTravelling.size());
		this.wakeUpNextPassenger();
	}

	@Override
	public int numberOfPassengersOnTheRollerCoaster() {
		return this.rollerCoaster.getOccupiedSeats();
	}

	@Override
	public int getWidthOfRollerCoaster() {
		return this.rollerCoaster.getWidth();
	}

	@Override
	public void spriteDidUpdatePositionToPoint(Sprite sprite, Point point) {
		for(Passenger passenger : this.passengersTravelling) {
			passenger.setX(this.rollerCoaster.getX() + this.rollerCoaster.getWidth() - passenger.getPosition() * Constants.PASSENGER_WIDTH);
			passenger.setY(this.rollerCoaster.getY() - 30);
		}
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
	public void wakeTravellingPassengerAtIndex(int index) {
		this.passengersTravelling.get(index).wakeUp();
	}

}
