package br.com.os.controller;

import javax.swing.JFrame;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
//	private Controller controller = new Controller();

	public Main() {
//		this.setupViewStuff();
	}
	
	@SuppressWarnings("unused")
	private void setupViewStuff() {
		this.setTitle("Welcome to the Crazy Train!");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void init() {
		System.out.println("Initializing the whole fucking system!");
	}
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.start();
	}
	
}
