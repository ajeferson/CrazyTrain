package br.com.os.interfaces;

/** Tells which methods a semaphore controller must have. */
public interface SemaphoreController {
	
	/** Ups the line semaphore. */
	public void upLine(int permits);
	
	/** Ups the line semaphore. */
	public void upLine();
	
	/** Downs the line semaphore */
	public void downLine();
	
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
	
	/** Must tells whether or not the roller coaster is making its circuits. */
	public boolean isRollerCoasterTravelling();
	
	/** Tells whether or not the roller coaster is empty.
	 * @return true if the roller coaster is empty.
	 * @return false otherwise. */
	public boolean isRollerCoasterEmpty();
	
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
	
}
