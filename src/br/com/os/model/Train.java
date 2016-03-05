package br.com.os.model;

/** This class describes the train, take takes passengers along a trail and takes
 * travellingTime (ms) to make an entire lap. */
public class Train {
	
	private final int maxSeats;
	private int travelingTime;
	
	public Train(int maxSeats, int travelingTime) {
		this.maxSeats = maxSeats;
		this.setTravelingTime(travelingTime);
	}

	
	/** Getters and Setters */
	
	public int getMaxSeats() {
		return maxSeats;
	}

	public int getTravelingTime() {
		return travelingTime;
	}

	public void setTravelingTime(int travelingTime) {
		this.travelingTime = travelingTime;
	}

}
