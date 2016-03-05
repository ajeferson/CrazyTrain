package br.com.os.model;

import br.com.os.controller.Controller;

/** This class represents a passenger that can take a trip on the train. */
public class Passenger extends Thread {

	private final int id;
	private int enteringTime;
	private int leavingTime;
	private boolean inside = false;

	private Controller controller;

	public Passenger(int id, int enteringTime, int leavingTime) {
		this.id = id;
		this.setEnteringTime(enteringTime);
		this.setLeavingTime(leavingTime);
	}


	@Override
	public void run() {

		while(true) {

			if(this.inside) {
				if(this.controller.getTrain().isMoving()) {
					this.enjoyLandscape();
				} else {
					this.controller.getsPassengers().release(); // Up passengers
					try {
						this.controller.getsPassengers().acquire(); // Down Mutex
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					this.controller.incrementLine();
					
					this.leaveTrain();
					
					this.controller.getMutex().release();
					
				}		
			} else {
				// TODO
			}

		}

	}


	private void enjoyLandscape() {
		System.out.println("Enjoying landscape...");
	}

	private void leaveTrain() {
		System.out.println("Leaving the Crazy Train...");
	}

	/** Getters and Setters */

	public int getPassengerId() {
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


	public boolean isInside() {
		return inside;
	}


	public void setInside(boolean inside) {
		this.inside = inside;
	}


	public Controller getController() {
		return controller;
	}


	public void setController(Controller controller) {
		this.controller = controller;
	}

}
