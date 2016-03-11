package br.com.os.model;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JLabel;

import br.com.os.interfaces.Item;
import br.com.os.interfaces.SemaphoreController;
import br.com.os.interfaces.View;

/** This class represents a passenger that can take a trip on the train. */
public class Passenger extends Thread implements View, Item {

	private static int lastId = 0;
	private final int id;
	private int enteringTime;
	private int leavingTime;
	private boolean shouldEnjoyLandscape = false;

	private SemaphoreController controller;
	
	// View attributes
	private JLabel view;
	
	// Constants
	private static final int PASSENGER_WIDTH = 50;
	private static final int PASSENGER_HEIGHT = 50;

	public Passenger(int enteringTime, int leavingTime) {
		super("P" + (++lastId));
		this.id = lastId;
		this.setEnteringTime(enteringTime);
		this.setLeavingTime(leavingTime);
	}


	@Override
	public void run() {

		while(true) {
			
			// Waiting for the permission to get in on the train
			this.controller.downLine();
			
			// Entering on the train
			this.controller.downMutex();
			this.enter();
			this.controller.upMutex();
			
			// Entering on train
			this.controller.downMutex();
			this.controller.incrementNumberOfPassengersOnRollerCoaster(); //numPass++
			if(this.controller.isRollerCoasterFull()) {
				this.controller.upRollerCoaster();
			}
			this.controller.upMutex();
			
			// Waiting for the permission to enjoy the landscape
			this.controller.downPassengers();
			
			// Enjoying the landscape
			this.controller.downMutex();
			
			System.out.println(this.getName() + " has will start enjoying the landscape...");
			
			this.shouldEnjoyLandscape = true;
			this.controller.upMutex();
			
			while(this.shouldEnjoyLandscape) {
				this.controller.downMutex();
				this.shouldEnjoyLandscape = this.controller.isRollerCoasterMoving();
				if(this.shouldEnjoyLandscape) {
					this.enjoyLandscape();
				}
				this.controller.upMutex();
			}
			
			// Actually getting out the train
			this.controller.downMutex();
			this.controller.decrementNumberOfPassengersOnRollerCoaster(); //numPas--
			this.leave();
			
			System.out.println(this.getName() + " left the Crazy Train...");
			
			// Saying to train: "Everybody is out"
			if(this.controller.isRollerCoasterEmpty()) {
				this.controller.upRollerCoaster();
			}
			
			this.controller.upMutex();
			
		}

	}
	
	private void enter() {
		// Spend some time here
		System.out.println(this.getName() + " enters the Crazy Train...");
	}

	private void leave() {
		// Spend some time here
//		System.out.println(this.getName() + " leaves the Crazy Train...");
	}
	
	private void enjoyLandscape() {
//		System.out.println(this.getName() + " enjoys the landscape...");
	}

	// Getters and Setters
	
	public void setController(SemaphoreController controller) {
		this.controller = controller;
	}

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

	/** Returns the id of the next passenger that will eventually be created. */
	public static int nextPassengerId() {
		return lastId + 1;
	}
	
	@Override
	public String toString() {
		String str = "Id: " + this.id;
		str += "\nEntering time: " + this.enteringTime;
		str += "\nLeaving time: " + this.leavingTime;
		return str;
	}

	// View implementations
	
	@Override
	public Component asView() {
		if(this.view == null) {
			this.view = new JLabel(this.id + "");
			this.view.setSize(PASSENGER_WIDTH, PASSENGER_HEIGHT);
		}
		return view;
	}


	@Override
	public void moveTo(Point point) {
		this.view.setLocation(point);
	}

}
