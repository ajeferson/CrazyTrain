package br.com.os.model;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JPanel;

import br.com.os.controller.Controller;
import br.com.os.controller.Main;

/** This class describes the train, take takes passengers along a trail and takes
 * travellingTime (ms) to make an entire lap. */
public class Train extends Thread {

	private final int maxSeats;
	private int seats = 0;
	private int passengersEnjoying = 0;
	private long travelingTime;
	private boolean moving = false;

	private JPanel view = null;
	
	//Constants
	public static final int TRAIN_WIDTH = 100;
	public static final int TRAIN_HEIGHT = 50;
	public static final Point TRAIN_POSITION = new Point((Main.WINDOW_WIDTH / 2) - (TRAIN_WIDTH / 2), Main.WINDOW_HEIGHT - 200);
	

	private Controller controller;

	/** Creates a train
	 * @param maxSeats Max amount of seats on the train
	 * @param travelingTime The amount of time that the train takes to make a lap (in milliseconds)*/
	public Train(int maxSeats, long travelingTime) {
		this.maxSeats = maxSeats;
		this.setTravelingTime(travelingTime);
	}

	@Override
	public void run() {

		while(true) {

			// Saying: "Available seats"
			this.controller.getSemaphoreLine().up(this.maxSeats);

			// Sleeping while passengers do not enter
			this.controller.getSemaphoreTrain().down();

			// Set moving
			this.controller.getSemaphoreMutex().down();
			this.moving = true;
			this.controller.getSemaphoreMutex().up();

			System.out.println("Crazy Train is full with passengers...");

			// Waking up passenger for enjoying the landscape
			this.controller.getSemaphorePassengers().up(this.maxSeats);

			System.out.println("Crazy Train finished waking all passengers for enjoying the landscape...");

			// Actually moving
			System.out.println("The crazy train started moving...");
			this.move();

			// Stop moving
			this.controller.getSemaphoreMutex().down();
			this.moving = false;
			System.out.println("The crazy train finished moving...");
			this.controller.getSemaphoreMutex().up();

			// Waiting for passengers to stop enjoying the landscape
			this.controller.getSemaphoreTrain().down();

			// Waking up passengers to get out
			this.controller.getSemaphorePassengers().up(this.maxSeats);

			// Waiting for passengers to get out
			this.controller.getSemaphoreTrain().down();

			System.out.println("Everybody left the Crazy Train...");

			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");

		}

	}

	/** Moves the train with the duration of the travellingTime */
	public void move() {
		
		double totalSpace = Main.WINDOW_WIDTH + TRAIN_WIDTH;
		double elapsedTime = 0.0;
		long startTime = System.currentTimeMillis();
		
		int xPosition = (int) TRAIN_POSITION.getX();
		int yPosition = (int) TRAIN_POSITION.getY();
		
		double time = (this.travelingTime / 2.0) * ((Main.WINDOW_WIDTH - TRAIN_POSITION.getX()) / totalSpace);
		int deltaX = Main.WINDOW_WIDTH - xPosition;
		while(elapsedTime < time) {
			elapsedTime = System.currentTimeMillis() - startTime;
			xPosition = (int) (TRAIN_POSITION.getX() + ((elapsedTime / time) * deltaX));
			this.asView().setLocation(xPosition, yPosition);
		}
		
		int deltaY = 150;
		yPosition -= deltaY;
		xPosition = this.view.getX();
		this.view.setLocation(xPosition, yPosition);
		time = travelingTime / 2.0;
		elapsedTime = 0.0;
		startTime = System.currentTimeMillis();
		while(elapsedTime < time) {
			elapsedTime = System.currentTimeMillis() - startTime;
			xPosition = (int) (Main.WINDOW_WIDTH - ((elapsedTime / time) * totalSpace));
			this.asView().setLocation(xPosition, yPosition);
		}
		
		yPosition += deltaY;
		xPosition = this.view.getX();
		this.view.setLocation(xPosition, yPosition);
		time = (travelingTime / 2.0) * (((float)(TRAIN_POSITION.getX() + TRAIN_WIDTH)) / totalSpace);
		elapsedTime = 0.0;
		startTime = System.currentTimeMillis();
		while(elapsedTime < time) {
			elapsedTime = System.currentTimeMillis() - startTime;
			xPosition = (int) (((elapsedTime / time) * deltaX) - TRAIN_WIDTH);
			this.asView().setLocation(xPosition, yPosition);
		}
		
	}

	
	/** View Methods */
	
	public JPanel asView() {
		if(this.view == null) {
			this.view = new JPanel();
			this.view.setSize(TRAIN_WIDTH, TRAIN_HEIGHT);
			this.view.setLocation(TRAIN_POSITION);
			this.view.setBackground(Color.RED);
		}
		return this.view;
	}


	/** Getters and Setters */

	public int getMaxSeats() {
		return maxSeats;
	}

	public long getTravelingTime() {
		return travelingTime;
	}

	public void setTravelingTime(long travelingTime) {
		this.travelingTime = travelingTime;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public void increaseSeats() {
		this.seats++;
	}

	public void decreaseSeats() {
		this.seats--;
	}

	public boolean isFull() {
		return this.seats == this.maxSeats;
	}

	public boolean isEmpty() {
		return this.seats == 0;
	}

	public int getPassengersEnjoying() {
		return passengersEnjoying;
	}

	public void setPassengersEnjoying(int passengersEnjoying) {
		this.passengersEnjoying = passengersEnjoying;
	}

	public void increasePassengersEnjoying() {
		this.passengersEnjoying++;
	}

	public void decreasePassengersEnjoying() {
		this.passengersEnjoying--;
	}

}
