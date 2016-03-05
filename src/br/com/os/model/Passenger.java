package br.com.os.model;

/** This class represents a passenger that can take a trip on the train. */
public class Passenger {

	private final int id;
	private int enteringTime;
	private int leavingTime;
	
	public Passenger(int id, int enteringTime, int leavingTime) {
		this.id = id;
		this.setEnteringTime(enteringTime);
		this.setLeavingTime(leavingTime);
	}

	
	/** Getters and Setters */
	
	public int getId() {
		return id;
	}

	public int getEnteringTime() {
		return enteringTime;
	}

	public void setEnteringTime(int enteringTime) {
		this.enteringTime = enteringTime;
	}

	public int getLeavingTime() {
		return leavingTime;
	}

	public void setLeavingTime(int leavingTime) {
		this.leavingTime = leavingTime;
	}
	
}
