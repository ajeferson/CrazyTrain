package br.com.os.interfaces;

import java.awt.Point;

/** Tells which methods a semaphore controller must have. */
public interface SemaphoreController {
	
	/** Ups the passengers semaphore.
	 * @param permits The number to increment on the semaphore. */
	public void upPassengers(int permits);
	
	/** Increments the passengers semaphore by 1. */
	public void upPassengers();
	
	/** Downs the passengers semaphore. */
	public void downPassengers();
	
	/** Increments the roller coaster semaphore by 1. */
	public void upRollerCoaster();
	
	/** Downs the roller coaster semaphore. */
	public void downRollerCoaster();
	
	/** Ups the mutex semaphore. */
	public void upMutex();
	
	/** Downs the mutex semaphore. */
	public void downMutex();
	
	/** Tells if the current roller coaster is full.
	 * @return true if the roller coaster is full.
	 * @return false otherwise. */
	public boolean isRollerCoasterFull();
	
	/** Tells whether or not the roller coaster is moving.
	 * @return true if the roller coaster is moving.
	 * @return false otherwise. */
	public boolean isRollerCoasterMoving();
	
	/** Tells whether or not the roller coaster is empty.
	 * @return true if the roller coaster is empty.
	 * @return false otherwise. */
	public boolean isRollerCoasterEmpty();
	
	/** Increases the number of passengers on the roller coaster by 1. */
	public void incrementNumberOfPassengersOnRollerCoaster();
	
	/** Decrements the number of passengers on the roller coaster by 1. */
	public void decrementNumberOfPassengersOnRollerCoaster();
	
	/** Returns the size of the line */
	public int getLineSize();
	
	/** Returns the exact point of the next available position on the roller coaster. */
	public Point nextAvailablePositionOnRollerCoaster();
	
	/** Ups the semaphore of the next passenger on the line. */
	public void wakeUpNextPassenger();
	
	public void wakeUpNextPassenger(int index);
	
	/** Returns if a roller coaster exists. */
	public boolean isRollerCoasterAlive();
	
	/** Tells the controller that a passenger has just entered the roller coaster. */
	public void passengerDidEnter();
	
	/** Returns the number of occupied seats on the roller coaster. */
	public int numberOfPassengersOnTheRollerCoaster();
	
}
