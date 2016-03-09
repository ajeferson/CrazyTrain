package br.com.os.controller;

import java.util.ArrayList;

import br.com.os.model.AmazingSemaphore;
import br.com.os.model.Passenger;
import br.com.os.model.Train;

/** Class that centralizes the semaphores and some other shared variables. */
public class Controller {

	// Semaphores
	private AmazingSemaphore semaphoreLine = new AmazingSemaphore(0);
	private AmazingSemaphore semaphoreTrain = new AmazingSemaphore(0);
	private AmazingSemaphore semaphorePassengers = new AmazingSemaphore(0);
	private AmazingSemaphore semaphoreMutex = new AmazingSemaphore(1);

	// Control variables
	final int MAX_SEATS = 3;
	private int line = 0;

	// Train and passengers
	private Train train = new Train(MAX_SEATS, 5000);
	private ArrayList<Passenger> passengers = new ArrayList<Passenger>();

	public void start() {
		
		this.train.setController(this);
		this.train.start();

		// Mocking passengers
		this.addPassenger(1000, 2000);
		this.addPassenger(1000, 2000);
		this.addPassenger(1000, 2000);
		this.addPassenger(1000, 2000);
		this.addPassenger(1000, 2000);
		this.addPassenger(1000, 2000);

	}

	/** Adds a new passenger to the ArrayList of passengers and runs it. */
	private void addPassenger(int enteringTime, int leavingTime) {
		Passenger passenger = new Passenger(enteringTime, leavingTime);
		passenger.setController(this);
		this.passengers.add(passenger);
		passenger.start();
	}

	public void printReport() {
		this.semaphoreMutex.down();
		System.out.println("------------------------------");
		System.out.println("Semaphore train: " + this.semaphoreTrain.availablePermits());
		System.out.println("Semaphore passengers: " + this.semaphorePassengers.availablePermits());
		System.out.println("Semaphore line: " + this.semaphoreLine.availablePermits());
		System.out.println("Semaphore mutex: " + this.semaphoreMutex.availablePermits());
		System.out.println("Number of passengers on the train: " + this.train.getSeats());
		System.out.println("Number of passengers enjoying: " + this.train.getPassengersEnjoying());
		System.out.println("------------------------------");
		this.semaphoreMutex.up();
	}

	/** Getters and Setters */

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

	public AmazingSemaphore getSemaphoreLine() {
		return semaphoreLine;
	}

	public void setSemaphoreLine(AmazingSemaphore semaphoreLine) {
		this.semaphoreLine = semaphoreLine;
	}

	public AmazingSemaphore getSemaphoreTrain() {
		return semaphoreTrain;
	}

	public void setSemaphoreTrain(AmazingSemaphore semaphoreTrain) {
		this.semaphoreTrain = semaphoreTrain;
	}

	public AmazingSemaphore getSemaphoreMutex() {
		return semaphoreMutex;
	}

	public void setSemaphoreMutex(AmazingSemaphore semaphoreMutex) {
		this.semaphoreMutex = semaphoreMutex;
	}

	public AmazingSemaphore getSemaphorePassengers() {
		return semaphorePassengers;
	}

	public void setSemaphorePassengers(AmazingSemaphore semaphorePassengers) {
		this.semaphorePassengers = semaphorePassengers;
	}

}
