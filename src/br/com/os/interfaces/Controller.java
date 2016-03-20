package br.com.os.interfaces;

import br.com.os.enums.Direction;
import br.com.os.model.Passenger;

/** Defines useful methods for handling with semaphores and roller coaster. */
public interface Controller {
	
	// Semaphore methods
	
	/** Ups the line semaphore. */
	public void upLine(int permits);
	
	/** Ups the line semaphore. */
	public void upLine();
	
	/** Downs the line semaphore */
	public void downLine();
	
	/** Drains all the permits of the line sempaphore. */
	public void drainLine();
	
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
	
	/** Downs the semaphore protector. */
	public void downProtector();
	
	/** Ups the semaphore protector. */
	public void upProtector();
	
	/** Ups the ArrayList semaphore. */
	public void upArrayList();
	
	/** Downs the ArrayList semaphore. */
	public void downArrayList();
	
	
	// Roller coaster methods
	
	/** Tells if the current roller coaster is full.
	 * @return true if the roller coaster is full.
	 * @return false otherwise. */
	public boolean isRollerCoasterFull();
	
	/** Tells whether or not the roller coaster is moving.
	 * @return true if the roller coaster is moving.
	 * @return false otherwise. */
	public boolean isRollerCoasterMoving();
	
	/** Must tells whether or not the roller coaster is making its circuits. */
	public boolean isRollerCoasterTravelling();
	
	/** Tells whether or not the roller coaster is empty.
	 * @return true if the roller coaster is empty.
	 * @return false otherwise. */
	public boolean isRollerCoasterEmpty();
	
	/** Must return whether or not the roller coaster was deleted. */
	public boolean isRollerCoasterAlive();
	
	/** Increases the number of passengers on the roller coaster by 1. */
	public void incrementNumberOfPassengersOnRollerCoaster();
	
	/** Decrements the number of passengers on the roller coaster by 1. */
	public void decrementNumberOfPassengersOnRollerCoaster();
	
	/** Returns the number of occupied seats on the roller coaster. */
	public int numberOfPassengersOnTheRollerCoaster();
	
	/** Must return the number of seats on the roller coaster. */
	public int numberOfSeatsOfTheRollerCoaster();
	
	/** Must return the width of the roller coaster. */
	public int getWidthOfRollerCoaster();
	
	/** Must return the height of the roller coaster. */
	public int getHeightOfRollerCoaster();
	
	/** Must return the current x position of the roller coaster. */
	public int getXPositionOfRollerCoaster();
	
	/** Must return the current Y position of the roller coaster. */
	public int getYPositionOfRollerCoaster();
	
	/** Must return the direction in which the roller coaster is moving */
	public Direction getDirectionOfRollerCoaster();
	
	/** Must return the size of the line towards to the roller coaster. */
	public int numberOfPassengersOnTheLine();
	
	/** Makes after the given position to advance one space.
	 * @param position The reference position from which all passengers will move. */
	public void organizeLineWithPosition(int position);
	
	/** Adds a passenger to the array os passengers. */
	public void addPassenger(Passenger passenger);
	
	
	// Delegate methods
	
	/** Tells the controller that the roller coaster finished its execution. */
	public void rollerCoasterDidDie();
	
	/** Tells that the passenger with the given id has just died.
	 * @param id The id of the dead passenger. */
	public void passengerDidDie(int id);
	
}
