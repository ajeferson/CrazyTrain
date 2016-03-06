package br.com.os.controller;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Controller controller = new Controller();;
	
	// View Attributes
	private Container container;
	
	// Contants
	public static final int WINDOW_WIDTH = 500;
	public static final int WINDOW_HEIGHT = 500;

	public Main() {
		this.setupViewStuff();
		this.setup();
	}
	
	private void setupViewStuff() {
		this.container = this.getContentPane();
		this.container.setLayout(null);
		this.setTitle("Welcome to the Crazy Train!");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.addTrain();
	}
	
	private void setup() {
		controller.start();
	}
	
	private void addTrain() {
		container.add(this.controller.getTrain().asView());
	}
	
	public void init() {
		System.out.println("Initializing the whole fucking system!");
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}
