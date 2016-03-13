package br.com.os.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import br.com.os.interfaces.Item;
import br.com.os.interfaces.SemaphoreController;
import br.com.os.interfaces.View;
import br.com.os.other.Constants;

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

	public Passenger(int enteringTime, int leavingTime) {
		super("P" + (++lastId));
		this.id = lastId;
		this.setEnteringTime(enteringTime);
		this.setLeavingTime(leavingTime);
	}


	@Override
	public void run() {
		
		this.moveInLine();
		
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
			this.view.setSize(Constants.PASSENGER_WIDTH, Constants.PASSENGER_HEIGHT);
			this.view.setOpaque(true);
			this.view.setBackground(Color.ORANGE);
			this.view.setFont(new Font("Verdana", Font.BOLD, 30));
			this.view.setHorizontalAlignment(SwingConstants.CENTER);
			this.view.setLocation(-Constants.PASSENGER_WIDTH, Constants.WINDOW_HEIGHT - 2 * (Constants.PASSENGER_HEIGHT));
		}
		return view;
	}

	@Override
	public void moveTo(Point point) {
		int initialY = this.view.getY();
		int initialX = this.view.getX();
		int deltaX = (int) (point.getX() - initialX);
		int deltaY = (int) (point.getY() - initialY);
		double elapsedTime = 0.0;
		double startTime = System.currentTimeMillis();
		while((elapsedTime = System.currentTimeMillis() - startTime) < this.enteringTime) {
			final double fraction = elapsedTime/this.enteringTime;
			try {
				SwingUtilities.invokeAndWait(new Runnable() {
					@Override
					public void run() {
						view.setLocation(initialX + (int) (deltaX * fraction),
								initialY + (int) (deltaY * fraction));
					}
				});
			} catch (InvocationTargetException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** Moves this passenger on line leading to the roller coaster. */
	private void moveInLine() {
		int count = this.controller.getLineSize() - 3;
		int factor = this.controller.getLineSize() - 1;
		if(count < 0) {
			count = 0;
		} else {
			factor = -1;
		}
		int posX = Constants.WINDOW_WIDTH - (count + 1) * (10 + Constants.PASSENGER_WIDTH);
		this.moveTo(new Point(posX, (int) this.view.getLocation().getY()));
		if(factor >= 0) {
			int posY = (int) (this.view.getLocation().getY() - (2 - factor) * (10 + Constants.PASSENGER_HEIGHT));
			this.moveTo(new Point((int) this.view.getLocation().getX(), posY));
		}
	}

}
