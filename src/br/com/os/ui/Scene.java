package br.com.os.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.com.os.enums.Direction;
import br.com.os.interfaces.Controller;
import br.com.os.interfaces.Item;
import br.com.os.interfaces.ViewController;
import br.com.os.interfaces.ViewControllerDelegate;
import br.com.os.model.Passenger;
import br.com.os.model.RollerCoaster;
import br.com.os.model.amazing.AmazingSemaphore;
import br.com.os.other.Constants;
import br.com.os.sprite.BufferedImageLoader;
import br.com.os.sprite.Sprite;

/** It's the scenario. */
public class Scene extends JPanel implements Controller, ViewControllerDelegate {

	private static final long serialVersionUID = 8905347569137169009L;

	// Semaphores
	private AmazingSemaphore semaphoreRollerCoaster = new AmazingSemaphore(0);
	private AmazingSemaphore semaphorePassengers = new AmazingSemaphore(0);
	private AmazingSemaphore semaphoreMutex = new AmazingSemaphore(1);
	private AmazingSemaphore semaphoreLine = new AmazingSemaphore(0);
	private AmazingSemaphore semaphoreProtector = new AmazingSemaphore(1);

	// Screen elements
	private BufferedImage background;
	private RollerCoaster rollerCoaster;
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	private JButton createRollerCoasterButton;


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

	/** Iterates over the passengers array and draws each of them on the screen. */
	private void drawPassengers(Graphics g) {
		for(Passenger passenger : this.passengers) {
			passenger.draw(g);
		}
	}

	/** Draws the roller coaster on the screen. */
	private void drawRollerCoaster(Graphics g) {
		if(this.rollerCoaster != null) {
			this.rollerCoaster.draw(g);
		}
	}

	/** Draws the background on the screen. */
	private void drawBackground(Graphics g) {
		if(this.background == null) {
			this.background = BufferedImageLoader.loadImage("awesome-background.png");
		}
		g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);
	}


	// ItemHandler implement

	@Override
	public void didProduceItem(ViewController viewController, Item item) {
		if(item instanceof RollerCoaster) {
			this.createRollerCoasterButton = viewController.getActionButton();
			this.handleCreationOfRollerCoaster((RollerCoaster) item);
		} else if(item instanceof Passenger) {
			this.handleCreationOfPassenger((Passenger) item);
		}
	}

	/** Adds a roller coaster to the canvas and sets it as the current roller coaster. */
	private void handleCreationOfRollerCoaster(RollerCoaster rollerCoaster) {
		this.createRollerCoasterButton.setEnabled(false);
		this.rollerCoaster = rollerCoaster;
		this.rollerCoaster.setController(this);
		this.rollerCoaster.setScene(this);
		this.rollerCoaster.build();
		this.rollerCoaster.play();
		this.rollerCoaster.start();
		this.repaint();
	}

	/** Adds the passenger to the array and make it start. */
	private void handleCreationOfPassenger(Passenger passenger) {
		passenger.setScene(this);
		passenger.setController(this);
		passenger.build();
		passenger.play();
		passenger.start();
	}


	// Controller implement

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
	public void downProtector() {
		this.semaphoreProtector.down();
	}

	@Override
	public void upProtector() {
		this.semaphoreProtector.up();
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
	public boolean isRollerCoasterAlive() {
		return this.rollerCoaster != null && this.rollerCoaster.isKeepAlive();
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
	public void drainLine() {
		this.semaphoreLine.drainPermits();
	}

	@Override
	public int numberOfPassengersOnTheLine() {
		if(this.rollerCoaster == null) {
			return this.passengers.size() - 1;
		}
		return this.passengers.size() - this.rollerCoaster.getOccupiedSeats() - 1;
	}

	@Override
	public void organizeLineWithPosition(int position) {
		this.semaphoreProtector.down();
		for(Passenger passenger : this.passengers) {
			if(!passenger.isTravelling() && passenger.getPosition() > position) {
				passenger.setPosition(passenger.getPosition() - 1);
				if(passenger.isMoving()) {
					passenger.setDelta(passenger.getDelta() + Constants.TILE_SIZE);
				} else {
					passenger.move(new Point(passenger.getX() + Constants.TILE_SIZE, passenger.getY()),
							Direction.RIGHTWARDS, Sprite.awesomeTime(Constants.TILE_SIZE));
				}
			}
		}
		this.semaphoreProtector.up();
	}

	@Override
	public void addPassenger(Passenger passenger) {
		this.passengers.add(passenger);
	}

	public void killRollerCoaster() {
		this.semaphoreProtector.down();
		if(this.rollerCoaster != null && this.rollerCoaster.isKeepAlive()) {
			this.rollerCoaster.setKeepAlive(false);
			if(!this.rollerCoaster.isTravelling() && this.semaphoreLine.availablePermits() == this.rollerCoaster.getMaxSeats()) {
				this.semaphoreRollerCoaster.up();
				this.semaphoreProtector.up();
			} else {
				this.semaphoreProtector.up();
				JOptionPane.showMessageDialog(null, "O vagão será deletado ao fim da próxima viagem.");
			}
		} else {
			this.semaphoreProtector.up();
		}
	}

	@Override
	public void rollerCoasterDidDie() {
		this.rollerCoaster = null;
		this.createRollerCoasterButton.setEnabled(true);
		this.repaint();
	}

}
