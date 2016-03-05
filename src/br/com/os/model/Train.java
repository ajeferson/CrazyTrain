package br.com.os.model;

import br.com.os.controller.Controller;

/** This class describes the train, take takes passengers along a trail and takes
 * travellingTime (ms) to make an entire lap. */
public class Train extends Thread{
	
	private final int maxSeats;
	private int travelingTime;
	private boolean moving = false;
	
	private Controller controller;
	
	public Train(int maxSeats, int travelingTime) {
		this.maxSeats = maxSeats;
		this.setTravelingTime(travelingTime);
	}
	
	@Override
	public void run() {
		
		while(true) {
			
			if(this.controller.getsPassengers().availablePermits() < this.maxSeats) {
				
				try {
					this.controller.getsTrain().acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			} else {
				
				try {
					this.controller.getMutex().acquire(); // Down
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				if(!this.moving) {
					this.move();
				}
				
				this.controller.getMutex().release(); // Up
				
			}
			
		}
	}
	
	private void move() {
		this.moving = true;
		System.out.println("The crazy train is moving...");
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

}
