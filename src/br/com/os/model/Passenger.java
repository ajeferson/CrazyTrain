package br.com.os.model;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JLabel;

import br.com.os.controller.Controller;
import br.com.os.interfaces.View;

/** This class represents a passenger that can take a trip on the train. */
public class Passenger extends Thread implements View {

	private static int lastId = 0;
	private final int id;
	private int enteringTime;
	private int leavingTime;
	private boolean shouldEnjoyLandscape = false;

	private Controller controller;
	
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
			
			System.out.println(this.getName() + " has will start enjoying the landscape...");
			
			this.shouldEnjoyLandscape = true;
			this.controller.getSemaphoreMutex().up();
			
			while(this.shouldEnjoyLandscape) {
				this.controller.getSemaphoreMutex().down();
				this.shouldEnjoyLandscape = this.controller.getTrain().isMoving();
				if(this.shouldEnjoyLandscape) {
					this.enjoyLandscape();
				}
				this.controller.getSemaphoreMutex().up();
			}
			
			// Actually getting out the train
			this.controller.getSemaphoreMutex().down();
			this.getUp(); //numPas--
			this.leave();
			
			System.out.println(this.getName() + " left the Crazy Train...");
			
			// Saying to train: "Everybody is out"
			if(this.controller.getTrain().isEmpty()) {
				this.controller.getSemaphoreTrain().up();
			}
			
			this.controller.getSemaphoreMutex().up();
			
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
	
	private void sit() {
		this.controller.getTrain().increaseSeats();;
	}
	
	private void getUp() {
		this.controller.getTrain().decreaseSeats();
	}

	// Getters and Setters

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
