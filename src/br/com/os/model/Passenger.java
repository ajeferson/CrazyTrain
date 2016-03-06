package br.com.os.model;

import br.com.os.controller.Controller;

/** This class represents a passenger that can take a trip on the train. */
public class Passenger extends Thread {

	private final int id;
	private int enteringTime;
	private int leavingTime;
	private boolean enjoyingLandscape = false;

	private Controller controller;

	public Passenger(int id, int enteringTime, int leavingTime) {
		this.id = id;
		this.setEnteringTime(enteringTime);
		this.setLeavingTime(leavingTime);
	}


	@Override
	public void run() {

		while(true) {

			// Waiting for the permission to get in on the train
			this.controller.getSemaphoreLine().down();
			
			// Entering on the train
			this.controller.getSemaphoreMutex().down();
			this.enter();
			this.controller.getSemaphoreMutex().up();
			
			// Entering on train
			this.controller.getSemaphoreMutex().down();
			this.sit(); //numPass++;
			if(this.controller.getTrain().isFull()) {
				this.controller.getSemaphoreTrain().up();
			}
			this.controller.getSemaphoreMutex().up();
			
			// Waiting for the permission to enjoy the landscape
			this.controller.getSemaphorePassengers().down();
			
			// Enjoying the landscape
			this.controller.getSemaphoreMutex().down();
			this.enjoyingLandscape = true;
			this.controller.getTrain().increasePassengersEnjoying();
			this.controller.getSemaphoreMutex().up();
			while(this.enjoyingLandscape) {
				this.controller.getSemaphoreMutex().down();
				this.enjoyingLandscape = this.controller.getTrain().isMoving();
				this.controller.getSemaphoreMutex().up();
				this.enjoyLandscape();
			}
			
			// Stopping enjoying the landscape
			this.controller.getSemaphoreMutex().down();
			this.controller.getTrain().decreasePassengersEnjoying(); //numPass--;
			
			// Saying to train: "Everybody stopped enjoying the landscape"
			if(this.controller.getTrain().getPassengersEnjoying() == 0) {
				this.controller.getSemaphoreTrain().up();
			}
			this.controller.getSemaphoreMutex().up();
			
			// Waiting for the train to allow the exit
			this.controller.getSemaphorePassengers().down();
			
			// Actually getting out the train
			this.controller.getSemaphoreMutex().down();
			this.getUp();
			this.leave();
			
			// Saying to train: "Everybody is out"
			if(this.controller.getTrain().isEmpty()) {
				this.controller.getSemaphoreTrain().up();
			}
			this.controller.getSemaphoreMutex().up();
			
		}

	}
	
	private void enter() {
		System.out.println("Entering the Crazy Train...");
	}

	private void leave() {
		System.out.println("Leaving the Crazy Train...");
	}
	
	private void enjoyLandscape() {
		System.out.println("Enjoying landscape...");
	}
	
	private void sit() {
		this.controller.getTrain().increaseSeats();;
	}
	
	private void getUp() {
		this.controller.getTrain().decreaseSeats();
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

	public Controller getController() {
		return controller;
	}


	public void setController(Controller controller) {
		this.controller = controller;
	}

}
