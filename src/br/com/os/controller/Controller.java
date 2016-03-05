package br.com.os.controller;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import br.com.os.model.Passenger;
import br.com.os.model.Train;

/** Class that centralizes the semaphores and some other shared variables. */
public class Controller {

	// Semaphores
	private Semaphore sPassengers = new Semaphore(1);
	private Semaphore sTrain = new Semaphore(0);
	private Semaphore mutex = new Semaphore(1);

	// Control variables
	final int MAX_SEATS = 5;
	private int line = 0;

	// Train and passengers
	private Train train = new Train(MAX_SEATS, 1000);
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();

	public void setup() {

		this.train.setController(this);

		// Mocking passengers
		this.addPassenger(1, 1000, 2000);

	}

	/** Adds a new passenger to the ArrayList of passengers and runs it. */
	private void addPassenger(int id, int enteringTime, int leavingTime) {
		Passenger passenger = new Passenger(id, enteringTime, leavingTime);
		passenger.setController(this);
		//		passenger.run();
		this.passengers.add(passenger);
	}

	/** Getters and Setters */

	public Semaphore getsPassengers() {
		return sPassengers;
	}
	
	public void setsPassengers(Semaphore sPassengers) {
		this.sPassengers = sPassengers;
	}
	
	public Semaphore getsTrain() {
		return sTrain;
	}
	
	public void setsTrain(Semaphore sTrain) {
		this.sTrain = sTrain;
	}
	
	public Semaphore getMutex() {
		return mutex;
	}
	
	public void setMutex(Semaphore mutex) {
		this.mutex = mutex;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}
	
	public void incrementLine() {
		this.line++;
	}
	
	public void decrementLine() {
		this.line--;
	}

}
