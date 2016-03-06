package br.com.os.model;

import java.awt.Color;

import javax.swing.JPanel;

import br.com.os.controller.Controller;

/** This class describes the train, take takes passengers along a trail and takes
 * travellingTime (ms) to make an entire lap. */
public class Train extends Thread {

	private final int maxSeats;
	private int seats = 0;
	private int passengersEnjoying = 0;
	private long travelingTime;
	private boolean moving = false;

	private JPanel view = null;

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
			for(int i = 0; i < this.maxSeats; i++) {
				this.controller.getSemaphoreLine().up();
			}

			// Sleeping while passengers do not enter
			this.controller.getSemaphoreTrain().down();

			// Set moving
			this.controller.getSemaphoreMutex().down();
			this.moving = true;
			this.controller.getSemaphoreMutex().up();

			System.out.println("Crazy Train is full with passengers...");

			// Waking up passenger for enjoying the landscape
			for(int i = 0; i < this.maxSeats; i++) {
				this.controller.getSemaphorePassengers().up();
			}

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
			for(int i = 0; i < this.maxSeats; i++) {
				this.controller.getSemaphorePassengers().up();
			}

			// Waiting for passengers to get out
			this.controller.getSemaphoreTrain().down();

			System.out.println("Everybody left the Crazy Train...");

			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");

		}

	}

	/** Moves the train with the duration of the travellingTime */
	private void move() {
		long elapsedTime = 0;
		long startTime = System.currentTimeMillis();
		while(elapsedTime < this.travelingTime) {
			elapsedTime = System.currentTimeMillis() - startTime;
		}
		System.out.println("Elapsed time: " + elapsedTime + " ms");
	}

	/** View Methods */
	public JPanel asView() {
		if(this.view == null) {
			this.view = new JPanel();
			this.view.setSize(100, 50);
			this.view.setLocation(200, 350);
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
